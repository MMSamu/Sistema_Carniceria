package mx.uam.ayd.proyecto.presentacion.asignarMetodoEntrega;

import mx.uam.ayd.proyecto.negocio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControlAsignarMetodoEntrega {

    private final PedidoService pedidoService;
    private final VentanaMetodoEntrega ventanaMetodoEntrega;

    @Autowired
    public ControlAsignarMetodoEntrega(PedidoService pedidoService,
                                       VentanaMetodoEntrega ventanaMetodoEntrega) {
        this.pedidoService = pedidoService;
        this.ventanaMetodoEntrega = ventanaMetodoEntrega;
    }

    /**
     * Arranca la HU-03: abre el modal con las opciones de entrega.
     */
    public void inicia() {
        ventanaMetodoEntrega.setControl(this);
        ventanaMetodoEntrega.muestra(pedidoService.listarMetodosEntrega());
    }

    /**
     * Llamado por la ventana cuando el usuario confirma la selección.
     * (Usa un id de pedido de ejemplo; integra aquí el id real cuando lo tengas).
     */
    public void asignarMetodo() {
        String metodoSeleccionado = ventanaMetodoEntrega.obtenerSeleccion();
        if (metodoSeleccionado == null || metodoSeleccionado.isBlank()) {
            // Aquí podrías mostrar un Alert en vez de un println
            System.out.println("Selecciona un método de entrega.");
            return;
        }

        try {
            // TODO: sustituir 1L por el id real del pedido que estés trabajando
            pedidoService.asignarMetodoEntrega(1L, metodoSeleccionado);
            ventanaMetodoEntrega.cerrarVentana();
        } catch (Exception e) {
            // Aquí también podrías mostrar un Alert
            System.out.println("Error al asignar método: " + e.getMessage());
        }
    }
}

