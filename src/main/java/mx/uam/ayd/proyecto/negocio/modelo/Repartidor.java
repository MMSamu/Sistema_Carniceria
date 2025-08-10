package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

import java.util.List;

/**
 * Representa a un repartidor encargado de entregar pedidos.
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un repartidor encargado de entregar pedidos a domicilio.
 * Contiene datos de contacto, estado de disponibilidad y vehículo asignado.
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
=======
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepartidor;

    private String nombre;
    private String apellido;
    private String telefono;
    private boolean disponible;
    private String vehiculo;

    /**
<<<<<<< HEAD
     * Lista de pedidos asignados a este repartidor.
     */

    @OneToMany(mappedBy = "repartidor", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    /**
     * Marca el pedido como tomado para entrega.
     * @param pedido pedido a tomar
     */

    public void tomarEntrega(Pedido pedido) {

        pedidos.add(pedido);
=======
     * Marca al repartidor como ocupado al tomar una entrega.
     */

    public void tomarEntrega() {

>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
        this.disponible = false;

    }

    /**
<<<<<<< HEAD
     * Marca el pedido como entregado y al repartidor como disponible.
=======
     * Marca al repartidor como disponible después de completar una entrega.
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
     */

    public void completarEntrega() {

        this.disponible = true;

    }

    /**
<<<<<<< HEAD
     * Simula el reporte de ubicación del repartidor.
     */

    public void reportarUbicacion() {

        // lógica futura de geolocalización

    }

    /**
     * Devuelve el nombre completo del repartidor.
     */

    public String getNombreCompleto() {

        return nombre + " " + apellido;

    }

}
=======
     * Reporta el vehículo con el que realiza la entrega.
     * @return descripción textual del vehículo
     */

    public String reportarUbicacion() {

        return "Repartidor " + nombre + " en vehículo: " + vehiculo;

    }

}
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
