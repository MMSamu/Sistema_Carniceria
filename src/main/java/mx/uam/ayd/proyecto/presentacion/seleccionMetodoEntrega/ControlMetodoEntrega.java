package mx.uam.ayd.proyecto.presentacion.seleccionMetodoEntrega;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
/**
 * Controlador del modal de selección de método de entrega (HU-03).
 * Se enlaza al FXML ventana-seleccionar-entregas.fxml.
 */
public class ControlMetodoEntrega {

    @FXML
    private ChoiceBox<String> choiceMetodoEntrega;

    /**
     * -- SETTER --
     * Inyectado por la vista para poder cerrar el modal.
     */
    @Setter
    private Stage stage;                           // lo asigna la vista
    private Optional<String> seleccionado = Optional.empty();

    /** Carga los métodos en el ChoiceBox. */

    public void cargarMetodosEntrega(List<String> metodos) {
        choiceMetodoEntrega.getItems().setAll(metodos);
        if (!metodos.isEmpty()) {
            choiceMetodoEntrega.getSelectionModel().selectFirst();
        }
    }

    /** Devuelve el método elegido (vacío si el usuario canceló). */

    public Optional<String> getMetodoSeleccionado() {
        return seleccionado;
    }

    /* Acciones de UI */

    @FXML
    private void onAceptar(ActionEvent e) {

        seleccionado = Optional.ofNullable(
                choiceMetodoEntrega.getSelectionModel().getSelectedItem()
        );
        if (stage != null) stage.close();

    }

    @FXML

    private void onCancelar(ActionEvent e) {

        seleccionado = Optional.empty();
        if (stage != null) stage.close();

    }
}
