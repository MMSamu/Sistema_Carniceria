package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

/**
 * Representa un producto incluido dentro de un pedido, junto con su cantidad.
 */

=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un producto que ha sido solicitado como parte de un pedido.
 * Contiene la información necesaria para identificar el producto, su precio,
 * peso y su disponibilidad al momento de procesar el pedido.
 */
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
@Entity
@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
=======
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
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

=======
    private int idProducto;

    private String nombre;
    private String descripcion;
    private float precio;
    private boolean disponible;
    private float peso;

    /**
     * Actualiza el precio del producto en el pedido.
     * 
     * @param nuevoPrecio nuevo precio a establecer
     */
    public void actualizarPrecio(float nuevoPrecio) {
        this.precio = nuevoPrecio;
    }

    /**
     * Verifica si el producto está disponible para ser agregado al pedido.
     * 
     * @return true si el producto está disponible, false en caso contrario
     */
    public boolean verificarDisponibilidad() {
        return disponible;
    }

    /**
     * Calcula el subtotal de este producto (precio × peso)
     * 
     * @return subtotal del producto
     */
    public float calcularSubtotal() {
        return precio * peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getNombre() {
        return this.nombre;
    }

    public float getPeso() {
        return this.peso;
    }

    public float getPrecio() {
        return this.precio;
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
    }

}