package mx.uam.ayd.proyecto.presentacion.notificacionesClientes;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.NotificacionService;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ControlNotificacionesCliente {

    private final NotificacionService servicioNotificacion;

    public List<Notificacion> obtenerNotificaciones(Long idPedido) {
        return servicioNotificacion.listarEstadoPedido(idPedido);
    }

    public long contarNoLeidas(Long idPedido) {
        return servicioNotificacion.contarNoLeidas(idPedido);
    }

    public EstadoPedido obtenerEstadoActual(Long idPedido) {
        var lista = servicioNotificacion.listarEstadoPedido(idPedido);
        return lista.isEmpty() ? null : lista.get(0).getEstadoPedido();
    }

    public void marcarLeidas(Long idPedido) {
        servicioNotificacion.marcarLeidas(idPedido);
    }
}

