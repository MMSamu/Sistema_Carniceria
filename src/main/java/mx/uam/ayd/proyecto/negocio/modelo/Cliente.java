package mx.uam.ayd.proyecto.negocio.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un cliente dentro del sistema.
 * Puede realizar pedidos y consultar su historial.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(nullable = false, length = 60)
    private String apellido;

    @Column(length = 20)
    private String telefono;

    @Column(length = 120)
    private String email;

    // Lista de pagos asociados a este cliente
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Pago> pagos = new ArrayList<>();

@Column
    private String metodoPago; // Efectivo, Transferencia, etc.
    /**
     * Agrega un pago al cliente y establece la relación inversa.
     * @param pago El pago a agregar.
     */
    public void agregarPago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser null");
        }
        if (!pagos.contains(pago)) {
            pagos.add(pago);
        }
        if (pago.getCliente() != this) {
            pago.setCliente(this);
        }

    }

    /**
     * (Opcional) Elimina un pago del cliente y rompe la relación.
     */
    public void eliminarPago(Pago pago) {
        if (pago == null) return;
        if (pagos.remove(pago)) {
            if (pago.getCliente() == this) {
                pago.setCliente(null);
            }
        }
    }
}
