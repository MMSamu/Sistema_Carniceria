package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un cliente en el sistema.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /** Nombre(s) del cliente */
    @Column(nullable = false)
    private String nombre;

    /** Apellido(s) del cliente */
    @Column(nullable = false)
    private String apellido;

    /** Teléfono de contacto (opcional) */
    private String telefono;

    /** Correo electrónico (opcional) */
    private String email;

    /**
     * Relación 1..N con Direccion.
     * - LAZY para no traer direcciones si no se necesitan.
     * - Cascade PERSIST/MERGE para crear/actualizar junto con el cliente sin borrar en cascada.
     */
  
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Builder.Default
    private List<Direccion> direcciones = new ArrayList<>();

    /*  Métodos de dominio */

    /** Nombre completo para UI/reportes. */
  
    public String getNombreCompleto() {
        String n = nombre != null ? nombre : "";
        String a = apellido != null ? apellido : "";
        return (n + " " + a).trim();
    }

    /** Indica si existe al menos un dato de contacto. */
  
    public boolean tieneContacto() {
        return (telefono != null && !telefono.isBlank()) || (email != null && !email.isBlank());
    }

    /** Añade una dirección y asegura la consistencia del lado dueño. */
  
    public void agregarDireccion(Direccion dir) {
        if (dir == null) return;
        if (direcciones == null) direcciones = new ArrayList<>();
        direcciones.add(dir);
        dir.setCliente(this);
    }

    /** Quita una dirección actualizando ambas partes. */
  
    public void quitarDireccion(Direccion dir) {
        if (dir == null || direcciones == null) return;
        direcciones.remove(dir);
        if (dir.getCliente() == this) {
            dir.setCliente(null);
        }
    }

    /** Actualiza datos de contacto de forma segura. */
  
    public void actualizarContacto(String nuevoTelefono, String nuevoEmail) {
        if (nuevoTelefono != null && !nuevoTelefono.isBlank()) this.telefono = nuevoTelefono;
        if (nuevoEmail != null && !nuevoEmail.isBlank()) this.email = nuevoEmail;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + idCliente + ", nombreCompleto='" + getNombreCompleto() + "'}";
    }
}
