package mx.uam.ayd.proyecto.presentacion.pedidosCarnicero;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import java.util.List;

/**
 * HU-12: Vista para el carnicero.
 * - Muestra lista de pedidos "en proceso" ordenados por hora.
 * - Permite ver el detalle (productos, notas, entrega/pago).
 * - Acciones: "Pedido listo" y "Listo para entregar" (habilitación según estado).
 * Compatible con HU-06: muestra tipoEntrega, metodoPago y dirección si existe.
 */
public class VentanaPedidosCarnicero {

    private final ControlPedidosCarnicero control;
    private final Stage stage = new Stage();

    // Componentes UI
    private final ListView<Pedido> lst = new ListView<>();
    private final TextArea detalle = new TextArea();
    private final Button btnListo = new Button("Marcar PEDIDO LISTO");
    private final Button btnEntregar = new Button("Marcar LISTO PARA ENTREGAR");
    private final Label badge = new Label("0");
    private final TextField txtBuscar = new TextField();

    private final ObservableList<Pedido> modelo = FXCollections.observableArrayList();

    public VentanaPedidosCarnicero(ControlPedidosCarnicero control) {
        this.control = control;
        construirUI();
        wireEvents();
    }



    private void construirUI() {
        stage.setTitle("HU-12 · Pedidos pendientes (Carnicero)");

        // lista izquierda
        lst.setItems(modelo);
        lst.setCellFactory(v -> new ListCell<>() {
            @Override protected void updateItem(Pedido p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setText(null);
                } else {
                    String estado = p.getEstado() == null ? "" : p.getEstado();
                    setText("Pedido #" + p.getIdPedido() + "  |  " + p.getHora() + "  |  " + estado);
                }
            }
        });

        // panel de detalle
        detalle.setEditable(false);
        detalle.setWrapText(true);

        // contador + búsqueda
        txtBuscar.setPromptText("Buscar por # de pedido… (Enter)");
        Label lblPend = new Label("Pendientes:");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox top = new HBox(12, lblPend, badge, spacer, new Label("Buscar:"), txtBuscar);
        top.setPadding(new Insets(10));

        // acciones abajo
        HBox acciones = new HBox(10, btnListo, btnEntregar);
        acciones.setPadding(new Insets(10));

        // centro dividido
        SplitPane split = new SplitPane(lst, detalle);
        split.setDividerPositions(0.38);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(split);
        root.setBottom(acciones);

        stage.setScene(new Scene(root, 1000, 600));
    }

    private void wireEvents() {
        // al seleccionar pedido en la lista
        lst.getSelectionModel().selectedItemProperty().addListener((obs, oldV, sel) -> {
            if (sel != null) {
                detalle.setText(formatear(sel));
                actualizarHabilitados(sel);
            }
        });

        // botón: PEDIDO LISTO
        btnListo.setOnAction(e -> {
            Pedido p = lst.getSelectionModel().getSelectedItem();
            if (p == null) { alerta("Selecciona un pedido."); return; }
            try {
                control.marcarPedidoListo(p.getIdPedido());
            } catch (Exception ex) {
                alerta(ex.getMessage());
            }
        });

        // botón: LISTO PARA ENTREGAR
        btnEntregar.setOnAction(e -> {
            Pedido p = lst.getSelectionModel().getSelectedItem();
            if (p == null) { alerta("Selecciona un pedido."); return; }
            try {
                control.marcarListoParaEntregar(p.getIdPedido());
            } catch (Exception ex) {
                alerta(ex.getMessage());
            }
        });

        // buscar por número de pedido (puede no estar en la lista si no está "en proceso")
        txtBuscar.setOnAction(e -> {
            String s = txtBuscar.getText().trim();
            if (s.isEmpty()) return;
            try {
                long id = Long.parseLong(s);
                Pedido p = control.obtenerDetalles(id);
                detalle.setText(formatear(p));
                actualizarHabilitados(p);
            } catch (NumberFormatException nfe) {
                alerta("Escribe un número de pedido válido.");
            } catch (Exception ex) {
                alerta("No se encontró el pedido #" + s);
            }
        });
    }

    // API desde control

    // el control llama esto para refrescar la lista de pendientes
    public void mostrarListaPedidos(List<Pedido> lista) {
        Platform.runLater(() -> {
            modelo.setAll(lista);
            badge.setText(String.valueOf(lista.size()));
            if (!modelo.isEmpty()) {
                lst.getSelectionModel().selectFirst();
            } else {
                detalle.setText("No hay pedidos 'en proceso' por ahora.");
                btnListo.setDisable(true);
                btnEntregar.setDisable(true);
            }
        });
    }

    // trae al frente la ventana
    public void mostrar() {
        if (!stage.isShowing()) stage.show(); else stage.toFront();
    }

    // el control la usa tras una acción para re-pintar el detalle actual
    public void mostrarDetalles(long id) {
        try {
            Pedido p = control.obtenerDetalles(id);
            detalle.setText(formatear(p));
            actualizarHabilitados(p);
        } catch (Exception ex) {
            alerta(ex.getMessage());
        }
    }

    // helpers

    private void actualizarHabilitados(Pedido p) {
        String estado = p.getEstado() == null ? "" : p.getEstado().toLowerCase();
        btnListo.setDisable(!"en proceso".equals(estado));
        btnEntregar.setDisable(!"pedido listo".equals(estado));
    }

    private String formatear(Pedido p) {
        StringBuilder sb = new StringBuilder();

        sb.append("Pedido #").append(p.getIdPedido()).append("\n");
        sb.append("Fecha/Hora: ").append(p.getFecha()).append(" ").append(p.getHora()).append("\n");
        if (p.getEstado() != null) sb.append("Estado: ").append(p.getEstado()).append("\n");

        // HU-06: entrega/pago/dirección
        if (p.getTipoEntrega() != null) sb.append("Entrega: ").append(p.getTipoEntrega()).append("\n");
        if (p.getMetodoPago() != null) sb.append("Pago: ").append(p.getMetodoPago()).append("\n");
        if (p.getDireccionEntrega() != null) {
            sb.append("Dirección: ")
                    .append(p.getDireccionEntrega().getCalle()).append(" ")
                    .append(p.getDireccionEntrega().getNumero()).append(", ")
                    .append(p.getDireccionEntrega().getColonia()).append(", ")
                    .append(p.getDireccionEntrega().getCiudad()).append("  CP ")
                    .append(p.getDireccionEntrega().getCodigoPostal()).append("\n");
        }

        // notas/observaciones destacadas
        if (p.getObservaciones() != null && !p.getObservaciones().isBlank()) {
            sb.append("\n*** NOTAS DEL CLIENTE ***\n").append(p.getObservaciones()).append("\n");
        }

        // detalle de productos
        sb.append("\nProductos:\n");
        if (p.getItems() != null && !p.getItems().isEmpty()) {
            for (ProductoPedido it : p.getItems()) {
                StringBuilder linea = new StringBuilder(" - ").append(it.getNombre());

                if (it.getCantidad() > 0) {
                    linea.append("  x").append(it.getCantidad());
                } else if (it.getPeso() > 0f) {
                    linea.append(" (").append(String.format("%.2f", it.getPeso())).append(" kg)");
                }

                if (it.getPrecio() > 0f) {
                    linea.append("  $").append(String.format("%.2f", it.getPrecio()));
                }

                sb.append(linea).append("\n");
            }
        } else {
            sb.append(" (sin items)\n");
        }

        return sb.toString();
    }

    private void alerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
