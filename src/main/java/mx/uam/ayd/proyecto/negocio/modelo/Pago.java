package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Representa un pago realizado por un cliente para un pedido.
 * Contiene información sobre el tipo de pago, el monto, la fecha y su estado de confirmación.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private String tipoPago; // "Efectivo", "Tarjeta"
    private float monto;
    private LocalDate fechaPago;
    private String estado; // "Pendiente", "Confirmado"
    private String cliente; //

    /**
     * Marca el pago como confirmado.
     */

    public void confirmarPago() {

        this.estado = "Confirmado";

    }

    /**
     * Genera un recibo textual del pago.
     * @return Cadena con la información del recibo.
     */

    public String generarRecibo() {

        return "Pago #" + idPago + " de $" + monto + " realizado el " + fechaPago + " vía " + tipoPago;
    }

}
