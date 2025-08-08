package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

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

    private int cantidadDisponible;
    private int stockMinimo;
    private LocalDate fechaActualizacion;

    /**
     * Lista de pedidos que han incluido este producto.
     */

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ProductoPedido> productosPedido;

    /**
     * Actualiza el stock disponible del producto.
     * @param cantidadRestante nueva cantidad disponible
     */

    public void actualizarStock(int cantidadRestante) {

        this.cantidadDisponible = cantidadRestante;
        this.fechaActualizacion = LocalDate.now();

    }

    /**
     * Verifica si el producto tiene stock suficiente.
     * @return true si está disponible
     */

    public boolean verificarDisponibilidad() {

        return cantidadDisponible > stockMinimo;

    }

    /**
     * Simula la generación de un reporte de inventario.
     */

    public void generarReporte() {

        // lógica exportable

    }

}