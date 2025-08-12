package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import java.util.List;

import mx.uam.ayd.proyecto.negocio.ProductoPedidoService;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

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

    public boolean actualizarPeso(ProductoPedido producto, float nuevoPeso) {
        return servicio.actualizarPesoProducto(producto, nuevoPeso);
    }

    public float calcularTotal() {
        return servicio.calcularTotal();
    }

    public void agregarNota(String nota) {
        servicio.agregarNota(nota);
    }

    public String obtenerNota() {
        return servicio.obtenerNota();
    }
}