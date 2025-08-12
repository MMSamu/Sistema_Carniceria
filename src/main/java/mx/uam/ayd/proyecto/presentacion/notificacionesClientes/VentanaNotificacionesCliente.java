package mx.uam.ayd.proyecto.presentacion.notificacionesClientes;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Componente de la capa de presentación encargado de mostrar y actualizar
 * periódicamente las notificaciones de un pedido específico en la interfaz de usuario.
 *
 * Este componente:
 * - Consulta el estado de las notificaciones y del pedido a intervalos regulares.
 * - Construye un DTO (DatosNotificaciones) con la información necesaria para renderizar en la UI.
 * - Permite iniciar y detener el ciclo de actualización.
 */
@Component // Indica que esta clase es un componente de Spring
@RequiredArgsConstructor // Lombok: genera constructor con los campos finales requeridos
public class VentanaNotificacionesCliente {

    // Controlador que maneja la lógica de negocio relacionada con notificaciones
    private final ControlNotificacionesCliente control;

    // Ejecuta tareas periódicas en segundo plano
    private ScheduledExecutorService scheduler;

    /**
     * Inicia la actualización periódica de notificaciones para un pedido.
     *
     * @param idPedido ID del pedido a monitorear.
     * @param render   Función (callback) que recibe el DTO DatosNotificaciones
     *                 y lo utiliza para renderizar en la interfaz gráfica.
     */
    public void iniciar(Long idPedido, Consumer<DatosNotificaciones> render) {
        // Detener cualquier ciclo previo para evitar duplicados
        detener();

        // Crear un scheduler de un solo hilo para ejecutar la tarea periódica
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Definir la tarea a ejecutar periódicamente
        Runnable task = () -> {
            // Obtener todas las notificaciones del pedido
            List<Notificacion> notifs = control.obtenerNotificaciones(idPedido);

            // Contar cuántas notificaciones no están leídas
            long noLeidas = control.contarNoLeidas(idPedido);

            // Obtener el estado actual del pedido
            EstadoPedido estado = control.obtenerEstadoActual(idPedido);

            // Determinar si se deben mostrar botones/mapas en la UI (ej. en ruta)
            boolean mostrarBotonContactar = (estado == EstadoPedido.EN_RUTA);
            boolean mostrarMapa = (estado == EstadoPedido.EN_RUTA);

            // Crear un DTO con toda la información necesaria para la vista
            DatosNotificaciones dto = new DatosNotificaciones(
                    estado,
                    notifs,
                    noLeidas,
                    mostrarBotonContactar,
                    mostrarMapa
            );

            // Ejecutar el renderizado pasando el DTO a la función de la UI
            render.accept(dto);
        };

        // Programar la tarea para que se ejecute cada 30 segundos
        scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * Detiene la ejecución periódica de la tarea de actualización de notificaciones.
     */
    public void detener() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }

    /**
     * DTO que contiene los datos que la UI necesita para mostrar notificaciones.
     *
     * @param estadoActual           Estado actual del pedido.
     * @param notificaciones         Lista completa de notificaciones del pedido.
     * @param cantidadNoLeidas       Número de notificaciones no leídas.
     * @param mostrarBotonContactar  Indica si se debe mostrar el botón de contacto.
     * @param mostrarMapa            Indica si se debe mostrar el mapa de seguimiento.
     */
    public record DatosNotificaciones(
            EstadoPedido estadoActual,
            List<Notificacion> notificaciones,
            long cantidadNoLeidas,
            boolean mostrarBotonContactar,
            boolean mostrarMapa
    ) {}
}
