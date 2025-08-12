package mx.uam.ayd.proyecto.presentacion.pedidosCarnicero;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.PedidoService;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Control de HU-12: Visualizar lista de pedidos (carnicero)
 * Orquesta la vista y el servicio.
 */
@Component
@RequiredArgsConstructor
public class ControlPedidosCarnicero {

    private final PedidoService pedidoService;
    private VentanaPedidosCarnicero vista;

    public void inicio() {
        if (vista == null) {
            vista = new VentanaPedidosCarnicero(this);
        }
        recargarLista();
        vista.mostrar();
    }

    void recargarLista() {
        // Lista solo pedidos "en proceso" ordenados por hora
        List<Pedido> lista = pedidoService.obtenerPedidosEnProcesoOrdenados();
        vista.mostrarListaPedidos(lista);
    }

    Pedido obtenerDetalles(long id) {
        return pedidoService.obtenerDetallesPedido(id);
    }

    void marcarPedidoListo(long id) {
        pedidoService.marcarPedidoListo(id);
        recargarLista();
        vista.mostrarDetalles(id); // refresca panel de detalle
    }

    void marcarListoParaEntregar(long id) {
        pedidoService.marcarListoParaEntregar(id);
        recargarLista();
        vista.mostrarDetalles(id);
    }
}
