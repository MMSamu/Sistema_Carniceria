package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad unificada para HU-05 (auditoría de envío) y HU-10 (notificaciones de estado de pedido).
 */
@Entity
@Getter @Setter @NoArgsConstructor
public class Notificacion {

    /* ======================== Campos existentes (HU-05) ======================== */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idPedido; // referencia al pedido

    // Reemplazamos el String por un enum más seguro (ver abajo). Si ya tienes datos, mantén el String y añade el enum.
    // @Column(nullable = false)
    // private String canal; // "WHATSAPP" o "SMS"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private Canal canal = Canal.WHATSAPP; // default

    @Column(nullable = false, length = 20)
    private String telefonoDestino;

    @Column(nullable = false, length = 30)
    private String estadoEnvio; // "CONFIRMADO" / "FALLO" (simulado)

    @Column(nullable = false, length = 200)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    /* ======================== Campos añadidos (HU-10) ======================== */

    // Qué tipo de notificación es (permite usar la misma entidad para pagos, estado de pedido, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoNotificacion tipo = TipoNotificacion.ESTADO_PEDIDO;

    // Solo aplica cuando tipo == ESTADO_PEDIDO.
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoPedido estadoPedido; // CONFIRMADO, PREPARADO, EN_RUTA, ENTREGADO

    // Para que la UI sepa si ya se mostró/leyó (campanita, badges, etc.)
    @Column(nullable = false)
    private boolean leida = false;

    /* ======================== Enums de apoyo ======================== */

    public enum Canal { SMS, WHATSAPP }

    public enum TipoNotificacion {
        ESTADO_PEDIDO,   // HU-10
        PAGO,            //
        OTRO
    }

    /**
     * Estados clave del pedido para HU-10.
     */
    public enum EstadoPedido { CONFIRMADO, PREPARADO, EN_RUTA, ENTREGADO }
}
