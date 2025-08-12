package mx.uam.ayd.proyecto.presentacion.finalizacionNotificacion;

import jakarta.annotation.PostConstruct;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VentanaFinalizacionPedidoController {

    @FXML private Label lblIdPedido;
    @FXML private Label lblEstado;
    @FXML private Label lblTipoEntrega;

    @PostConstruct
    private void init() {
        // Si necesitas inicializar algo, aquí.
    }

    /** La usa el control para inyectar los datos que ya calculó el servicio. */
    public void setResumen(Long idPedido, String estado, String tipoEntrega) {
        lblIdPedido.setText("Pedido: #" + idPedido);
        lblEstado.setText("Estado: " + estado);
        lblTipoEntrega.setText("Entrega: " + tipoEntrega);
    }

    @FXML
    private void onCerrar() {
        // Cierra la ventana actual
        Stage stage = (Stage) lblIdPedido.getScene().getWindow();
        stage.close();
    }
}