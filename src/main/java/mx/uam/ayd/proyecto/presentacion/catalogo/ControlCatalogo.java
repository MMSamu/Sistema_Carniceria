package mx.uam.ayd.proyecto.presentacion.catalogo;

import mx.uam.ayd.proyecto.negocio.ProductoService;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import java.util.List;

// controlador para manejar el catálogo de productos
public class ControlCatalogo {

    // servicio para operaciones con productos
    private final ProductoService productoService;

    // sonstructor
    public ControlCatalogo(ProductoService productoService) {
        this.productoService = productoService;
    }

    // metodo para obtener productos con filtros
    public List<Producto> obtenerProductos(String textoBusqueda, String tipoCorte,
                                           Boolean esMenudeo, Boolean esCongelado) {
        // llama al servicio para obtener productos filtrados
        return productoService.obtenerProductosFiltrados(textoBusqueda, tipoCorte, esMenudeo, esCongelado);
    }
}