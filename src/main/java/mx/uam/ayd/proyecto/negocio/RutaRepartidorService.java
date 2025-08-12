package mx.uam.ayd.proyecto.negocio;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RutaRepartidorService
{
    private final PedidoRepository pedidoRepository;
    private final ProductoPedidoRepository productoPedidoRepository;

    @Autowired
    public RutaRepartidorService(PedidoRepository pedidoRepository,
                                 ProductoPedidoRepository productoPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoPedidoRepository = productoPedidoRepository;
    }

    /**
     * Obtiene los pedidos asignados al repartidor.
     * Si aún no tienes relación Pedido->Repartidor, usa todos los de "domicilio".
     */
    public List<Pedido> obtenerPedidosParaRepartidor(Long idRepartidor) {
        // Opción A (cuando exista la relación):
        // return pedidoRepository.findByRepartidor_IdRepartidor(idRepartidor);

        // Opción B (temporal): todos los pedidos “a domicilio”
        List<Pedido> lista = new ArrayList<Pedido>();
        for (Pedido p : pedidoRepository.findByTipoEntregaContainingIgnoreCase("domicilio")) {
            lista.add(p);
        }
        return lista;
    }

    /**
     * Productos de un pedido (para mostrar detalles).
     */
    public List<ProductoPedido> obtenerProductosDelPedido(Long idPedido) {
        List<ProductoPedido> lista = new ArrayList<ProductoPedido>();
        for (ProductoPedido pp : productoPedidoRepository.findByPedido_IdPedido(idPedido)) {
            lista.add(pp);
        }
        return lista;
    }

    /**
     * Total del pedido a partir de sus productos.
     */
    public BigDecimal calcularTotalPedido(Long idPedido) {
        BigDecimal total = BigDecimal.ZERO;
        for (ProductoPedido pp : obtenerProductosDelPedido(idPedido)) {
            BigDecimal sub = pp.getSubtotal();
            if (sub == null) {
                BigDecimal pu = pp.getPrecioUnitario() != null ? pp.getPrecioUnitario() : BigDecimal.ZERO;
                int cant = Math.max(0, pp.getCantidad());
                sub = pu.multiply(BigDecimal.valueOf(cant));
            }
            total = total.add(sub);
        }
        return total;
    }

    /**
     * Cuenta cuántos items tiene el pedido.
     */
    public int contarItemsPedido(Long idPedido) {
        int n = 0;
        for (ProductoPedido ignored : obtenerProductosDelPedido(idPedido)) {
            n++;
        }
        return n;
}
}

