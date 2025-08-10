package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un repartidor de la carnicería.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepartidor;

    /** Nombre(s) del repartidor */
    @Column(nullable = false)
    private String nombre;

    /** Apellido(s) del repartidor */
    @Column(nullable = false)
    private String apellido;

    /** Teléfono de contacto */
    private String telefono;

    /** Fecha de ingreso */
    private LocalDate fechaIngreso;

    /** Estatus (activo/inactivo) */
    @Builder.Default
    private boolean activo = true;

    /** Lista de pedidos asignados a este repartidor */
    @OneToMany(mappedBy = "repartidor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    /* Métodos de dominio */

    /** Nombre completo para UI o reportes. */
    public String getNombreCompleto() {
        String n = nombre != null ? nombre : "";
        String a = apellido != null ? apellido : "";
        return (n + " " + a).trim();
    }

    /** Activa o inactiva al repartidor. */
    public void marcarActivo(boolean nuevoEstado) {
        this.activo = nuevoEstado;
    }

    /** Asigna un pedido y ajusta relación bidireccional. */
    public void asignarPedido(Pedido pedido) {
        if (pedido == null) return;
        pedidos.add(pedido);
        pedido.setRepartidor(this);
    }

    /** Elimina un pedido asignado y ajusta relación bidireccional. */
    public void quitarPedido(Pedido pedido) {
        if (pedido == null) return;
        pedidos.remove(pedido);
        if (pedido.getRepartidor() == this) {
            pedido.setRepartidor(null);
        }
    }

    /** Verifica si el repartidor está disponible (activo y sin pedidos asignados). */
    public boolean disponible() {
        return activo && (pedidos == null || pedidos.isEmpty());
    }

    @Override
    public String toString() {
        return "Repartidor{id=" + idRepartidor +
                ", nombreCompleto='" + getNombreCompleto() + '\'' +
                ", activo=" + activo +
                ", pedidosAsignados=" + (pedidos != null ? pedidos.size() : 0) +
                '}';
    }
}
