package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /* CRUD persistente */

    public ProductoPedido registrarProductoPedido(ProductoPedido productoPedido) {
        return productoPedidoRepository.save(productoPedido);
    }

    public Optional<ProductoPedido> obtenerProductoPedidoPorId(Long id) {
        return productoPedidoRepository.findById(id);
    }

    /** Iterable -> List para compatibilidad con CrudRepository */
    public List<ProductoPedido> listarProductosPedido() {
        List<ProductoPedido> list = new ArrayList<>();
        productoPedidoRepository.findAll().forEach(list::add);
        return list;
    }

    public void eliminarProductoPedido(Long id) {
        productoPedidoRepository.deleteById(id);
    }

    /* Carrito en memoria (compat UI) */

    private final List<ProductoPedido> carrito = new ArrayList<>();
    private String nota;

    /** Lista los renglones del carrito (copia defensiva). */
    public List<ProductoPedido> obtenerProductosDelCarrito() {
        return new ArrayList<>(carrito);
    }

    /** Agrega un renglón al carrito. La UI espera boolean. */
    public boolean agregarProducto(ProductoPedido pp) {
        if (pp == null) return false;
        carrito.add(pp);
        return true;
    }

    /** Elimina un renglón del carrito. La UI espera boolean. */
    public boolean eliminarProducto(ProductoPedido pp) {
        return carrito.remove(pp);
    }

    /** Actualiza el peso del renglón. La UI espera boolean. */
    public boolean actualizarPesoProducto(ProductoPedido pp, float nuevoPeso) {
        if (pp == null) return false;
        pp.setPeso(nuevoPeso);
        if (pp.getPedido() != null) {
            pp.getPedido().recalcularTotal();
        }
        return true;
    }

    /** La UI espera float en calcularTotal(). */
    public float calcularTotal() {
        BigDecimal total = carrito.stream()
                .map(ProductoPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.floatValue();
    }

    /** Nota de pedido que la UI permite capturar. */
    public void agregarNota(String n) { this.nota = n; }
    public String obtenerNota() { return this.nota; }
}
