package mx.uam.ayd.proyecto.presentacion.checklist;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Window;
import javafx.util.Callback;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Ventana modal de revisión (checklist) antes de confirmar la compra.
 * Usa ControlChecklist y el FXML checklist_pedido.fxml.
 */
public class VentanaChecklist {

    public enum Resultado { EDITAR, CONFIRMAR, CANCELAR }

    /**
     * Muestra la ventana checklist.
     *
     * @param owner        ventana padre (puede ser null)
     * @param items        productos del pedido
     * @param tipoEntrega  texto del método de entrega
     * @param subtotal     suma de subtotales
     * @param envio        costo de envío
     * @param total        subtotal + envío
     */
    public Optional<Resultado> mostrar(Window owner,
                                       List<ProductoPedido> items,
                                       String tipoEntrega,
                                       BigDecimal subtotal,
                                       BigDecimal envio,
                                       BigDecimal total) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checklist_pedido.fxml"));
        DialogPane pane = loader.load();

        ControlChecklist ctrl = loader.getController();
        ctrl.setData(items, tipoEntrega, subtotal, envio, total);

        Dialog<Resultado> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        if (owner != null) {
            dialog.initOwner(owner);
        }

        dialog.setResultConverter(new Callback<ButtonType, Resultado>() {
            @Override
            public Resultado call(ButtonType bt) {
                if (ctrl.esConfirmar(bt)) {
                    return Resultado.CONFIRMAR;
                }
                if (ctrl.esEditar(bt)) {
                    return Resultado.EDITAR;
                }
                return Resultado.CANCELAR;
            }
        });

        return dialog.showAndWait();
    }
}
