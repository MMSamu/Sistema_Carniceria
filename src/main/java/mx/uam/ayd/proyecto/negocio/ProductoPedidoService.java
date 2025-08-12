package mx.uam.ayd.proyecto.negocio;

import jakarta.transaction.Transactional;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

@Service
public class ProductoPedidoService {

    private final ProductoPedidoRepository repo;

    public ProductoPedidoService(ProductoPedidoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProductoPedido aumentarCantidad(long productoPedidoId) {
        ProductoPedido pp = repo.findById(productoPedidoId)
                .orElseThrow(() -> new IllegalArgumentException("ProductoPedido no encontrado: " + productoPedidoId));
        pp.setCantidad(pp.getCantidad() + 1);
        return repo.save(pp);
    }

    @Transactional
    public ProductoPedido reducirCantidad(long productoPedidoId) {
        ProductoPedido pp = repo.findById(productoPedidoId)
                .orElseThrow(() -> new IllegalArgumentException("ProductoPedido no encontrado: " + productoPedidoId));
        if (pp.getCantidad() <= 1) {
            throw new IllegalStateException("No se puede reducir por debajo de 1. Considera eliminar.");
        }
        pp.setCantidad(pp.getCantidad() - 1);
        return repo.save(pp);
    }

    @Transactional
    public void eliminarItem(long productoPedidoId) {
        if (!repo.existsById(productoPedidoId)) {
            throw new IllegalArgumentException("ProductoPedido no encontrado: " + productoPedidoId);
        }
        repo.deleteById(productoPedidoId);
    }
}