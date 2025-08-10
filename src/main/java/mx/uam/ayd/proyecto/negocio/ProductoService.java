package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de productos.
 */
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Registra un nuevo producto.
     */
    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Obtiene un producto por su id.
     */
    public Optional<Producto> obtenerProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto);
    }

    /**
     * Iterable -> List para compatibilidad con CrudRepository.
     */
    public List<Producto> listarProductos() {
        List<Producto> list = new ArrayList<>();
        productoRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Elimina un producto por su id.
     */
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }
}

