package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los productos dentro de un pedido.
 */

@Service
@RequiredArgsConstructor

public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    /**
     * Registra un nuevo productoPedido en el sistema.
     *
     * @param productoPedido el productoPedido a registrar
     * @return el productoPedido guardado
     */

    public ProductoPedido registrarProductoPedido(ProductoPedido productoPedido) {

        return productoPedidoRepository.save(productoPedido);

    }

    /**
     * Obtiene un productoPedido por su ID.
     *
     * @param idProductoPedido identificador del productoPedido
     * @return el productoPedido si existe
     */

    public Optional<ProductoPedido> obtenerProductoPedidoPorId(Long idProductoPedido) {

        return productoPedidoRepository.findById(idProductoPedido);

    }

    /**
     * Lista todos los productosPedido registrados.
     *
     * @return lista de productosPedido
     */

    public List<ProductoPedido> listarProductosPedido() {

        return (List<ProductoPedido>) productoPedidoRepository.findAll();

    }
}
