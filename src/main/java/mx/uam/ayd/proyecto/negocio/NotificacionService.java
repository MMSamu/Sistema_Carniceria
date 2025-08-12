package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.NotificacionRepository;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.Canal;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import mx.uam.ayd.proyecto.negocio.modelo.ResumenNotificacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de notificaciones que centraliza el envío y consulta de notificaciones
 * relacionadas con pedidos.
 *
 * <p>Casos de uso:</p>
 * <ul>
 *   <li><b>HU-05</b>: Confirmación/auditoría al finalizar un pedido
 *       mediante {@link #enviarNotificacionPedido(Long, String, String, String, boolean)}.</li>
 *   <li><b>HU-10</b>: Notificación por cambio de estado del pedido
 *       mediante {@link #notificarEstadoPedido(Long, EstadoPedido, Canal, String)}.</li>
 *   <li><b>HU-10</b>: Consultas relacionadas (listar, contar no leídas,
 *       marcar leídas y obtener estado actual).</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor // Inyecta vía constructor los repositorios finales
public class NotificacionService {

    // Repositorio para validar la existencia del pedido
    private final PedidoRepository pedidoRepository;
    // Repositorio para persistir y consultar notificaciones
    private final NotificacionRepository notificacionRepo;

    // ---------------- HU-05 ----------------

    /**
     * Envía (registra) una notificación de confirmación de pedido (HU-05).
     *
     * <p>Reglas principales:</p>
     * <ul>
     *   <li>El {@code idPedido} es obligatorio.</li>
     *   <li>El pedido debe estar finalizado para poder notificar.</li>
     *   <li>Se valida que el pedido exista.</li>
     *   <li>Se registra un log de notificación en base de datos con el canal preferido (o WhatsApp por defecto).</li>
     * </ul>
     *
     * @param idPedido         identificador del pedido a confirmar
     * @param tipoEntrega      tipo de entrega (ej. "domicilio" o "tienda")
     * @param telefonoDestino  teléfono del cliente (destino de la notificación)
     * @param canalPreferido   canal preferido (valor del enum {@link Canal} como String)
     * @param pedidoFinalizado indica si el pedido ya se finalizó
     * @return resumen con id, estado y tipo de entrega
     * @throws IllegalArgumentException si {@code idPedido} es nulo o si el pedido no existe
     * @throws IllegalStateException    si el pedido no está finalizado
     */
    public ResumenNotificacion enviarNotificacionPedido(
            Long idPedido,
            String tipoEntrega,
            String telefonoDestino,
            String canalPreferido,
            boolean pedidoFinalizado
    ) {
        // Validación de entrada obligatoria
        if (idPedido == null) {
            throw new IllegalArgumentException("idPedido es obligatorio");
        }
        // Reglas de negocio: no notificar si no está finalizado
        if (!pedidoFinalizado) {
            throw new IllegalStateException("El pedido no está finalizado; no se puede notificar.");
        }

        // Verificación de existencia del pedido
        Optional<?> existe = pedidoRepository.findById(idPedido);
        if (existe.isEmpty()) {
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);
        }

        // Construcción de mensaje/estado para auditoría
        String estado = "Pedido confirmado";
        String mensaje = "Pedido #" + idPedido + " - " + estado + " - Entrega: " + tipoEntrega;

        // Estado "lógico" del envío (no integra un proveedor real)
        String estadoEnvio = "CONFIRMADO";

        // Construcción del log de notificación a persistir
        Notificacion log = new Notificacion();
        log.setIdPedido(idPedido);
        try {
            // Intenta mapear el canal desde String al enum Canal
            log.setCanal(Canal.valueOf(canalPreferido));
        } catch (Exception e) {
            // Si el String no coincide, usa WhatsApp por defecto
            log.setCanal(Canal.WHATSAPP);
        }
        log.setTelefonoDestino(telefonoDestino);
        log.setEstadoEnvio(estadoEnvio);
        log.setMensaje(mensaje);

        // Persistencia del log
        notificacionRepo.save(log);

        // Resumen de respuesta para la capa superior (UI/Control)
        return new ResumenNotificacion(idPedido, estado, tipoEntrega);
    }

    // ---------------- HU-10 ----------------

    /**
     * Registra una notificación asociada a un cambio de estado del pedido (HU-10).
     *
     * <p>Reglas principales:</p>
     * <ul>
     *   <li>El {@code idPedido} es obligatorio y el pedido debe existir.</li>
     *   <li>Construye un mensaje de acuerdo al {@link EstadoPedido} recibido.</li>
     *   <li>Si el canal es nulo, usa {@link Canal#WHATSAPP}; si el teléfono es nulo, usa un valor por defecto.</li>
     * </ul>
     *
     * @param idPedido         identificador del pedido
     * @param estado           nuevo estado del pedido (enum)
     * @param canal            canal de notificación (puede ser nulo)
     * @param telefonoDestino  número telefónico de destino (puede ser nulo)
     * @return la entidad {@link Notificacion} ya persistida
     * @throws IllegalArgumentException si {@code idPedido} es nulo o el pedido no existe
     */
    @Transactional
    public Notificacion notificarEstadoPedido(
            Long idPedido,
            EstadoPedido estado,
            Canal canal,
            String telefonoDestino
    ) {
        // Validación básica
        if (idPedido == null) {
            throw new IllegalArgumentException("idPedido es obligatorio");
        }
        // Asegura que el pedido exista antes de notificar
        if (pedidoRepository.findById(idPedido).isEmpty()) {
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);
        }

        // Mensaje amigable según el estado recibido
        final String mensaje = switch (estado) {
            case CONFIRMADO -> "Tu pedido fue confirmado.";
            case PREPARADO  -> "Tu pedido está preparado.";
            case EN_RUTA    -> "Tu pedido va en ruta. ¡Puedes contactar al repartidor!";
            case ENTREGADO  -> "Tu pedido ha sido entregado.";
        };

        // Construcción del log
        Notificacion log = new Notificacion();
        log.setIdPedido(idPedido);
        // Valores por defecto para robustez
        log.setCanal(canal != null ? canal : Canal.WHATSAPP);
        log.setTelefonoDestino(telefonoDestino != null ? telefonoDestino : "5555555555");
        log.setEstadoEnvio("CONFIRMADO");
        log.setMensaje(mensaje);

        // Persistencia y retorno de la notificación creada
        return notificacionRepo.save(log);
    }

    // ----------- HU-10: MÉTODOS DE CONSULTA -----------

    /**
     * Lista las notificaciones de un pedido ordenadas por fecha/hora descendente.
     * <p>Nota: Si el repositorio no filtra por tipo, se usa directamente
     * {@code findByIdPedidoOrderByFechaHoraDesc}.</p>
     *
     * @param idPedido identificador del pedido
     * @return lista de notificaciones del pedido
     */
    @Transactional(readOnly = true)
    public List<Notificacion> listarEstadoPedido(Long idPedido) {
        // Si tu repo no tiene filtro por tipo, usa solo findByIdPedidoOrderByFechaHoraDesc
        return notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
    }

    /**
     * Cuenta cuántas notificaciones del pedido están marcadas como no leídas.
     *
     * @param idPedido identificador del pedido
     * @return número de notificaciones no leídas
     */
    @Transactional(readOnly = true)
    public long contarNoLeidas(Long idPedido) {
        return notificacionRepo.countByIdPedidoAndLeidaFalse(idPedido);
    }

    /**
     * Marca como leídas todas las notificaciones del pedido (operación en bloque).
     *
     * @param idPedido identificador del pedido
     */
    @Transactional
    public void marcarLeidas(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        // Marca cada notificación como leída en memoria
        lista.forEach(n -> n.setLeida(true));
        // Guarda todas las actualizaciones de una sola vez
        notificacionRepo.saveAll(lista);
    }

    /**
     * Obtiene el estado más reciente (última notificación) del pedido.
     *
     * @param idPedido identificador del pedido
     * @return el {@link EstadoPedido} más reciente o {@code null} si no hay notificaciones
     */
    @Transactional(readOnly = true)
    public EstadoPedido obtenerEstadoActual(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        // Si no hay registros, no se puede inferir estado actual
        return lista.isEmpty() ? null : lista.get(0).getEstadoPedido();
    }
}
