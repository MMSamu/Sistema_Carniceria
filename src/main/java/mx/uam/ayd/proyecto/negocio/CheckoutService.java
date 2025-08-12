package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio de checkout para HU-07 (checklist de pedido).
 * - Obtiene los productos del pedido
 * - Calcula subtotal, envío y total
 * - Lee el método de entrega seleccionado en el pedido
 */
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final PedidoRepository pedidoRepository;
    private final ProductoPedidoRepository productoPedidoRepository;

    /**
     * Obtiene los productos asociados a un pedido.
     * Requiere que exista la relación ProductoPedido -> Pedido.
     *
     * @param idPedido id del pedido
     * @return lista de productos del pedido
     */
    public List<ProductoPedido> obtenerProductosDelPedido(Long idPedido) {
        return productoPedidoRepository.findByPedido_IdPedido(idPedido);
    }

    /**
     * Devuelve el tipo de entrega registrado en el pedido (ej. "A domicilio", "En tienda").
     *
     * @param idPedido id del pedido
     * @return tipo de entrega (puede ser null si aún no se ha asignado)
     */
    public String obtenerTipoEntregaDelPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
        return pedido.getTipoEntrega();
    }

    /**
     * Calcula el subtotal sumando los subtotales de cada producto.
     * Si un producto no trae subtotal, se calcula como precioUnitario * cantidad.
     *
     * @param items productos del pedido
     * @return subtotal (nunca null)
     */
    public BigDecimal calcularSubtotal(List<ProductoPedido> items) {
        BigDecimal subtotal = BigDecimal.ZERO;

        if (items == null || items.isEmpty()) {
            return subtotal;
        }

        for (ProductoPedido pp : items) {
            BigDecimal sub = pp.getSubtotal();
            if (sub == null) {
                BigDecimal precio = pp.getPrecioUnitario() != null ? pp.getPrecioUnitario() : BigDecimal.ZERO;
                int cantidad = Math.max(0, pp.getCantidad());
                sub = precio.multiply(BigDecimal.valueOf(cantidad));
            }
            subtotal = subtotal.add(sub);
        }
        return subtotal;
    }

    /**
     * Calcula el costo de envío según el tipo de entrega.
     * Regla simple por ahora: si contiene "domicilio" -> $50.00; en otro caso $0.00.
     *
     * @param tipoEntrega tipo de entrega del pedido
     * @return costo de envío
     */
    public BigDecimal calcularEnvio(String tipoEntrega) {
        if (tipoEntrega != null && tipoEntrega.toLowerCase().contains("domicilio")) {
            return new BigDecimal("50.00");
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcula el total como subtotal + envío.
     *
     * @param subtotal subtotal calculado
     * @param envio costo de envío
     * @return total (nunca null)
     */
    public BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal envio) {
        BigDecimal s = (subtotal != null) ? subtotal : BigDecimal.ZERO;
        BigDecimal e = (envio != null) ? envio : BigDecimal.ZERO;
        return s.add(e);
    }
}