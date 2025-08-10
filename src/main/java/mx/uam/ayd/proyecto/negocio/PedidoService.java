package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los pedidos.
 */

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    /**
     * Registra un nuevo pedido en el sistema.
     * @param pedido entidad Pedido a registrar
     * @return pedido registrado
     */
  
    public Pedido registrarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    /**
     * Obtiene un pedido por su identificador.
     * @param idPedido identificador del pedido
     * @return el pedido si existe
     */
  
    public Optional<Pedido> obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    /**
     * Obtiene la lista completa de pedidos registrados.
     * @return lista de pedidos
     */
  
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Elimina un pedido por su identificador.
     * @param idPedido identificador del pedido
     */
  
    public void eliminarPedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }
}
