package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductoPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @Column(nullable = false)
    private int cantidad;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    /* Compatibilidad con UI del carrito */
  
    private float peso;

    public String getNombre() {
        return producto != null ? producto.getNombre() : null;
    }

    public float getPeso() { return peso; }
    public void setPeso(float p) { this.peso = Math.max(0, p); }

    public BigDecimal getPrecio() { return precioUnitario; }

    public BigDecimal calcularSubtotal() { return getSubtotal(); }
  
    /* ************************************************************* */

    public BigDecimal getSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(Math.max(0, cantidad)));
    }

    public void actualizarCantidad(int nuevaCantidad) {
        this.cantidad = Math.max(0, nuevaCantidad);
        if (pedido != null) {
            pedido.recalcularTotal();
        }
    }

    public void actualizarPrecioUnitario(BigDecimal nuevoPrecio) {
        if (nuevoPrecio == null || nuevoPrecio.compareTo(BigDecimal.ZERO) < 0) return;
        this.precioUnitario = nuevoPrecio;
        if (pedido != null) {
            pedido.recalcularTotal();
        }
    }

    public void vincularPedido(Pedido p) {
        this.pedido = p;
        if (p != null && !p.getProductosPedido().contains(this)) {
            p.getProductosPedido().add(this);
        }
    }

    public void vincularProducto(Producto pr) {
        this.producto = pr;
        if (pr != null && (pr.getProductosPedido() != null) && !pr.getProductosPedido().contains(this)) {
            pr.getProductosPedido().add(this);
        }
    }

    @Override
    public String toString() {
        return "ProductoPedido{id=" + idProductoPedido +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + getSubtotal() +
                '}';
    }

    // HELPERS PARA COMPATIBILIDAD CON TESTS

    public void setNombre(String nombre) {
        if (this.producto == null) {
            this.producto = new Producto();
        }
        this.producto.setNombre(nombre);
    }

    /**
     * Compatibilidad: algunos tests esperan setPrecio(int).
     * Nuestro modelo usa BigDecimal 'precioUnitario'.
     */
    
    public void setPrecio(int precio) {
        this.precioUnitario = java.math.BigDecimal.valueOf(precio);
    }

    /** Sobrecarga opcional por si algún test usa BigDecimal explícito. */
    
    public void setPrecio(java.math.BigDecimal precio) {
        this.precioUnitario = precio;
    }

}

