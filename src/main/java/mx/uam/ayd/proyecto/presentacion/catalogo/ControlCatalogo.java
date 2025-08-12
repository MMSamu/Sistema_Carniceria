package mx.uam.ayd.proyecto.presentacion.catalogo;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.ProductoService;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ControlCatalogo {

    private final ProductoService productoService;

    // controles del FXML (deben coincidir con catalogo.fxml)
    @FXML private TextField txtBusqueda;
    @FXML private ComboBox<String> cmbTipoCorte;
    @FXML private CheckBox chkMenudeo;
    @FXML private CheckBox chkCongelado;

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colPrecio;
    @FXML private TableColumn<Producto, String> colDisponibilidad;
    @FXML private TableColumn<Producto, String> colOferta;

    // se ejecuta al cargar el FXML
    @FXML
    private void initialize() {
        // Configurar columnas
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colPrecio.setCellValueFactory(d -> {
            Producto p = d.getValue();
            double precio = (p.isEnOferta() && p.getPrecioOferta() > 0)
                    ? p.getPrecioOferta()
                    : p.getPrecio();
            return new SimpleStringProperty(String.format("$ %.2f", precio));
        });
        colDisponibilidad.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getCantidadDisponible())));
        colOferta.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().isEnOferta() ? "Sí" : "No"));

        // al abrir: cargar todo el inventario (sin filtros)
        cargarTodo();
    }

    // botón filtrar
    @FXML
    private void onFiltrar() {
        recargarTabla();
    }

    // botón cerrar
    @FXML
    private void onCerrar() {
        if (txtBusqueda != null && txtBusqueda.getScene() != null) {
            txtBusqueda.getScene().getWindow().hide();
        }
    }

    // mostrar TODO (sin filtros) al abrir
    private void cargarTodo() {
        List<Producto> lista = productoService.obtenerProductosFiltrados(null, null, null, null);
        tblProductos.getItems().setAll(lista);
    }

    // aplicar filtros
    private void recargarTabla() {
        String q = (txtBusqueda != null) ? txtBusqueda.getText() : null;
        String tipo = (cmbTipoCorte != null) ? cmbTipoCorte.getValue() : null;
        Boolean menudeo = (chkMenudeo != null && chkMenudeo.isSelected()) ? Boolean.TRUE : null;
        Boolean congelado = (chkCongelado != null && chkCongelado.isSelected()) ? Boolean.TRUE : null;

        List<Producto> lista = productoService.obtenerProductosFiltrados(q, tipo, menudeo, congelado);
        tblProductos.getItems().setAll(lista);
    }

    // metodo que usa VentanaCatalogo
    public List<Producto> obtenerProductos(
            String textoBusqueda,
            String tipoCorte,
            Boolean esMenudeo,
            Boolean esCongelado) {

        return productoService.obtenerProductosFiltrados(
                textoBusqueda, tipoCorte, esMenudeo, esCongelado
        );
    }
}
