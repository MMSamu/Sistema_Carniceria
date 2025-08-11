package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import mx.uam.ayd.proyecto.negocio.ProductoPedidoService;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import java.math.BigDecimal;
import java.util.List;

public class ControlCarrito {

    private final ProductoPedidoService servicio;

    public ControlCarrito(ProductoPedidoService servicio) {
        this.servicio = servicio;
    }

    public List<ProductoPedido> obtenerCarrito() {
        return servicio.obtenerProductosDelCarrito();
    }

    public void agregarProducto(ProductoPedido producto) {
        servicio.agregarProducto(producto);
    }

    public void eliminarProducto(ProductoPedido producto) {
        servicio.eliminarProducto(producto);
    }

    /**
     * Nos quedamos con firma boolean por compatibilidad con la UI,
     * pero el servicio devuelve void, así que retornamos true.
     */
    public boolean actualizarPeso(ProductoPedido producto, float nuevoPeso) {
        servicio.actualizarPesoProducto(producto, nuevoPeso);
        return true;
    }

    /** El servicio devuelve BigDecimal -> convertimos a float para la UI antigua. */
    public float calcularTotal() {
        BigDecimal total = servicio.calcularTotal();
        return total != null ? total.floatValue() : 0f;
    }

    public void agregarNota(String nota) {
        servicio.agregarNota(nota);
    }

    public String obtenerNota() {
        return servicio.obtenerNota();
    }
}