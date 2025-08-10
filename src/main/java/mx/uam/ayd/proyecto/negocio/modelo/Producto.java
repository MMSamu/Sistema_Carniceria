package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/** Representa un producto disponible en el inventario de la carnicería. */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    // HU-1
    
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean enOferta;
    private double precioOferta;
    private String tipoCorte;
    private boolean esMenudeo;
    private boolean esCongelado;

    // Inventario
    
    private int cantidadDisponible;
    private int stockMinimo;
    private LocalDate fechaActualizacion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoPedido> productosPedido;

    public void actualizarStock(int nuevaCantidad) {
        this.cantidadDisponible = nuevaCantidad;
        this.fechaActualizacion = LocalDate.now();
    }

    public boolean verificarDisponibilidad() {
        return cantidadDisponible >= stockMinimo;
    }

    public String generarReporte() {
        return "Producto{id=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                ", stockMinimo=" + stockMinimo +
                ", ultimaActualizacion=" + fechaActualizacion +
                '}';
    }
}
