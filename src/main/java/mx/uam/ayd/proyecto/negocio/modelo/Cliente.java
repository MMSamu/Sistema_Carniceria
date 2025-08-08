package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa a un cliente que puede realizar pedidos dentro del sistema.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    /**
     * Lista de pedidos realizados por este cliente.
     */

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    /**
     * Método de utilidad para obtener el nombre completo del cliente.
     * @return nombre y apellido concatenados
     */

    public String getNombreCompleto() {

        return nombre + " " + apellido;

    }

    /**
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

}
