package mx.uam.ayd.proyecto.negocio.modelo;

/**
 * Enumeración que define los estados clave que puede tener un pedido
 * para efectos de notificación al cliente.
 *
 * Se utiliza principalmente para:
 * - Indicar el progreso del pedido en la interfaz.
 * - Enviar notificaciones específicas al cliente (WhatsApp, SMS, etc.).
 * - Registrar en la base de datos el estado actual del pedido.
 */
public enum EstadoPedidoEnum {

    /**
     * Pedido confirmado por el sistema y aceptado para su procesamiento.
     */
    CONFIRMADO,

    /**
     * Pedido en proceso de preparación (corte, empaque, etc.).
     */
    PREPARADO,

    /**
     * Pedido en ruta de entrega hacia el cliente.
     */
    EN_RUTA,

    /**
     * Pedido entregado al cliente final.
     */
    ENTREGADO
}

