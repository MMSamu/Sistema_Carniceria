package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa el pago realizado para un pedido.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

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

    /**
     * Marca el pago como confirmado.
     */

    public void confirmarPago() {

        this.estado = "Confirmado";

    }

    /**
     * Simula la generación de un recibo.
     */

    public void generarRecibo() {

        // lógica futura

    }

}