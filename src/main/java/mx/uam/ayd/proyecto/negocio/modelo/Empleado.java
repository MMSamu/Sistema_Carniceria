package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

import java.util.List;

/**
 * Representa a un empleado que puede procesar pedidos dentro del sistema.
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un empleado del sistema.
 * Puede procesar pedidos y atender clientes.
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

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String apellido;
<<<<<<< HEAD
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
=======
    private String rol; // Ejemplo: "Cajero", "Almacén", "Administrador"
    private String telefono;

    /**
     * Devuelve el nombre completo del empleado.
     * @return nombre y apellido concatenados
     */

    public String nombreCompleto() {
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

        return nombre + " " + apellido;

    }

    /**
<<<<<<< HEAD
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

=======
     * Permite al empleado realizar tareas relacionadas con pedidos.
     * Este metodo podría extenderse para registrar acciones.
     */

    public void procesarPedido(Long idPedido) {

        System.out.println("Empleado " + nombreCompleto() + " procesó el pedido " + idPedido);

    }
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
}
