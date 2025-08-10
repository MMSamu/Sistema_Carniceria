package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la relación Producto-Pedido.
 */
@Service
@RequiredArgsConstructor
public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    /**
     * Registra una nueva relación Producto-Pedido.
     */
    public ProductoPedido registrarProductoPedido(ProductoPedido productoPedido) {
        return productoPedidoRepository.save(productoPedido);
    }

    /**
     * Obtiene una relación Producto-Pedido por su id.
     */
    public Optional<ProductoPedido> obtenerProductoPedidoPorId(Long id) {
        return productoPedidoRepository.findById(id);
    }

    /**
     * Iterable -> List para compatibilidad con CrudRepository.
     */
    public List<ProductoPedido> listarProductosPedido() {
        List<ProductoPedido> list = new ArrayList<>();
        productoPedidoRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Elimina una relación Producto-Pedido por su id.
     */
    public void eliminarProductoPedido(Long id) {
        productoPedidoRepository.deleteById(id);
    }

    /*  Métodos de carrito (compatibilidad con la UI; opcional)
    private final List<ProductoPedido> carrito = new ArrayList<>();
    private String nota;

    public List<ProductoPedido> obtenerProductosDelCarrito() { return new ArrayList<>(carrito); }
    public void agregarProducto(ProductoPedido pp) { if (pp != null) carrito.add(pp); }
    public void eliminarProducto(ProductoPedido pp) { carrito.remove(pp); }
    public void actualizarPesoProducto(ProductoPedido pp, float nuevoPeso) {
        if (pp != null) { pp.setPeso(nuevoPeso); if (pp.getPedido()!=null) pp.getPedido().recalcularTotal(); }
    }
    public java.math.BigDecimal calcularTotal() {
        return carrito.stream().map(ProductoPedido::getSubtotal)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
    public void agregarNota(String n) { this.nota = n; }
    public String obtenerNota() { return this.nota; }
    */
}

