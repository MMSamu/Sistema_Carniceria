package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Relación entre un Producto y un Pedido, con cantidad y precio unitario.
 */

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

    /** Producto asociado */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProducto")
    private Producto producto;

    /** Pedido asociado */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    /** Cantidad solicitada del producto */
    @Column(nullable = false)
    private int cantidad;

    /** Precio unitario aplicado en este renglón */
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    /* Métodos de dominio */

    /** Subtotal = cantidad * precioUnitario (nunca null). */
    public BigDecimal getSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(Math.max(0, cantidad)));
    }

    /** Actualiza cantidad y mantiene el total del pedido coherente si está enlazado. */
    public void actualizarCantidad(int nuevaCantidad) {
        this.cantidad = Math.max(0, nuevaCantidad);
        if (pedido != null) {
            pedido.recalcularTotal();
        }
    }

    /** Cambia el precio unitario y recalcula el total del pedido si aplica. */
    public void actualizarPrecioUnitario(BigDecimal nuevoPrecio) {
        if (nuevoPrecio == null || nuevoPrecio.compareTo(BigDecimal.ZERO) < 0) return;
        this.precioUnitario = nuevoPrecio;
        if (pedido != null) {
            pedido.recalcularTotal();
        }
    }

    /** Ajusta la relación bidireccional con Pedido. */
    public void vincularPedido(Pedido p) {
        this.pedido = p;
        if (p != null && !p.getProductosPedido().contains(this)) {
            p.getProductosPedido().add(this);
        }
    }

    /** Ajusta la relación bidireccional con Producto. */
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
}
