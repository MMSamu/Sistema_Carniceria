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

@Component
@RequiredArgsConstructor
public class ControlFinalizacionPedido {

    private final NotificacionService servicioNotificacion;
    private final ApplicationContext applicationContext;

    public void mostrarConfirmacionYNotificar(
            Long idPedido, String tipoEntrega, String telefonoDestino, String canalPreferido) {
        try {
            boolean pedidoFinalizado = true;

            ResumenNotificacion resumen = servicioNotificacion.enviarNotificacionPedido(
                    idPedido, tipoEntrega, telefonoDestino, canalPreferido, pedidoFinalizado);

            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/finalizacion-pedido.fxml"));
                    loader.setControllerFactory(applicationContext::getBean);
                    Parent root = loader.load();

                    VentanaFinalizacionPedidoController controller = loader.getController();
                    controller.setResumen(resumen.getIdPedido(), resumen.getEstado(), resumen.getTipoEntrega());

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Pedido confirmado");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception ex) {
            System.err.println("No se pudo notificar: " + ex.getMessage());
        }
    }
}
