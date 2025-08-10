package mx.uam.ayd.proyecto.presentacion.asignarMetodoEntrega;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class VentanaMetodoEntrega {

    @FXML private ComboBox<String> comboMetodos;
    @FXML private Button btnConfirmar;

    private ControlAsignarMetodoEntrega control;
    private Stage stage;

    public void setControl(ControlAsignarMetodoEntrega control) {
        this.control = control;
    }

    /**
     * Muestra el modal con las opciones de entrega.
     */
    public void muestra(List<String> metodos) {
        Runnable show = () -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ventana-metodo-entrega.fxml"));
                loader.setController(this);
                Scene scene = new Scene(loader.load(), 360, 160);

                stage = new Stage();
                stage.setTitle("Asignar método de entrega");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);

                comboMetodos.setItems(FXCollections.observableArrayList(metodos));
                btnConfirmar.setOnAction(e -> {
                    if (control != null) control.asignarMetodo();
                });

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        if (Platform.isFxApplicationThread()) {
            show.run();
        } else {
            Platform.runLater(show);
        }
    }

    public String obtenerSeleccion() {
        return comboMetodos != null ? comboMetodos.getValue() : null;
    }

    public void cerrarVentana() {
        if (stage != null) {
            stage.close();
        }
    }
}

