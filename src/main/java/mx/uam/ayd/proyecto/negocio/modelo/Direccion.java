package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa una dirección asociada a un cliente.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    private String calle;
    private String numeroExterior;
    private String numeroInterior;   // opcional
    private String colonia;
    private String municipio;
    private String estado;
    private String codigoPostal;
    private String referencias;      // notas para repartidor
    private boolean predeterminada;  // útil si un cliente tiene varias direcciones

    /** Muchas direcciones pertenecen a un cliente. */
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    /* Helpers de dominio */

    /** Dirección breve para listados. */
  
    public String direccionCorta() {
        String numInt = (numeroInterior != null && !numeroInterior.isBlank()) ? " Int. " + numeroInterior : "";
        return String.format("%s %s%s, %s",
                safe(calle), safe(numeroExterior), numInt, safe(colonia));
    }

    /** Dirección completa para vistas detalladas o impresión. */
  
    public String direccionCompleta() {
        String numInt = (numeroInterior != null && !numeroInterior.isBlank()) ? " Int. " + numeroInterior : "";
        return String.format("%s %s%s, %s, %s, %s, CP %s",
                safe(calle), safe(numeroExterior), numInt,
                safe(colonia), safe(municipio), safe(estado), safe(codigoPostal));
    }

    /** Valida CP simple (5 dígitos).*/
  
    public boolean cpValido() {
        return codigoPostal != null && codigoPostal.matches("\\d{5}");
    }

    /** Actualización parcial desde otra dirección (solo valores no vacíos). */
    public void actualizarDesde(Direccion otra) {
        if (otra == null) return;
        if (notBlank(otra.calle)) this.calle = otra.calle;
        if (notBlank(otra.numeroExterior)) this.numeroExterior = otra.numeroExterior;
        if (notBlank(otra.numeroInterior)) this.numeroInterior = otra.numeroInterior;
        if (notBlank(otra.colonia)) this.colonia = otra.colonia;
        if (notBlank(otra.municipio)) this.municipio = otra.municipio;
        if (notBlank(otra.estado)) this.estado = otra.estado;
        if (notBlank(otra.codigoPostal)) this.codigoPostal = otra.codigoPostal;
        if (notBlank(otra.referencias)) this.referencias = otra.referencias;
        this.predeterminada = otra.predeterminada || this.predeterminada;
        if (otra.cliente != null) this.cliente = otra.cliente;
    }

    @Override
    public String toString() {
        return direccionCompleta();
    }

    /* Helpers internos */
    /* Comprueba que la cadena no sea null ni esté vacía después de quitar espacios.*/
    private static boolean notBlank(String s) { return s != null && !s.isBlank(); }
  
    /*Devuelve el valor original sin null (en caso de null devuelve "" vacío).*/
    private static String safe(String s) { return s == null ? "" : s.trim(); }
  
}
