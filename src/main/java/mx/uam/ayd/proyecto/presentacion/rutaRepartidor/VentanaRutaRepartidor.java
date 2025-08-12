package mx.uam.ayd.proyecto.presentacion.rutaRepartidor;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Window;
import org.springframework.context.ApplicationContext;
import java.io.IOException;

/**
 * Ventana modal para mostrar la ruta de entregas del repartidor.
 * Usa ControlRutaRepartidor y ruta_repartidor.fxml
 */
public class VentanaRutaRepartidor {

    private final ApplicationContext ctx;

    public VentanaRutaRepartidor(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void mostrar(Window owner, Long idRepartidor) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ruta_repartidor.fxml"));

        // Permite que Spring inyecte el controlador
        loader.setControllerFactory(ctx::getBean);

        DialogPane pane = loader.load();
        ControlRutaRepartidor ctrl = loader.getController();
        ctrl.setIdRepartidor(idRepartidor);
        ctrl.cargarDatos();

        Dialog<Void> dialog = new Dialog<Void>();
        dialog.setDialogPane(pane);
        if (owner != null) {
            dialog.initOwner(owner);
        }
        dialog.showAndWait();
}
}