package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * Servicio que contiene la lógica de negocio relacionada con los pedidos.
 */

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    /**
     * Registra un nuevo pedido en el sistema.
     *
     * @param pedido el pedido a registrar
     * @return el pedido guardado
     */

    public Pedido registrarPedido(Pedido pedido) {

        return pedidoRepository.save(pedido);

    }

    /**
     * Obtiene un pedido por su identificador.
     *
     * @param idPedido identificador del pedido
     * @return un Optional que puede contener el pedido, si existe
     */

    public Optional<Pedido> obtenerPedidoPorId(Long idPedido) {

        return pedidoRepository.findById(idPedido);

    }

    /**
     * Lista todos los pedidos registrados.
     *
     * @return lista de pedidos
     */

    public List<Pedido> listarPedidos() {

        return (List<Pedido>) pedidoRepository.findAll();

    }

    /**
     * Actualiza el estado de entrega del pedido.
     *
     * @param idPedido identificador del pedido
     * @param nuevoEstado nuevo estado a asignar
     * @return true si se actualizó correctamente, false si no se encontró el pedido
     */

    public boolean actualizarEstadoEntrega(Long idPedido, String nuevoEstado) {

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstadoEntrega(nuevoEstado);
            pedidoRepository.save(pedido);
            return true;
        }
        return false;
    }

    /**
     * Devuelve la lista de métodos de entrega disponibles.
     * @return lista de cadenas con cada opción.
     */

    public List<String> listarMetodosEntrega() {

        // Se utiliza List.of, para crear una lista inmutable

        return List.of("En tienda", "A domicilio", "Recolección en punto");

    }

    /**
     * Asigna un metodo de entrega a un pedido, validando reglas de negocio.
     *
     * @param idPedido      ID del pedido a actualizar.
     * @param tipoEntrega   Metodo de entrega seleccionado.
     * @throws IllegalStateException si ya tenía un metodo asignado.
     * @throws NoSuchElementException si el pedido no existe.
     * @return el pedido actualizado.
     */

    public Pedido asignarMetodoEntrega(Long idPedido, String tipoEntrega) {

        // 1. Recuperar pedido

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado: " + idPedido));

        // 2. Regla: no puede reasignar si ya tiene tipoEntrega

        if (pedido.getTipoEntrega() != null && !pedido.getTipoEntrega().isBlank()) {

            throw new IllegalStateException("Método de entrega ya asignado para el pedido: " + idPedido);

        }

        // 3. Asignar y guardar

        pedido.setTipoEntrega(tipoEntrega);

        return pedidoRepository.save(pedido);

    }

}