package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa una notificación enviada al cliente o al sistema.
 *
 * Está diseñada para cubrir:
 * - HU-05: Auditoría de envío de mensajes (registro de canal, estado, destino, etc.).
 * - HU-10: Notificaciones de estado del pedido (confirmación, preparación, entrega, etc.).
 *
 * Usa anotaciones JPA para persistencia y Lombok para generar getters, setters y constructor vacío.
 */
@Entity // Marca la clase como una entidad de persistencia JPA
@Getter @Setter @NoArgsConstructor // Lombok: genera getters, setters y constructor sin argumentos
public class Notificacion {

    /* ======================== Campos existentes (HU-05) ======================== */

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Valor autoincremental generado por la BD
    private Long id; // Identificador único de la notificación

    @Column(nullable = false)
    private Long idPedido; // Referencia al pedido asociado a la notificación

    // Canal de envío, reemplazando un String por un enum para mayor seguridad
    @Enumerated(EnumType.STRING) // Guarda el nombre del enum en la BD como texto
    @Column(nullable = false, length = 12)
    private Canal canal = Canal.WHATSAPP; // Canal de envío (por defecto WhatsApp)

    @Column(nullable = false, length = 20)
    private String telefonoDestino; // Número de teléfono del destinatario

    @Column(nullable = false, length = 30)
    private String estadoEnvio; // Estado del envío ("CONFIRMADO", "FALLO", etc.)

    @Column(nullable = false, length = 200)
    private String mensaje; // Contenido del mensaje enviado

    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now(); // Fecha y hora en que se generó la notificación

    /* ======================== Campos añadidos (HU-10) ======================== */

    // Tipo de notificación (permite manejar diferentes categorías con una sola entidad)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoNotificacion tipo = TipoNotificacion.ESTADO_PEDIDO; // Por defecto, notificación de estado de pedido

    // Estado del pedido (solo aplica si tipo == ESTADO_PEDIDO)
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoPedido estadoPedido; // Ej.: CONFIRMADO, PREPARADO, EN_RUTA, ENTREGADO

    // Indica si la notificación ya fue visualizada o marcada como leída por el usuario
    @Column(nullable = false)
    private boolean leida = false;

    /* ======================== Enums de apoyo ======================== */

    /**
     * Enum que define los canales posibles para enviar notificaciones.
     */
    public enum Canal { SMS, WHATSAPP }

    /**
     * Enum que define los tipos de notificación que puede manejar el sistema.
     */
    public enum TipoNotificacion {
        ESTADO_PEDIDO,   // Estado del pedido (HU-10)
        PAGO,            // Notificaciones relacionadas con pagos
        OTRO             // Otros tipos no clasificados
    }

    /**
     * Enum que define los estados clave que puede tener un pedido (HU-10).
     */
    public enum EstadoPedido { CONFIRMADO, PREPARADO, EN_RUTA, ENTREGADO }
}
