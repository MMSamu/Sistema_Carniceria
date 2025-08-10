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

    /** Devuelve catálogo simple para la UI (stub). */
    public List<String> listarMetodosEntrega() {
        return List.of("A domicilio", "Recoger en tienda");
    }

    /** Stub: asigna el método de entrega (ajusta a tu persistencia real). */
    public void asignarMetodoEntrega(long idPedido, String metodo) {
        // TODO: persistir el método en DB (campo en Pedido o entidad relacionada)
        // Por ahora es un no-op para que compile el flujo de la UI.
    }
}
