package mx.uam.ayd.proyecto.presentacion.confirmarEntrega;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VistaConfirmarEntrega {

    private ControlconfirmarPedido control;

    @FXML
    private TableView<Pedido> tabla;
    @FXML
    private TableColumn<Pedido, Long> colId;
    @FXML
    private TableColumn<Pedido, String> colEstado;
    @FXML
    private TableColumn<Pedido, BigDecimal> colTotal;

    void setControl(ControlconfirmarPedido control) {
        this.control = control;
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getIdPedido()).asObject());
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
        colTotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getTotal()));
    }

    void cargar(List<Pedido> pedidos) {
        tabla.getItems().setAll(pedidos);
        if (!pedidos.isEmpty())
            tabla.getSelectionModel().selectFirst();
    }

    @FXML
    private void onConfirmar() {
        var sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null || control == null)
            return;
        control.confirmarEntrega(sel.getIdPedido());
        control.recargarLista();
    }

    @FXML
    private void onCerrar() {
        tabla.getScene().getWindow().hide();
    }
}