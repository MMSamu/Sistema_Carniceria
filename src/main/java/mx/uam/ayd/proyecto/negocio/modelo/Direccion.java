package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una dirección asociada a un cliente o pedido.
 * Contiene los datos necesarios para identificar la ubicación de entrega.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    //contacto e identidad de quien recibe
    private String telefono;
    private String nombre;
    private String apellidos;
    private String paisRegion;
    private String referencia;

    //domicilio
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private String codigoPostal;

    //ubicación aproximada (mapa)
    private Double latitud;
    private Double longitud;

    private String tipoVivienda;

    /**
     * Valida si los campos mínimos están completos.
     * (Dejamos validaciones profundas al servicio para no romper otros flujos.)
     */
    public boolean validar() {
        return calle != null && !calle.isBlank()
                && numero != null && !numero.isBlank()
                && colonia != null && !colonia.isBlank()
                && ciudad != null && !ciudad.isBlank()
                && codigoPostal != null && codigoPostal.matches("\\d{5}");
    }

    /**
     * Devuelve una representación simple de la ubicación.
     */
    public String obtenerUbicacion() {
        return calle + " " + numero + ", " + colonia + ", " + ciudad + " C.P. " + codigoPostal;
    }
}
