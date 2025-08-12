package mx.uam.ayd.proyecto.presentacion.rutaRepartidor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import mx.uam.ayd.proyecto.negocio.RutaRepartidorService;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Controlador de la ventana "ruta_repartidor.fxml".
 * Muestra pedidos a entregar con columnas: id, tipoEntrega, items y total.
 * - Sin lambdas
 * - PropertyValueFactory con genéricos explícitos (evita unchecked warnings)
 */
@Component
public class ControlRutaRepartidor {

    // La tabla está tipada a RowPedido (proyección para la vista)
    @FXML private TableView<RowPedido> tablaPedidos;
    @FXML private TableColumn<RowPedido, Long>       colId;
    @FXML private TableColumn<RowPedido, String>     colTipo;
    @FXML private TableColumn<RowPedido, Integer>    colItems;
    @FXML private TableColumn<RowPedido, BigDecimal> colTotal;

    @FXML private Label lblInfo;

    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

    private RutaRepartidorService rutaService;
    private Long idRepartidor; // asígnalo al iniciar sesión del repartidor, por ejemplo

    public ControlRutaRepartidor() {
        // ctor vacío requerido por JavaFX
    }

    @Autowired
    public void setRutaService(RutaRepartidorService rutaService) {
        this.rutaService = rutaService;
    }

    public void setIdRepartidor(Long idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    @FXML
    private void initialize() {
        // PropertyValueFactory con genéricos explícitos (evita "unchecked assignment")
        colId.setCellValueFactory(new PropertyValueFactory<RowPedido, Long>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<RowPedido, String>("tipoEntrega"));
        colItems.setCellValueFactory(new PropertyValueFactory<RowPedido, Integer>("items"));
        colTotal.setCellValueFactory(new PropertyValueFactory<RowPedido, BigDecimal>("total"));

        // Formato monetario en la columna Total (sin lambdas)
        colTotal.setCellFactory(new Callback<TableColumn<RowPedido, BigDecimal>, TableCell<RowPedido, BigDecimal>>() {
            @Override
            public TableCell<RowPedido, BigDecimal> call(TableColumn<RowPedido, BigDecimal> column) {
                return new TableCell<RowPedido, BigDecimal>() {
                    @Override
                    protected void updateItem(BigDecimal value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setText(null);
                        } else {
                            setText(money.format(value));
                        }
                    }
                };
            }
        });
    }

    /**
     * Carga/recarga la tabla con los pedidos del repartidor.
     */
    public void cargarDatos() {
        if (rutaService == null) {
            lblInfo.setText("Servicio no inicializado.");
            return;
        }

        List<Pedido> pedidos = rutaService.obtenerPedidosParaRepartidor(idRepartidor);
        List<RowPedido> filas = new ArrayList<RowPedido>();

        for (Pedido p : pedidos) {
            Long id = obtenerIdPedido(p);
            int items = rutaService.contarItemsPedido(id);
            BigDecimal total = rutaService.calcularTotalPedido(id);
            String tipo = p.getTipoEntrega();
            filas.add(new RowPedido(id, tipo, items, total));
        }

        tablaPedidos.getItems().setAll(filas);
        lblInfo.setText("Pedidos cargados: " + filas.size());
    }

    // ==== Handlers de botones (defínelos en el FXML) ====

    @FXML
    private void onActualizar() {
        cargarDatos();
    }

    @FXML
    private void onVerDetalle() {
        RowPedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un pedido para ver el detalle.");
            return;
        }
        Long id = seleccionado.getId();
        // Aquí podrías abrir una ventana que liste ProductoPedido del pedido seleccionado
        mostrarAlerta("Detalle del pedido #" + id + " (abrir vista de detalle aquí).");
    }

    @FXML
    private void onCerrar() {
        // Si usas DialogPane con ButtonType Cerrar, no necesitas nada aquí.
    }


    private Long obtenerIdPedido(Pedido p) {
        try {
            return p.getIdPedido(); // si tu entidad lo expone así
        } catch (Throwable ignore) {
            try {
                return p.getIdPedido();     // tu clase Pedido tiene also getId()
            } catch (Throwable t) {
                return null;
            }
        }
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }


    public static class RowPedido {
        private final Long id;
        private final String tipoEntrega;
        private final Integer items;
        private final BigDecimal total;

        public RowPedido(Long id, String tipoEntrega, int items, BigDecimal total) {
            this.id = id;
            this.tipoEntrega = tipoEntrega;
            this.items = Integer.valueOf(items);
            this.total = total;
        }

        public Long getId() { return id; }
        public String getTipoEntrega() { return tipoEntrega; }
        public Integer getItems() { return items; }
        public BigDecimal getTotal() { return total;}
}
}