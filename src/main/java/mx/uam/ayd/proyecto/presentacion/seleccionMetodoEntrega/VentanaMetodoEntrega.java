package mx.uam.ayd.proyecto.presentacion.seleccionMetodoEntrega;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Vista modal para HU-03. Carga el FXML, inyecta el controlador,
 * muestra el diálogo y devuelve el método elegido.
 */

public class VentanaMetodoEntrega {

    private final ControlMetodoEntrega control;
    private static final String FXML_PATH = "/fxml/ventana-seleccionar-entregas.fxml"; // resources/fxml

    public VentanaMetodoEntrega(ControlMetodoEntrega control) {
        this.control = control;
    }

    /**
     * Muestra el modal y devuelve el método seleccionado.
     * @param owner   ventana padre (Stage o Window de tu escena)
     * @param metodos lista de métodos a mostrar
     */

    public Optional<String> mostrar(Window owner, List<String> metodos) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));

        Parent root = loader.load();
ControlMetodoEntrega control = loader.getController();
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Seleccionar método de entrega");
        dialog.setScene(new Scene(root));

        // Pasar contexto al controlador

        control.setStage(dialog);
        control.cargarMetodosEntrega(metodos);

        dialog.showAndWait();
        return control.getMetodoSeleccionado();

    }
}


