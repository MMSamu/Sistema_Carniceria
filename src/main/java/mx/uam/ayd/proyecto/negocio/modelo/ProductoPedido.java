package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductoPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProducto")
    private Producto producto;
    private float precio;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    private int cantidad;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    // Compatibilidad UI/Tests

    // Algunos tests usan p.setNombre(...)

    public void setNombre(String nombre) {

        if (this.producto == null) this.producto = new Producto();

        this.producto.setNombre(nombre);

    }


    public String getNombre() {

        return (producto != null) ? producto.getNombre() : null;

    }


    // Algunos tests usan setPrecio(int) y getPrecio() float

    public void setPrecio(int valor) {

        this.precioUnitario = BigDecimal.valueOf(valor);

    }


    public float getPrecio() {

        return precioUnitario != null ? precioUnitario.floatValue() : 0f;

    }


    // Peso opcional para la UI (si lo tienes):
    private float peso;

    public float getPeso() { return peso; }

    public void setPeso(float p) { this.peso = Math.max(0f, p); }


    public BigDecimal getSubtotal() {

        if (precioUnitario == null) return BigDecimal.ZERO;

        return precioUnitario.multiply(BigDecimal.valueOf(Math.max(0, cantidad)));

    }
    public float calcularSubtotal() {
        return precio * peso;
    }
}