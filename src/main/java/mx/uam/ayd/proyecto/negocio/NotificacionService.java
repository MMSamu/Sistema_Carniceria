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

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final PedidoRepository pedidoRepository;
    private final NotificacionRepository notificacionRepo;

    // ===== HU-05 =====
    @Transactional
    public ResumenNotificacion enviarNotificacionPedido(
            Long idPedido,
            String tipoEntrega,
            String telefonoDestino,
            String canalPreferido,
            boolean pedidoFinalizado
    ) {
        if (idPedido == null)
            throw new IllegalArgumentException("idPedido es obligatorio");
        if (!pedidoFinalizado)
            throw new IllegalStateException("El pedido no está finalizado; no se puede notificar.");
        if (!pedidoRepository.existsById(idPedido))
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);

        Canal canal = Canal.WHATSAPP;
        if (canalPreferido != null && !canalPreferido.isBlank()) {
            try {
                canal = Canal.valueOf(canalPreferido.trim().toUpperCase());
            } catch (IllegalArgumentException ignored) { }
        }

        final String estado = "Pedido confirmado";
        final String mensaje = "Pedido #" + idPedido + " - " + estado + " - Entrega: " + tipoEntrega;

        Notificacion log = new Notificacion();
        log.setIdPedido(idPedido);
        log.setCanal(canal);
        log.setTelefonoDestino(telefonoDestino != null ? telefonoDestino.trim() : null);
        log.setEstadoEnvio("CONFIRMADO");
        log.setMensaje(mensaje);

        notificacionRepo.save(log);
        return new ResumenNotificacion(idPedido, estado, tipoEntrega);
    }

    // ===== HU-10 =====
    @Transactional
    public Notificacion notificarEstadoPedido(
            Long idPedido,
            EstadoPedido estado,
            Canal canal,
            String telefonoDestino
    ) {
        if (idPedido == null)
            throw new IllegalArgumentException("idPedido es obligatorio");
        if (!pedidoRepository.existsById(idPedido))
            throw new IllegalArgumentException("No existe el pedido con id=" + idPedido);

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

    // ===== Consultas HU-10 =====
    @Transactional(readOnly = true)
    public List<Notificacion> listarEstadoPedido(Long idPedido) {
        return notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
    }

    @Transactional(readOnly = true)
    public long contarNoLeidas(Long idPedido) {
        return notificacionRepo.countByIdPedidoAndLeidaFalse(idPedido);
    }

    @Transactional
    public void marcarLeidas(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        // Si tu entidad NO tiene campo "leida", puedes borrar este foreach.
        for (Notificacion n : lista) {
            try { n.setLeida(true); } catch (Throwable ignored) {}
        }
        notificacionRepo.saveAll(lista);
    }

    @Transactional(readOnly = true)
    public EstadoPedido obtenerEstadoActual(Long idPedido) {
        List<Notificacion> lista = notificacionRepo.findByIdPedidoOrderByFechaHoraDesc(idPedido);
        if (lista.isEmpty()) return null;
        try { return lista.get(0).getEstadoPedido(); } catch (Throwable ignored) { return null; }
    }
}
