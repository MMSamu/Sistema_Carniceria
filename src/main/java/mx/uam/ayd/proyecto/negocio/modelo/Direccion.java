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

    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private String codigoPostal;

    /**
     * Valida si todos los campos de la dirección están completos.
     *
     * @return true si no hay campos vacíos o nulos, false en caso contrario.
     */
    public boolean validar() {

        return calle != null && !calle.isBlank()
                && numero != null && !numero.isBlank()
                && colonia != null && !colonia.isBlank()
                && ciudad != null && !ciudad.isBlank()
                && codigoPostal != null && !codigoPostal.isBlank();
    }

    /**
     * Devuelve una representación simple de la ubicación.
     *
     * @return Cadena concatenada con la dirección completa.
     */

    public String obtenerUbicacion() {

        return calle + " " + numero + ", " + colonia + ", " + ciudad + " C.P. " + codigoPostal;

    }

}
