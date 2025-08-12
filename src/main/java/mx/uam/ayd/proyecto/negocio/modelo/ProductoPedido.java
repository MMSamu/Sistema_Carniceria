package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto_pedido")
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cada renglón pertenece a un pedido (carrito)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Producto al que hace referencia el renglón
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Cantidad del producto en el pedido
    @Column(nullable = false)
    private int cantidad;

    // Precio unitario “congelado” al momento de agregar al carrito
    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    /* ========= Constructores ========= */

    protected ProductoPedido() {
        /* JPA */ }

    public ProductoPedido(Pedido pedido, Producto producto, int cantidad, BigDecimal precioUnitario) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser > 0");
        this.pedido = Objects.requireNonNull(pedido, "pedido requerido");
        this.producto = Objects.requireNonNull(producto, "producto requerido");
        this.cantidad = cantidad;
        this.precioUnitario = Objects.requireNonNull(precioUnitario, "precioUnitario requerido");
    }

    /* ========= Reglas de dominio simples ========= */

    public void aumentar() {
        this.cantidad += 1;
    }

    public void reducir() {
        if (this.cantidad <= 1) {
            throw new IllegalStateException("No se puede reducir por debajo de 1 (considera eliminar).");
        }
        this.cantidad -= 1;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    /* ========= Getters/Setters ========= */

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser > 0");
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /* ========= equals/hashCode por id ========= */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductoPedido that))
            return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoPedido{id=%d, producto=%s, cantidad=%d, precio=%s}"
                .formatted(id, producto != null ? producto.getNombre() : "?", cantidad, precioUnitario);
    }

    public Object getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }

    public float getPeso() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPeso'");
    }

    public float getPrecio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrecio'");
    }
}