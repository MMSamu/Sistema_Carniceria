package mx.uam.ayd.proyecto.presentacion.pago;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VentanaPago  {

    private final ApplicationContext applicationContext;

    /** Muestra la pantalla con los importes dados. */
    public void mostrar(double subtotal, double total) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-pago.fxml"));
                // Que el controlador (ControlPago) lo cree Spring:
                loader.setControllerFactory(applicationContext::getBean);

                Parent root = loader.load();

                ControlPago controller = loader.getController();
                controller.setResumen(subtotal, total);

                Stage stage = new Stage();
                stage.setTitle("Información adicional - Recogida en tienda");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}