package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un empleado del sistema.
 * Puede procesar pedidos y atender clientes.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    private String nombre;
    private String apellido;
    private String rol; // Ejemplo: "Cajero", "Almacén", "Administrador"
    private String telefono;

    /**
     * Devuelve el nombre completo del empleado.
     * @return nombre y apellido concatenados
     */

    public String nombreCompleto() {

        return nombre + " " + apellido;

    }

    /**
     * Permite al empleado realizar tareas relacionadas con pedidos.
     * Este metodo podría extenderse para registrar acciones.
     */

    public void procesarPedido(Long idPedido) {

        System.out.println("Empleado " + nombreCompleto() + " procesó el pedido " + idPedido);

    }
}
