package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio de negocio para gestionar la lógica relacionada con los pedidos.
 *
 * Funcionalidades incluidas:
 * - Actualización del estado de un pedido y registro de notificaciones (HU-10).
 * - Asignación de métodos de entrega (HU-03).
 * - Listado y consulta de pedidos en proceso (HU-12).
 * - Transiciones de estado para control de flujo de preparación y entrega.
 */
@Service
@RequiredArgsConstructor // Genera constructor con los campos final requeridos (inyección de
                         // dependencias)
public class PedidoService {

    private final PedidoRepository pedidoRepository; // Repositorio para acceso a datos de pedidos
    private final NotificacionService servicioNotificacion; // Servicio para manejar notificaciones (HU-10)

    /**
     * Actualiza el estado de un pedido y opcionalmente envía una notificación.
     *
     * @param idPedido    ID del pedido a actualizar.
     * @param nuevoEstado Nuevo estado que se asignará.
     * @return El pedido actualizado y persistido.
     */
    @Transactional
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) {
        // Buscar el pedido o lanzar excepción si no existe
        Pedido ped = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));

        // Asignar el nuevo estado como cadena (compatibilidad con el modelo actual)
        ped.setEstado(String.valueOf(nuevoEstado));

        // (Opcional) Usar teléfono real del pedido; si no existe, usar un valor por
        // defecto
        String telefono = ped.getTelefonoContacto() != null ? ped.getTelefonoContacto() : "5555555555";

        // Guardar cambios en BD
        Pedido guardado = pedidoRepository.save(ped);

        /*
         * servicioNotificacion.notificarEstadoPedido(
         * guardado.getIdPedido(),
         * nuevoEstado,
         * Notificacion.Canal.WHATSAPP,
         * telefono
         * );
         */

        return guardado;
    }

    // Métodos atajo para estados importantes
    @Transactional
    public void marcarConfirmado(Long idPedido) {
        actualizarEstado(idPedido, EstadoPedido.CONFIRMADO);
    }

    @Transactional
    public void marcarPreparado(Long idPedido) {
        actualizarEstado(idPedido, EstadoPedido.PREPARADO);
    }

    @Transactional
    public void marcarEnRuta(Long idPedido) {
        actualizarEstado(idPedido, EstadoPedido.EN_RUTA);
    }

    @Transactional
    public void marcarEntregado(Long idPedido) {
        actualizarEstado(idPedido, EstadoPedido.ENTREGADO);
    }

    public List<String> listarMetodosEntrega() {
        return List.of("A domicilio", "En tienda", "Recoger en mostrador");
    }

    public Pedido asignarMetodoEntrega(Long idPedido, String metodo) {
        // 1) Buscar el pedido o lanzar excepción si no existe
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado: " + idPedido));

        // 2) Validar que el método no sea nulo/vacío
        if (metodo == null || metodo.isBlank()) {
            throw new IllegalArgumentException("El método de entrega no puede estar vacío");
        }

        // Validar que el método esté en el catálogo soportado
        List<String> soportados = listarMetodosEntrega();
        if (!soportados.contains(metodo)) {
            throw new IllegalArgumentException("Método de entrega inválido: " + metodo
                    + ". Válidos: " + String.join(", ", soportados));
        }

        // 3) Evitar reasignación si ya existe un método de entrega asignado
        if (pedido.getTipoEntrega() != null && !pedido.getTipoEntrega().isBlank()) {
            throw new IllegalStateException("El pedido ya tiene método de entrega: " + pedido.getTipoEntrega());
        }

        // 4) Asignar y guardar cambios
        pedido.setTipoEntrega(metodo);
        return pedidoRepository.save(pedido);
    }

    // HU-12: listar/consultar
    @Transactional(readOnly = true)
    public List<Pedido> obtenerPedidosEnProcesoOrdenados() {
        // Asegurarse que el valor en BD sea "en proceso" con misma convención
        return pedidoRepository.findByEstadoOrderByHoraAsc("en proceso");
    }

    @Transactional(readOnly = true)
    public Pedido obtenerDetallesPedido(long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));
    }

    /* ==== HU-12: Transiciones de estado ==== */

    /**
     * Marca un pedido como "pedido listo" y registra la hora del cambio.
     *
     * @param idPedido ID del pedido.
     */
    @Transactional
    public void marcarPedidoListo(long idPedido) {
        Pedido p = obtenerDetallesPedido(idPedido);
        if (!"en proceso".equalsIgnoreCase(p.getEstado())) {
            throw new IllegalStateException("Sólo puede marcarse 'pedido listo' desde 'en proceso'.");
        }
        p.setEstado("pedido listo");
        p.setTimestampPedidoListo(LocalDateTime.now());
        pedidoRepository.save(p);
    }

    @Transactional
    public void marcarListoParaEntregar(long idPedido) {
        Pedido p = obtenerDetallesPedido(idPedido);
        if (!"pedido listo".equalsIgnoreCase(p.getEstado())) {
            throw new IllegalStateException("Debe estar en 'pedido listo' antes de pasar a 'listo para entregar'.");
        }
        p.setEstado("listo para entregar");
        p.setTimestampListoParaEntregar(LocalDateTime.now());
        pedidoRepository.save(p);
    }

    // HU-13: transiciones de estado (solo aplica a recepcionista)
    @Transactional(readOnly = true)
    public List<Pedido> listarListoParaEntregar() {
        return pedidoRepository.findByEstadoOrderByHoraAsc("listo para entregar");
    }

    @Transactional
    public void confirmarEntrega(long idPedido) {
        Pedido p = obtenerDetallesPedido(idPedido);
        if (!"listo para entregar".equalsIgnoreCase(p.getEstado())) {
            throw new IllegalStateException(
                    "Debe estar en 'listo para entregar' para confirmar la entrega.");
        }
        p.setEstado("entregado");
        p.setTimestampEntregado(LocalDateTime.now()); // requiere el campo en Pedido
        pedidoRepository.save(p);
    }
}
