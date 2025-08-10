package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un pago registrado en el sistema.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    /** Importe del pago */
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    /** Fecha y hora en que se registró el pago */
    @Column(nullable = false)
    private LocalDateTime fechaPago;

    /** Método de pago (Efectivo, Tarjeta, Transferencia, etc.) */
    @Column(nullable = false)
    private String metodoPago;

    /** Referencia/folio del pago (opcional) */
    private String referencia;

    /** Estado del pago (Registrado, Confirmado, Cancelado, Reembolsado...) */
    @Column(nullable = false)
    private String estado;

    /** Relación con el cliente que realizó el pago (opcional) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    /** Relación con el pedido que se liquida (opcional) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    /*  Métodos de dominio */

    /** Inicializa valores por defecto al crear el pago desde servicio/controlador. */
    public void inicializarSiNecesario() {
        if (this.fechaPago == null) this.fechaPago = LocalDateTime.now();
        if (!notBlank(this.estado)) this.estado = "Registrado";
    }

    /** Valida que el monto sea positivo. */
    public boolean montoValido() {
        return monto != null && monto.compareTo(BigDecimal.ZERO) > 0;
    }

    /** Confirma el pago (ej. tras verificación de banco). */
    public void confirmar() {
        this.estado = "Confirmado";
        if (this.fechaPago == null) this.fechaPago = LocalDateTime.now();
    }

    /** Cancela el pago con reglas simples. */
    public void cancelar() {
        this.estado = "Cancelado";
    }

    /** Marca el pago como reembolsado. */
    public void reembolsar() {
        this.estado = "Reembolsado";
    }

    /** Aplica un descuento directo al monto (por ejemplo, cupones). */
    public void aplicarDescuento(BigDecimal descuento) {
        if (descuento == null || descuento.compareTo(BigDecimal.ZERO) <= 0) return;
        if (this.monto == null) this.monto = BigDecimal.ZERO;
        BigDecimal nuevo = this.monto.subtract(descuento);
        this.monto = nuevo.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevo;
    }

    @Override
    public String toString() {
        return "Pago{id=" + idPago +
                ", monto=" + monto +
                ", fechaPago=" + fechaPago +
                ", metodoPago='" + safe(metodoPago) + '\'' +
                ", estado='" + safe(estado) + '\'' +
                '}';
    }

    /* Helpers internos */
  
    /* Comprueba que la cadena no sea null ni esté vacía después de quitar espacios. */
    private static boolean notBlank(String s) { return s != null && !s.isBlank(); }
  
    /* Devuelve el valor original sin null (en caso de null devuelve "" vacío). */ 
    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
