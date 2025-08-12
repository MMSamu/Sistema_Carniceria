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
 * Servicio de notificaciones:
 * - HU-05: enviarNotificacionPedido(...) -> confirmación/auditoría.
 * - HU-10: notificarEstadoPedido(...)    -> por cambio de estado del pedido.
 * - HU-10: métodos de consulta           -> listar, contar, marcar como leídas.
 */
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final PedidoRepository pedidoRepository;
    private final NotificacionRepository notificacionRepo;

    // ---------------- HU-05 ----------------
    public ResumenNotificacion enviarNotificacionPedido(
            Long idPedido,
            String tipoEntrega,
            String telefonoDestino,
            String canalPreferido,
            boolean pedidoFinalizado
    ) {
        if (idPedido == null) {
            throw new IllegalArgumentException("idPedido es obligatorio");
        }
        if (!pedidoFinalizado) {
            throw new IllegalStateException("El pedido no está finalizado; no se puede notificar.");
        }

        Optional<?> existe = pedidoRepository.findById(idPedido);
        if (existe.isEmpty()) {
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);
        }

        String estado = "Pedido confirmado";
        String mensaje = "Pedido #" + idPedido + " - " + estado + " - Entrega: " + tipoEntrega;

        String estadoEnvio = "CONFIRMADO";

        Notificacion log = new Notificacion();
        log.setIdPedido(idPedido);
        try {
            log.setCanal(Canal.valueOf(canalPreferido));
        } catch (Exception e) {
            log.setCanal(Canal.WHATSAPP);
        }
        log.setTelefonoDestino(telefonoDestino);
        log.setEstadoEnvio(estadoEnvio);
        log.setMensaje(mensaje);
        notificacionRepo.save(log);

        return new ResumenNotificacion(idPedido, estado, tipoEntrega);
    }

    // ---------------- HU-10 ----------------
    @Transactional
    public Notificacion notificarEstadoPedido(
            Long idPedido,
            EstadoPedido estado,
            Canal canal,
            String telefonoDestino
    ) {
        if (idPedido == null) {
            throw new IllegalArgumentException("idPedido es obligatorio");
        }
        if (pedidoRepository.findById(idPedido).isEmpty()) {
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);
        }

        final String mensaje = switch (estado) {
            case CONFIRMADO -> "Tu pedido fue confirmado.";
            case PREPARADO  -> "Tu pedido está preparado.";
            case EN_RUTA    -> "Tu pedido va en ruta. ¡Puedes contactar al repartidor!";
            case ENTREGADO  -> "Tu pedido ha sido entregado.";
        };

        Notificacion log = new Notificacion();
        log.setIdPedido(idPedido);
        log.setCanal(canal != null ? canal : Canal.WHATSAPP);
        log.setTelefonoDestino(telefonoDestino != null ? telefonoDestino : "5555555555");
        log.setEstadoEnvio("CONFIRMADO");
        log.setMensaje(mensaje);

        return notificacionRepo.save(log);
    }

    // ----------- HU-10: MÉTODOS DE CONSULTA -----------

    @Transactional(readOnly = true)
    public List<Notificacion> listarEstadoPedido(Long idPedido) {
        // Si tu repo no tiene filtro por tipo, usa solo findByIdPedidoOrderByFechaHoraDesc
        return notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
    }

    @Transactional(readOnly = true)
    public long contarNoLeidas(Long idPedido) {
        return notificacionRepo.countByIdPedidoAndLeidaFalse(idPedido);
    }

    @Transactional
    public void marcarLeidas(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        lista.forEach(n -> n.setLeida(true));
        notificacionRepo.saveAll(lista);
    }

    @Transactional(readOnly = true)
    public EstadoPedido obtenerEstadoActual(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        return lista.isEmpty() ? null : lista.get(0).getEstadoPedido();
    }
}
