package mx.uam.ayd.proyecto.negocio;
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final NotificacionService servicioNotificacion;

    @Transactional
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) {
        Pedido ped = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));

        ped.setEstado(String.valueOf(nuevoEstado));

        // (Opcional) tomar teléfono real del pedido/cliente
        String telefono = ped.getTelefonoContacto() != null ? ped.getTelefonoContacto() : "5555555555";

        Pedido guardado = pedidoRepository.save(ped);

       /* servicioNotificacion.notificarEstadoPedido(
                guardado.getIdPedido(),
                nuevoEstado,
                Notificacion.Canal.WHATSAPP,
                telefono
        );*/

        return guardado;
    }

    // Métodos atajo para estados importantes
    @Transactional public void marcarConfirmado(Long idPedido){ actualizarEstado(idPedido, EstadoPedido.CONFIRMADO); }
    @Transactional public void marcarPreparado (Long idPedido){ actualizarEstado(idPedido, EstadoPedido.PREPARADO); }
    @Transactional public void marcarEnRuta   (Long idPedido){ actualizarEstado(idPedido, EstadoPedido.EN_RUTA); }
    @Transactional public void marcarEntregado(Long idPedido){ actualizarEstado(idPedido, EstadoPedido.ENTREGADO); }
    public List<String> listarMetodosEntrega() {

        return List.of("A domicilio", "En tienda", "Recoger en mostrador");

    }
    public Pedido asignarMetodoEntrega(Long idPedido, String metodo) {

        // 1) Buscar el pedido o fallar si no existe.

        Pedido pedido = pedidoRepository.findById(idPedido)

                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado: " + idPedido));

        // 2) Validar entrada (null/blank) y pertenencia al catálogo soportado.

        if (metodo == null || metodo.isBlank()) {

            throw new IllegalArgumentException("El método de entrega no puede estar vacío");

        }

        List<String> soportados = listarMetodosEntrega();

        if (!soportados.contains(metodo)) {

            throw new IllegalArgumentException("Método de entrega inválido: " + metodo

                    + ". Válidos: " + String.join(", ", soportados));

        }

        // 3) Evitar reasignación si ya fue seteado previamente.

        if (pedido.getTipoEntrega() != null && !pedido.getTipoEntrega().isBlank()) {

            throw new IllegalStateException("El pedido ya tiene método de entrega: " + pedido.getTipoEntrega());

        }

        // 4) Asignar y persistir.

        pedido.setTipoEntrega(metodo);

        return pedidoRepository.save(pedido);

    }
    // HU-12: listar/consultar
    @Transactional(readOnly = true)
    public List<Pedido> obtenerPedidosEnProcesoOrdenados() {
        // asegurse de que en la BD el valor sea "en proceso" (misma convención).
        return pedidoRepository.findByEstadoOrderByHoraAsc("en proceso");
    }
    @Transactional(readOnly = true)
    public Pedido obtenerDetallesPedido(long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));
    }

    // HU-12: transiciones de estado
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
}
