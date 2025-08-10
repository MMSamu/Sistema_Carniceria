package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido registrarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido);
    }

    /** Iterable -> List para compatibilidad con CrudRepository */
    public List<Pedido> listarPedidos() {
        List<Pedido> list = new ArrayList<>();
        pedidoRepository.findAll().forEach(list::add);
        return list;
    }

    public void eliminarPedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    /* Métodos que usa la UI de "Asignar método de entrega" */

    /** Catálogo simple para la UI (stub). */
    public List<String> listarMetodosEntrega() {
        return List.of("A domicilio", "Recoger en tienda");
    }

    /** Versión con id (la correcta para persistir). */
    public void asignarMetodoEntrega(long idPedido, String metodo) {
        // TODO: persistir el método en DB (campo en Pedido o entidad relacionada)
    }

    /** Overload sin id para llamadas existentes en la UI (compatibilidad). */
    public void asignarMetodoEntrega(String metodo) {
        // NO-OP: solo para compilar si la UI llama sin id
    }

    /**
     * Asigna el método de entrega y devuelve el Pedido actualizado.
     * (Compatibilidad con tests que esperan un retorno Pedido)
     */
    
    public mx.uam.ayd.proyecto.negocio.modelo.Pedido asignarMetodoEntrega(long idPedido, String metodo) {
    var pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));
    pedido.setTipoEntrega(metodo);
    return PedidoRepository.save(pedido);
        
    }

}

