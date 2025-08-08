package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa a un repartidor encargado de entregar pedidos.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
        this.disponible = false;

    }

    /**
     * Marca el pedido como entregado y al repartidor como disponible.
     */

    public void completarEntrega() {

        this.disponible = true;

    }

    /**
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