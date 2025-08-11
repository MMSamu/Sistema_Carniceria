package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * Representa a un empleado del sistema.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    /** Nombre(s) del empleado */

    @Column(nullable = false)
    private String nombre;

    /** Apellido(s) del empleado */

    @Column(nullable = false)
    private String apellido;

    /** Teléfono de contacto (opcional) */

    private String telefono;

    /** Correo electrónico (opcional) */

    private String email;

    /** Puesto/cargo (texto libre) */

    private String puesto;

    /** Tipo de contrato (Indefinido, Temporal, Honorarios, etc.) */

    private String tipoContrato;

    /** Turno (Matutino, Vespertino, Nocturno, etc.) */

    private String turno;

    /** Fecha de ingreso */

    private LocalDate fechaIngreso;

    /** Salario nominal actual */

    @Column(precision = 12, scale = 2)
    private BigDecimal salario;

    /** Estatus laboral */

    @Builder.Default
    private boolean activo = true;

    /*  LÓGICA DE DOMINIO  */

    /** Nombre completo para UI/reportes. */

    public String getNombreCompleto() {

        String n = nombre != null ? nombre : "";

        String a = apellido != null ? apellido : "";

        return (n + " " + a).trim();

    }

    /** ¿Tiene al menos un dato de contacto? */

    public boolean tieneContacto() {

        return (telefono != null && !telefono.isBlank()) || (email != null && !email.isBlank());

    }

    /** Actualiza contacto (solo valores no vacíos). */

    public void actualizarContacto(String nuevoTelefono, String nuevoEmail) {

        if (notBlank(nuevoTelefono)) this.telefono = nuevoTelefono;

        if (notBlank(nuevoEmail)) this.email = nuevoEmail;

    }

    /** Años de antigüedad redondeados hacia abajo. */

    public int antiguedadAnios() {

        if (fechaIngreso == null) return 0;

        return Math.max(0, Period.between(fechaIngreso, LocalDate.now()).getYears());

    }

    /** Cambia de puesto (ignora valores vacíos). */

    public void cambiarPuesto(String nuevoPuesto) {

        if (notBlank(nuevoPuesto)) this.puesto = nuevoPuesto;

    }

    /** Cambia de turno (ignora valores vacíos). */

    public void cambiarTurno(String nuevoTurno) {

        if (notBlank(nuevoTurno)) this.turno = nuevoTurno;

    }

    /** Activa o desactiva al empleado. */

    public void marcarActivo(boolean nuevoEstatus) {

        this.activo = nuevoEstatus;

    }

    /** Ajusta el salario validando no-negatividad. */

    public void ajustarSalario(BigDecimal nuevoSalario) {

        if (nuevoSalario == null || nuevoSalario.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException("El salario no puede ser nulo ni negativo");

        }

        this.salario = nuevoSalario;

    }

    /** Hook de compatibilidad: algunas capas invocan esto. */

    public void procesarPedido(Pedido pedido) {

        // TODO: Lógica real si aplica para tu proyecto

    }

    /* Helpers internos */

    private static boolean notBlank(String s) { return s != null && !s.isBlank(); }

}

