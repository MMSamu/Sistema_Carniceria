package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class VentanaCarrito {

    // Referencia al control del caso de uso (se la inyectamos desde ControlCarrito)
    private ControlCarrito control;

    // TODO: Cuando tengas una tabla/lista, toma el id seleccionado de ahí.
    // Por ahora, para probar, usa un id fijo o expón un setter.
    private long productoPedidoIdSeleccionado = 1L; // <-- cambia al id real

    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReducir;
    @FXML
    private Button btnAumentar;

    void setControl(ControlCarrito control) {
        this.control = control;
    }

    // Handlers: delegan al control (presentación) -> servicio -> repositorio
    @FXML
    private void onEliminar() {
        if (control != null)
            control.onEliminar(productoPedidoIdSeleccionado);
    }

    @FXML
    private void onReducir() {
        if (control != null)
            control.onReducir(productoPedidoIdSeleccionado);
    }

    @FXML
    private void onAumentar() {
        if (control != null)
            control.onAumentar(productoPedidoIdSeleccionado);
    }

    // Opcional: cuando selecciones otro renglón en la tabla, actualiza este id:
    public void setProductoPedidoIdSeleccionado(long id) {
        this.productoPedidoIdSeleccionado = id;
    }
}