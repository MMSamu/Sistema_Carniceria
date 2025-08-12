package mx.uam.ayd.proyecto.presentacion.checklist;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ControlChecklist {

    @FXML private TableView<ProductoPedido> tablaItems;
    @FXML private TableColumn<ProductoPedido, String>     colNombre;
    @FXML private TableColumn<ProductoPedido, Integer>    colCantidad;
    @FXML private TableColumn<ProductoPedido, BigDecimal> colPrecio;
    @FXML private TableColumn<ProductoPedido, BigDecimal> colSubtotal;

    @FXML private Label lblMetodoEntrega;
    @FXML private Label lblSubtotal;
    @FXML private Label lblEnvio;
    @FXML private Label lblTotal;

    @FXML private ButtonType btnEditar;
    @FXML private ButtonType btnConfirmar;

    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

    /**
     * Carga la información del checklist (entidades directamente).
     */
    public void setData(List<ProductoPedido> items,
                        String tipoEntrega,
                        BigDecimal subtotal,
                        BigDecimal envio,
                        BigDecimal total) {

        inicializarColumnas();
        tablaItems.getItems().setAll(items);

        lblMetodoEntrega.setText(tipoEntrega != null ? tipoEntrega : "—");
        lblSubtotal.setText(formatear(subtotal));
        lblEnvio.setText(formatear(envio));
        lblTotal.setText(formatear(total));
    }

    private void inicializarColumnas() {
        if (colNombre.getCellValueFactory() == null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        if (colCantidad.getCellValueFactory() == null) {
            colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        }
        if (colPrecio.getCellValueFactory() == null) {
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        }
        if (colSubtotal.getCellValueFactory() == null) {
            colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        }

        colPrecio.setCellFactory(new Callback<TableColumn<ProductoPedido, BigDecimal>, TableCell<ProductoPedido, BigDecimal>>() {
            @Override
            public TableCell<ProductoPedido, BigDecimal> call(TableColumn<ProductoPedido, BigDecimal> column) {
                return new TableCell<ProductoPedido, BigDecimal>() {
                    @Override
                    protected void updateItem(BigDecimal value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setText(null);
                        } else {
                            setText(formatear(value));
                        }
                    }
                };
            }
        });

        colSubtotal.setCellFactory(new Callback<TableColumn<ProductoPedido, BigDecimal>, TableCell<ProductoPedido, BigDecimal>>() {
            @Override
            public TableCell<ProductoPedido, BigDecimal> call(TableColumn<ProductoPedido, BigDecimal> column) {
                return new TableCell<ProductoPedido, BigDecimal>() {
                    @Override
                    protected void updateItem(BigDecimal value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setText(null);
                        } else {
                            setText(formatear(value));
                        }
                    }
                };
            }
        });
    }

    public boolean esEditar(ButtonType bt) {
        return bt != null && "Editar".equals(bt.getText());
    }

    public boolean esConfirmar(ButtonType bt) {
        return bt != null && "Confirmar y pagar".equals(bt.getText());
    }
    private String formatear(BigDecimal v) {
        return money.format(v == null ? BigDecimal.ZERO : v);
    }

}