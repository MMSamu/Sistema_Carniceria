package mx.uam.ayd.proyecto.presentacion.notificacionesClientes;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.NotificacionService;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.EstadoPedido;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controlador de la capa de presentación encargado de gestionar
 * la lógica relacionada con las notificaciones de un cliente.
 *
 * Funciones principales:
 * - Recuperar y mostrar las notificaciones de un pedido.
 * - Contar las notificaciones no leídas (para badges o campanitas en la UI).
 * - Obtener el estado actual del pedido desde las notificaciones.
 * - Marcar las notificaciones como leídas.
 *
 * Se comunica con la capa de negocio a través de NotificacionService.
 */
@Component // Indica que este es un componente gestionado por Spring (inyectable)
@RequiredArgsConstructor // Lombok: genera un constructor con los campos finales requeridos
public class ControlNotificacionesCliente {

    // Servicio de negocio que maneja la lógica de las notificaciones
    private final NotificacionService servicioNotificacion;

    /**
     * Obtiene todas las notificaciones relacionadas con un pedido específico.
     *
     * @param idPedido ID del pedido.
     * @return Lista de notificaciones asociadas, ordenadas de más reciente a más antigua.
     */
    public List<Notificacion> obtenerNotificaciones(Long idPedido) {
        return servicioNotificacion.listarEstadoPedido(idPedido);
    }

    /**
     * Cuenta cuántas notificaciones no leídas tiene un pedido.
     *
     * @param idPedido ID del pedido.
     * @return Número de notificaciones no leídas.
     */
    public long contarNoLeidas(Long idPedido) {
        return servicioNotificacion.contarNoLeidas(idPedido);
    }

    /**
     * Obtiene el estado actual del pedido a partir de la notificación más reciente.
     *
     * @param idPedido ID del pedido.
     * @return Estado actual del pedido o null si no hay notificaciones.
     */
    public EstadoPedido obtenerEstadoActual(Long idPedido) {
        var lista = servicioNotificacion.listarEstadoPedido(idPedido);
        return lista.isEmpty() ? null : lista.get(0).getEstadoPedido();
    }

    /**
     * Marca todas las notificaciones de un pedido como leídas.
     *
     * @param idPedido ID del pedido.
     */
    public void marcarLeidas(Long idPedido) {
        servicioNotificacion.marcarLeidas(idPedido);
    }
}
