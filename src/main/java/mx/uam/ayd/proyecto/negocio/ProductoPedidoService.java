package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con la asociación
 * entre productos y pedidos.
 */

@Service
@RequiredArgsConstructor
public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    /**
     * Registra una nueva relación Producto-Pedido.
     * @param productoPedido entidad ProductoPedido a registrar
     * @return relación registrada
     */
  
    public ProductoPedido registrarProductoPedido(ProductoPedido productoPedido) {
        return productoPedidoRepository.save(productoPedido);
    }

    /**
     * Obtiene una relación Producto-Pedido por su identificador.
     * @param id identificador de la relación
     * @return relación si existe
     */
  
    public Optional<ProductoPedido> obtenerProductoPedidoPorId(Long id) {
        return productoPedidoRepository.findById(id);
    }

    /**
     * Obtiene todas las relaciones Producto-Pedido registradas.
     * @return lista de relaciones
     */
  
    public List<ProductoPedido> listarProductosPedido() {
        return productoPedidoRepository.findAll();
    }

    /**
     * Elimina una relación Producto-Pedido por su identificador.
     * @param id identificador de la relación
     */
  
    public void eliminarProductoPedido(Long id) {
        productoPedidoRepository.deleteById(id);
    }
}

