package mx.uam.ayd.proyecto.negocio;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los productos del inventario.
 */

@Service
@RequiredArgsConstructor

public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Registra un nuevo producto en el inventario.
     *
     * @param producto producto a registrar
     * @return producto guardado
     */

    public Producto registrarProducto(Producto producto) {

        return productoRepository.save(producto);

    }

    /**
     * Busca un producto por su ID.
     *
     * @param idProducto identificador del producto
     * @return un Optional que puede contener el producto si existe
     */

    public Optional<Producto> obtenerProductoPorId(Long idProducto) {

        return productoRepository.findById(idProducto);

    }

    /**
     * Devuelve la lista completa de productos registrados.
     *
     * @return lista de productos
     */

    public List<Producto> listarProductos() {

        return (List<Producto>) productoRepository.findAll();

    }

    /**
     * Actualiza la cantidad disponible de un producto.
     *
     * @param idProducto identificador del producto
     * @param nuevaCantidad nueva cantidad disponible
     * @return true si se actualizó correctamente, false si no se encontró el producto
     */

    public boolean actualizarStock(Long idProducto, int nuevaCantidad) {

        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setCantidadDisponible(nuevaCantidad);
            productoRepository.save(producto);
            return true;
        }
        return false;
    }
=======
public class ProductoService {
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
}
