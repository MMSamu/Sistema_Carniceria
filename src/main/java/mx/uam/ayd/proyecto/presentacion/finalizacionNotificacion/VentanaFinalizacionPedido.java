package mx.uam.ayd.proyecto.presentacion.finalizacionNotificacion;

import javax.swing.JOptionPane;

/**
 * Ventana emergente (pop-up) para mostrar mensajes relacionados
 * con la finalización del pedido.
 *
 * <p>Este componente utiliza {@link JOptionPane} de Swing para mostrar:
 * <ul>
 *   <li>Un mensaje de confirmación al usuario cuando el pedido se finaliza correctamente.</li>
 *   <li>Un mensaje de error en caso de que ocurra algún problema.</li>
 * </ul>
 * </p>
 *
 * <p>Nota: Aunque el resto del sistema use JavaFX, esta clase usa Swing por
 * simplicidad en la generación de pop-ups modales.</p>
 */
public class VentanaFinalizacionPedido {

    /**
     * Muestra un pop-up de confirmación con la información del pedido.
     *
     * @param idPedido    Identificador único del pedido.
     * @param estado      Estado actual del pedido (ej. "Confirmado").
     * @param tipoEntrega Tipo de entrega (ej. "Domicilio", "Tienda").
     */
    public void mostrar(Long idPedido, String estado, String tipoEntrega) {
        // Construcción del mensaje que se mostrará al usuario.
        String msg = "¡Listo!\n\n" +
                "Pedido #" + idPedido + "\n" +
                estado + "\n" +
                "Entrega: " + tipoEntrega + "\n\n" +
                "Recibirás un mensaje por WhatsApp/SMS con los detalles.";

        /*
         * Parámetros del showMessageDialog:
         * (null)   → No hay ventana padre; aparece centrado en pantalla.
         * (msg)    → Contenido del mensaje.
         * ("Pedido confirmado") → Título de la ventana emergente.
         * (JOptionPane.INFORMATION_MESSAGE) → Ícono de información.
         */
        JOptionPane.showMessageDialog(null, msg, "Pedido confirmado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un pop-up de error con el detalle proporcionado.
     *
     * @param detalle Mensaje que describe el error.
     */
    public void mostrarError(String detalle) {
        /*
         * Parámetros del showMessageDialog:
         * (null)   → No hay ventana padre; aparece centrado.
         * (detalle) → Contenido del mensaje de error.
         * ("Error") → Título de la ventana emergente.
         * (JOptionPane.ERROR_MESSAGE) → Ícono de error.
         */
        JOptionPane.showMessageDialog(null, detalle, "Error", JOptionPane.ERROR_MESSAGE);
    }
}


