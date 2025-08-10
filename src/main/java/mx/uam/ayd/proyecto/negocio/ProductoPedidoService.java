package mx.uam.ayd.proyecto.negocio;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los productos dentro de un pedido.
 */

@Service
@RequiredArgsConstructor

public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    /**
     * Registra un nuevo productoPedido en el sistema.
     *
     * @param productoPedido el productoPedido a registrar
     * @return el productoPedido guardado
     */

    public ProductoPedido registrarProductoPedido(ProductoPedido productoPedido) {

        return productoPedidoRepository.save(productoPedido);

    }

    /**
     * Obtiene un productoPedido por su ID.
     *
     * @param idProductoPedido identificador del productoPedido
     * @return el productoPedido si existe
     */

    public Optional<ProductoPedido> obtenerProductoPedidoPorId(Long idProductoPedido) {

        return productoPedidoRepository.findById(idProductoPedido);

    }

    /**
     * Lista todos los productosPedido registrados.
     *
     * @return lista de productosPedido
     */

    public List<ProductoPedido> listarProductosPedido() {

        return (List<ProductoPedido>) productoPedidoRepository.findAll();

    }
}
=======
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

@Service
public class ProductoPedidoService {

    @Autowired
    private ProductoPedidoRepository productoPedidoRepository;

    private String nota;

    // Obtener productos en el carrito (desde la base)
    public List<ProductoPedido> obtenerProductosDelCarrito() {
        return (List<ProductoPedido>) productoPedidoRepository.findAll();
    }

    // Agregar producto y guardar en la base
    public void agregarProducto(ProductoPedido producto) {
        productoPedidoRepository.save(producto);
    }

    // Eliminar producto del carrito y base
    public void eliminarProducto(ProductoPedido producto) {
        productoPedidoRepository.delete(producto);
    }

    // Actualizar peso de producto y guardar en base
    public boolean actualizarPesoProducto(ProductoPedido producto, float nuevoPeso) {
        if (nuevoPeso <= 0) {
            return false;
        }
        producto.setPeso(nuevoPeso);
        productoPedidoRepository.save(producto);
        return true;
    }

    // Calcular subtotal de un producto
    public float calcularSubtotal(ProductoPedido producto) {
        return producto.calcularSubtotal();
    }

    // Calcular total del carrito
    public float calcularTotal() {
        float total = 0;
        for (ProductoPedido p : obtenerProductosDelCarrito()) {
            total += calcularSubtotal(p);
        }
        return total;
    }

    // Agregar nota al pedido
    public void agregarNota(String nota) {
        if (nota.length() <= 200) {
            this.nota = nota;
        }
    }

    public String obtenerNota() {
        return nota;
    }
}
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
