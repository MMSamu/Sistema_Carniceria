package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

import java.time.LocalDate;

/**
<<<<<<< HEAD
 * Representa el pago realizado para un pedido.
=======
 * Representa un pago realizado por un cliente para un pedido.
 * Contiene información sobre el tipo de pago, el monto, la fecha y su estado de confirmación.
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
=======
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

<<<<<<< HEAD
    private String tipoPago;
    private double monto;
    private LocalDate fechaPago;
    private String estado;

    /**
     * Pedido al que está asociado este pago.
     */

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    /**
     * Marca el pago como procesado.
     */

    public void procesarPago() {

        this.estado = "Procesado";
        this.fechaPago = LocalDate.now();

    }
=======
    private String tipoPago; // "Efectivo", "Tarjeta"
    private float monto;
    private LocalDate fechaPago;
    private String estado; // "Pendiente", "Confirmado"

    @ManyToOne
    private Cliente cliente; //
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1

    /**
     * Marca el pago como confirmado.
     */

    public void confirmarPago() {

        this.estado = "Confirmado";

    }

    /**
<<<<<<< HEAD
     * Simula la generación de un recibo.
     */

    public void generarRecibo() {

        // lógica futura

    }

}
=======
     * Genera un recibo textual del pago.
     * @return Cadena con la información del recibo.
     */

    public String generarRecibo() {

        return "Pago #" + idPago + " de $" + monto + " realizado el " + fechaPago + " vía " + tipoPago;
    }

}
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
