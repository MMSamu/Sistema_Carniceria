package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa a un empleado que puede procesar pedidos dentro del sistema.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String apellido;
    private String rol;
    private String telefono;

    /**
     * Lista de pedidos que han sido procesados por este empleado.
     */

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Pedido> pedidosProcesados;

    /**
     * Devuelve el nombre completo del empleado.
     */

    public String getNombreCompleto() {

        return nombre + " " + apellido;

    }

    /**
     * Simula el procesamiento de un pedido.
     * @param pedido pedido a procesar
     */

    public void procesarPedido(Pedido pedido) {

        pedidosProcesados.add(pedido);
        pedido.setEmpleado(this);

    }

    /**
     * Simula que el empleado atiende a un cliente.
     * (Lógica real se implementa en la capa de servicio).
     */

    public void atenderCliente() {

        // Implementación en el servicio

    }

}
