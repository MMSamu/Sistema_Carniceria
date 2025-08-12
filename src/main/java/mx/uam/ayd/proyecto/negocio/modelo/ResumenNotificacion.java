package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.AllArgsConstructor;  // genera constructor con todos los argumentos
import lombok.Getter;              // genera getters

/**
 * No lleva @Entity ni se guarda en BD: solo transporta datos a la vista.
 */
@Getter
@AllArgsConstructor
public class ResumenNotificacion {

    // número de pedido que se mostrará al usuario
    private final Long idPedido;

    // texto de estado ("Pedido confirmado")
    private final String estado;

    // tipo de entrega que eligió el usuario ("TIENDA" o "DOMICILIO")
    private final String tipoEntrega;
}
