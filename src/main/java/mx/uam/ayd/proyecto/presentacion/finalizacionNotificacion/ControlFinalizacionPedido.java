package mx.uam.ayd.proyecto.presentacion.finalizacionNotificacion;

import lombok.RequiredArgsConstructor;                  // inyección por constructor
import mx.uam.ayd.proyecto.negocio.NotificacionService;
import mx.uam.ayd.proyecto.negocio.modelo.ResumenNotificacion;
import org.springframework.stereotype.Component;

/**
 * Llama al servicio para enviar la notificación y muestra el pop-up de confirmación.
 */
@Component
@RequiredArgsConstructor
public class ControlFinalizacionPedido {

    private final NotificacionService servicioNotificacion;          // negocio HU-05
    private final VentanaFinalizacionPedido vista = new VentanaFinalizacionPedido(); // pop-up

    /**
     * Punto de entrada desde la UI (por ejemplo, acción del botón "Finalizar pedido").
     *
     * @param idPedido        número de pedido generado
     * @param tipoEntrega     "TIENDA" o "DOMICILIO"
     * @param telefonoDestino número al que se enviará la notificación
     * @param canalPreferido  "WHATSAPP" o "SMS"
     */
    public void mostrarConfirmacionYNotificar(Long idPedido,String tipoEntrega,String telefonoDestino,String canalPreferido) {
        try {
            // Llegamos aquí solo cuando el pedido ya quedó finalizado en el flujo actual
            boolean pedidoFinalizado = true;

            // Negocio: enviar (simulado) + auditar
            ResumenNotificacion resumen = servicioNotificacion.enviarNotificacionPedido(idPedido, tipoEntrega, telefonoDestino, canalPreferido, pedidoFinalizado);

            // Vista: mostrar confirmación
            vista.mostrar(resumen.getIdPedido(), resumen.getEstado(), resumen.getTipoEntrega());

        } catch (Exception ex) {
            // Vista: mostrar error si algo falla
            vista.mostrarError("No se pudo notificar: " + ex.getMessage());
        }
    }
}
