package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    /*  CRUD persistente */

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

    /** Agrega un renglón al carrito. */
    public void agregarProducto(ProductoPedido pp) {
        if (pp != null) {
            carrito.add(pp);
        }
    }

    /** Elimina un renglón del carrito. */
    public void eliminarProducto(ProductoPedido pp) {
        carrito.remove(pp);
    }

    /** Actualiza el "peso" que la UI muestra para el renglón (compatibilidad). */
    public void actualizarPesoProducto(ProductoPedido pp, float nuevoPeso) {
        if (pp != null) {
            pp.setPeso(nuevoPeso);
            if (pp.getPedido() != null) {
                pp.getPedido().recalcularTotal();
            }
        }
    }

    /** Total del carrito (suma de subtotales). */
    public BigDecimal calcularTotal() {
        return carrito.stream()
                .map(ProductoPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Nota de pedido que la UI permite capturar. */
    public void agregarNota(String n) { this.nota = n; }
    public String obtenerNota() { return this.nota; }
}
