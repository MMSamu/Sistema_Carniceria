package mx.uam.ayd.proyecto.presentacion.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.PedidoService;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * HU-03: Endpoints para seleccionar el método de entrega de un Pedido.
 *
 * Notas:
 * - La UI debe usar GET para cargar la lista de métodos (radios/toggle).
 * - Luego usar POST para asignar el método seleccionado al pedido.
 * - Este controlador mapea excepciones comunes a códigos HTTP claros.
 */

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
// @CrossOrigin(origins = "*") // <- descomenta si tu frontend está en otro host/puerto
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * HU-03: Listar métodos de entrega soportados.
     * GET /api/pedidos/{id}/metodos-entrega
     *
     * Nota: el {id} es útil si quisieras validar existencia del pedido antes
     * (por simplicidad, aquí devolvemos el catálogo directamente).
     */

    @GetMapping("/{id}/metodos-entrega")

    public ResponseEntity<List<String>> listarMetodos(@PathVariable Long id) {

        return ResponseEntity.ok(pedidoService.listarMetodosEntrega());

    }

    /**
     * HU-03: Asignar método de entrega al pedido y devolver el pedido actualizado.
     * POST /api/pedidos/{id}/metodo-entrega?metodo=A%20domicilio
     *
     * Reglas de negocio:
     * - Falla con 404 si el pedido no existe.
     * - Falla con 400 si el método es inválido o ya había uno asignado.
     */

    @PostMapping("/{id}/metodo-entrega")

    public ResponseEntity<Pedido> asignarMetodo(@PathVariable Long id,

                                                @RequestParam("metodo") String metodo) {

        Pedido actualizado = pedidoService.asignarMetodoEntrega(id, metodo);

        return ResponseEntity.ok(actualizado);

    }

    /* Manejo simple de errores */

    @ExceptionHandler(NoSuchElementException.class)

    public ResponseEntity<ErrorDTO> handleNotFound(NoSuchElementException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)

                .body(ErrorDTO.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()));

    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })

    public ResponseEntity<ErrorDTO> handleBadRequest(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)

                .body(ErrorDTO.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));

    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<ErrorDTO> handleGeneric(Exception ex) {

        // En producción podrías loggear el stacktrace aquí

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

                .body(ErrorDTO.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error inesperado"));

    }

    /* DTO pequeño para respuestas de error */

    @Data

    private static class ErrorDTO {

        private final int status;
        private final String message;
        private final Instant timestamp;

        static ErrorDTO of(int status, String message) {

            return new ErrorDTO(status, message, Instant.now());

        }
    }
}
