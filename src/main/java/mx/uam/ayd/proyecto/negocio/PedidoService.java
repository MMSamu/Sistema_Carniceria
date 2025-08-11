package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    /**
     * HU-03: Lista de métodos de entrega soportados por el sistema.
     *
     * Notas para el equipo:
     * - Si en el futuro los métodos vienen de BD o un catálogo configurable,
     *   basta con sustituir esta implementación por una consulta al repositorio.
     * - La UI de checkout debería consultar esta lista para renderizar radios/toggles.
     */

    public List<String> listarMetodosEntrega() {

        return List.of("A domicilio", "En tienda", "Recoger en mostrador");

    }

    /**
     * HU-03: Asigna el método de entrega a un pedido y devuelve el Pedido actualizado.
     *
     * Reglas de negocio:
     *  - El pedido debe existir; si no, se lanza NoSuchElementException.
     *  - El método debe ser uno de los soportados por {@link #listarMetodosEntrega()}.
     *  - Si el pedido YA tiene tipoEntrega asignado, no se puede volver a asignar
     *    (lanzamos IllegalStateException). Si quieren idempotencia, se puede ajustar aquí.
     *
     * Consideraciones técnicas:
     *  - @Transactional asegura que la operación de escritura sea atómica.
     *  - Devolvemos el Pedido para que la UI pueda refrescar su estado inmediatamente.
     */

    @Transactional

    public Pedido asignarMetodoEntrega(Long idPedido, String metodo) {

        // 1) Buscar el pedido o fallar si no existe.

        Pedido pedido = pedidoRepository.findById(idPedido)

                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado: " + idPedido));

        // 2) Validar entrada (null/blank) y pertenencia al catálogo soportado.

        if (metodo == null || metodo.isBlank()) {

            throw new IllegalArgumentException("El método de entrega no puede estar vacío");

        }

        List<String> soportados = listarMetodosEntrega();

        if (!soportados.contains(metodo)) {

            throw new IllegalArgumentException("Método de entrega inválido: " + metodo

                    + ". Válidos: " + String.join(", ", soportados));

        }

        // 3) Evitar reasignación si ya fue seteado previamente.

        if (pedido.getTipoEntrega() != null && !pedido.getTipoEntrega().isBlank()) {

            throw new IllegalStateException("El pedido ya tiene método de entrega: " + pedido.getTipoEntrega());

        }

        // 4) Asignar y persistir.

        pedido.setTipoEntrega(metodo);

        return pedidoRepository.save(pedido);

    }
}

