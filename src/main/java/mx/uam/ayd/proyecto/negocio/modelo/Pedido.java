package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un repartidor encargado de entregar pedidos a domicilio.
 * Contiene datos de contacto, estado de disponibilidad y vehículo asignado.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepartidor;

    private String nombre;
    private String apellido;
    private String telefono;
    private boolean disponible;
    private String vehiculo;

    /**
     * Marca al repartidor como ocupado al tomar una entrega.
     */

    public void tomarEntrega() {

        this.disponible = false;

    }

    /**
     * Marca al repartidor como disponible después de completar una entrega.
     */

    public void completarEntrega() {

        this.disponible = true;

    }

    /**
     * Reporta el vehículo con el que realiza la entrega.
     * @return descripción del vehículo
     */

    public String reportarUbicacion() {

        return "Repartidor " + nombre + " en vehículo: " + vehiculo;

    }

}
