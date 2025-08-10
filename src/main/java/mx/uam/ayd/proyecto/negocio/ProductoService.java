package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los productos.
 */
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Registra un nuevo producto en el sistema.
     * @param producto entidad Producto a registrar
     * @return producto registrado
     */
  
    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Obtiene un producto por su identificador.
     * @param idProducto identificador del producto
     * @return producto si existe
     */
  
    public Optional<Producto> obtenerProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto);
    }

    /**
     * Obtiene todos los productos registrados.
     * @return lista de productos
     */
  
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    /**
     * Elimina un producto por su identificador.
     * @param idProducto identificador del producto
     */
  
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }
}
