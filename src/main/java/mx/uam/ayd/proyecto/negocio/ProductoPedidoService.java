package mx.uam.ayd.proyecto.negocio;

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