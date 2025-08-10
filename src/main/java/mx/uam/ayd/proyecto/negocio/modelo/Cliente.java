package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

import java.util.List;

/**
 * Representa a un cliente que puede realizar pedidos dentro del sistema.
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a un cliente dentro del sistema.
 * Puede realizar pedidos y consultar su historial.
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

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    /**
<<<<<<< HEAD
     * Lista de pedidos realizados por este cliente.
     */

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    /**
     * Método de utilidad para obtener el nombre completo del cliente.
     * @return nombre y apellido concatenados
     */

    public String getNombreCompleto() {
=======
     * Metodo para mostrar el nombre completo del cliente.
     * 
     * @return nombre y apellido
     */

    public String nombreCompleto() {
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

        return nombre + " " + apellido;

    }

    /**
<<<<<<< HEAD
     * Simula el registro del cliente en el sistema.
     */

    public void registrarse() {

        // lógica de registro (simulada)

    }

    /**
     * Permite al cliente iniciar un nuevo pedido.
     */

    public void realizarPedido(Pedido pedido) {

        pedidos.add(pedido);

    }

    /**
     * Permite consultar el historial de pedidos.
     */

    public List<Pedido> consultarHistorial() {

        return pedidos;

    }

=======
     * Verifica si el cliente tiene datos de contacto válidos.
     * 
     * @return true si el teléfono y el email están presentes
     */

    public boolean datosContactoCompletos() {

        return telefono != null && !telefono.isBlank()
                && email != null && !email.isBlank();

    }
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
}
