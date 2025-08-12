package mx.uam.ayd.proyecto.presentacion.finalizacionNotificacion;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.NotificacionService;
import mx.uam.ayd.proyecto.negocio.modelo.ResumenNotificacion;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Controlador de orquestación para la pantalla de finalización de pedido.
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Invocar al servicio de notificaciones para registrar la confirmación del pedido.</li>
 *   <li>Cargar y mostrar la ventana (FXML) de confirmación al usuario.</li>
 * </ul>
 * <p>
 * Usa JavaFX para la interfaz y Spring para inyección de dependencias (controllerFactory).
 */
@Component
@RequiredArgsConstructor
public class ControlFinalizacionPedido {

    // Servicio de dominio que registra y resume la notificación de confirmación
    private final NotificacionService servicioNotificacion;

    // Contexto de Spring para permitir que el FXMLLoader cree controladores gestionados por Spring
    private final ApplicationContext applicationContext;

    /**
     * Registra la confirmación del pedido (HU-05/HU-10) y muestra una ventana modal con el resumen.
     *
     * @param idPedido        identificador del pedido a confirmar
     * @param tipoEntrega     tipo de entrega (p.ej. "domicilio" o "tienda")
     * @param telefonoDestino teléfono de destino para la notificación
     * @param canalPreferido  canal preferido (String compatible con enum Canal)
     */
    public void mostrarConfirmacionYNotificar(
            Long idPedido, String tipoEntrega, String telefonoDestino, String canalPreferido) {
        try {
            // En este flujo, la finalización del pedido se asume verdadera
            boolean pedidoFinalizado = true;

            // Llamada al servicio de negocio: registra la notificación y devuelve un resumen
            ResumenNotificacion resumen = servicioNotificacion.enviarNotificacionPedido(
                    idPedido, tipoEntrega, telefonoDestino, canalPreferido, pedidoFinalizado);

            // Asegura que la creación/mostrar de la UI ocurra en el hilo de JavaFX
            Platform.runLater(() -> {
                try {
                    // Carga del FXML de la ventana de finalización
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/finalizacion-pedido.fxml"));

                    // Permite que el controlador del FXML sea un bean de Spring
                    loader.setControllerFactory(applicationContext::getBean);

                    // Instancia la jerarquía de nodos definida en el FXML
                    Parent root = loader.load();

                    // Obtiene el controlador ya gestionado por Spring para pasarle datos
                    VentanaFinalizacionPedidoController controller = loader.getController();
                    controller.setResumen(resumen.getIdPedido(), resumen.getEstado(), resumen.getTipoEntrega());

                    // Crea y configura la ventana modal (bloquea la ventana padre mientras está activa)
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Pedido confirmado");
                    stage.setScene(new Scene(root));
                    stage.show(); // Muestra la ventana (no bloqueante; para bloquear usar showAndWait)
                } catch (Exception e) {
                    // Traza para depuración si ocurre un error al cargar/mostrar la UI
                    e.printStackTrace();
                }
            });

        } catch (Exception ex) {
            // Mensaje de error simple en consola si la notificación no pudo registrarse
            System.err.println("No se pudo notificar: " + ex.getMessage());
        }
    }
}


