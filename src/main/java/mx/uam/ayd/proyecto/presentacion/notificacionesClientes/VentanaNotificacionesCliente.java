package mx.uam.ayd.proyecto.presentacion.notificacionesClientes;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class VentanaNotificacionesCliente {

    private final ControlNotificacionesCliente control;
    private ScheduledExecutorService scheduler;

/*    public void iniciar(Long idPedido, Consumer<DatosNotificaciones> render) {
        detener();

        scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            List<Notificacion> notifs = control.obtenerNotificaciones(idPedido);
            long noLeidas = control.contarNoLeidas(idPedido);
            EstadoPedido estado = control.obtenerEstadoActual(idPedido);

            boolean mostrarBotonContactar = (estado == EstadoPedido.EN_RUTA);
            boolean mostrarMapa = (estado == EstadoPedido.EN_RUTA);

            DatosNotificaciones dto = new DatosNotificaciones(
                    estado,
                    notifs,
                    noLeidas,
                    mostrarBotonContactar,
                    mostrarMapa
            );

            render.accept(dto);
        };

        scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
    }*/

    public void detener() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }

    public record DatosNotificaciones(
            EstadoPedido estadoActual,
            List<Notificacion> notificaciones,
            long cantidadNoLeidas,
            boolean mostrarBotonContactar,
            boolean mostrarMapa
    ) {}
}
