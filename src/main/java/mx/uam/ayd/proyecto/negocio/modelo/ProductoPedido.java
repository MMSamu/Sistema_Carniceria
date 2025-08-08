package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un producto incluido dentro de un pedido, junto con su cantidad.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductoPedido;

    private int cantidad;
    private double precioUnitario;

    /**
     * Pedido al que pertenece este producto.
     */

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    /**
     * Producto base (del inventario) asociado al elemento del pedido.
     */

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Calcula el subtotal de productoPedido (precioUnitario * cantidad).
     * @return subtotal
     */

    public double getSubtotal() {

        return precioUnitario * cantidad;

    }

}