package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

/**
 * Repositorio para la entidad ProductoPedido.
 * Se utiliza para almacenar la relación entre productos y pedidos realizados.
 * Proporciona acceso a los detalles individuales de productos por pedido.
 */

public interface ProductoPedidoRepository extends CrudRepository<ProductoPedido, Long> {

}