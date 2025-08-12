package mx.uam.ayd.proyecto.presentacion.pago;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Window;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.PagoService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ControlPago {

    private final PagoService pagoService;

    // Campos vinculados al FXML
    @FXML private TextField txtTelefono, txtNombre, txtApellido, txtNumProductos;
    @FXML private RadioButton rbEfectivo, rbTransferencia;
    @FXML private Label lblSubtotal, lblTotal;
    @FXML private ToggleGroup grupoPago;

    @FXML
    private void initialize() {
        // Si en el FXML no se definió <fx:define><ToggleGroup .../></fx:define>
        if (grupoPago == null) {
            grupoPago = new ToggleGroup();
            rbEfectivo.setToggleGroup(grupoPago);
            rbTransferencia.setToggleGroup(grupoPago);
        }
        if (!rbEfectivo.isSelected() && !rbTransferencia.isSelected()) {
            rbEfectivo.setSelected(true);
        }

        // Filtro: solo dígitos para "Número de productos"
        txtNumProductos.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                txtNumProductos.setText(newV.replaceAll("\\D", ""));
            }
        });

        // Filtro: solo números y +, espacios y guiones para teléfono (opcional)
        txtTelefono.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("[0-9+\\-\\s]*")) {
                txtTelefono.setText(newV.replaceAll("[^0-9+\\-\\s]", ""));
            }
        });
    }

    /** Permite que la ventana que carga el FXML muestre importes. */
    public void setResumen(double subtotal, double total) {
        lblSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    /**
     * Registra el pago y (si no exixte ) da de alta al cliene por el sistema
     */
    public boolean registrarPago(String nombre, String apellido, String telefono, String metodo){
        if (nombre == null || nombre.isBlank()) return false;
        if (apellido == null || apellido.isBlank()) return false;
        if (telefono == null || telefono.isBlank()) return false;
        if (metodo == null || metodo.isBlank()) return false;

        return pagoService.registrarPago(nombre.trim(), apellido.trim(), telefono.trim(), metodo.trim());
    }



    /**
     * Handler del botón "Finalizar el pedido".
     */
    @FXML
    private void onFinalizarPedido() {
        String telefono = txtTelefono.getText().trim();
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String numProd  = txtNumProductos.getText().trim();

        if (telefono.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || numProd.isEmpty()) {
            mostrarAlerta("Faltan datos", "Completa todos los campos.");
            return;
        }

        if (!esTelefonoValido(telefono)) {
            mostrarAlerta("Teléfono inválido", "Ingresa un número de teléfono válido (solo dígitos, +, guiones o espacios).");
            return;
        }

        try {
            int n = Integer.parseInt(numProd);
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Dato inválido", "El número de productos debe ser un entero positivo.");
            return;
        }

        String metodo = rbEfectivo.isSelected() ? "Efectivo" : "Transferencia";
        boolean ok = registrarPago(nombre, apellido, telefono, metodo);

        if (ok) {
            mostrarAlerta("✅ Pedido finalizado", "Pago registrado correctamente.");

            Window w = lblTotal.getScene() != null ? lblTotal.getScene().getWindow() : null;
            if (w != null) w.hide();

        } else {

            mostrarAlerta("❌ Error", "No se pudo registrar el pago.");
        }
    }

    private boolean esTelefonoValido(String t) {
        // Acepta dígitos, espacios, + y guiones; longitud mínima 7
        String limpio = t.replaceAll("[\\s\\-]", "");
        return limpio.matches("\\+?\\d{7,15}");
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }



}
