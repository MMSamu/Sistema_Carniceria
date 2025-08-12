package mx.uam.ayd.proyecto.presentacion.finalizacionNotificacion;

import javax.swing.JOptionPane;

/**
 * Ventana emergente (pop-up).
 * Solo muestra la confirmación al usuario.
 */
public class VentanaFinalizacionPedido {

    /**
     * Pop-up de confirmación con la info.
     */
    public void mostrar(Long idPedido, String estado, String tipoEntrega) {
        String msg = "¡Listo!\n\n" + "Pedido #" + idPedido + "\n" + estado + "\n" + "Entrega: " + tipoEntrega + "\n\n" + "Recibirás un mensaje por WhatsApp/SMS con los detalles.";
// (null) parent (null = centro de pantalla)
// (msg) contenido
// ("Pedido confirmado") título
// (JOptionPane.INFORMATION_MESSAGE) ícono de información
        JOptionPane.showMessageDialog(null, msg, "Pedido confirmado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pop-up de error si algo falla en el proceso.
     */
    public void mostrarError(String detalle) {
        JOptionPane.showMessageDialog(null,detalle,"Error",JOptionPane.ERROR_MESSAGE);
    }
}

