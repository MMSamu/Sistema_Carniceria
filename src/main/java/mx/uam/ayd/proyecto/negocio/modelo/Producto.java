package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidades que representan un producto en el sistema de inventario.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Producto {

    @Id
    private int idInventario;

    private int cantidadDisponible;
    private int stockMinimo;
    private LocalDate fechaActualizacion;
    // se requiere para la HU-1
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean enOferta;
    private double precioOferta;
    private String tipoCorte;
    private boolean esMenudeo;
    private boolean esCongelado;

    /**
     * Actualiza el stock disponible del producto y la fecha de actualización.
     */
    public void actualizarStock(int nuevaCantidad) {

        this.cantidadDisponible = nuevaCantidad;
        this.fechaActualizacion = LocalDate.now();

    }

    /**
     * Verifica si hay suficiente stock disponible.
     * @return true si la cantidad disponible es mayor o igual al stock mínimo, false en caso contrario.
     */

    public boolean verificarDisponibilidad() {

        return cantidadDisponible >= stockMinimo;

    }

    /**
     * Genera un reporte sobre el estado del producto.
     */

    public String generarReporte() {

        return "ID Inventario: " + idInventario +
                ", Cantidad disponible: " + cantidadDisponible +
                ", Stock mínimo: " + stockMinimo +
                ", Última actualización: " + fechaActualizacion;

    }

}

