package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa un producto disponible en el inventario de la carnicería.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    // Campos funcionales (HU-1)
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean enOferta;
    private double precioOferta;
    private String tipoCorte;
    private boolean esMenudeo;
    private boolean esCongelado;

    // Control de inventario
    private int cantidadDisponible;
    private int stockMinimo;
    private LocalDate fechaActualizacion;

    /**
     * Lista de pedidos que han incluido este producto.
     */
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoPedido> productosPedido;

    /**
     * Actualiza el stock disponible del producto y la fecha de actualización.
     * @param nuevaCantidad nueva cantidad disponible
     */
    public void actualizarStock(int nuevaCantidad) {
        this.cantidadDisponible = nuevaCantidad;
        this.fechaActualizacion = LocalDate.now();
    }

    /**
     * Verifica si hay suficiente stock disponible.
     * @return true si la cantidad disponible es mayor o igual al stock mínimo.
     */
    public boolean verificarDisponibilidad() {
        return cantidadDisponible >= stockMinimo;
    }

    /**
     * Genera un reporte sobre el estado del producto.
     */
    public String generarReporte() {
        return "Producto{id=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                ", stockMinimo=" + stockMinimo +
                ", ultimaActualizacion=" + fechaActualizacion +
            '}';
    }
}