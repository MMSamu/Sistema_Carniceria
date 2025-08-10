package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

import java.util.List;

/**
 * Representa una dirección física asociada a un pedido.
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una dirección asociada a un cliente o pedido.
 * Contiene los datos necesarios para identificar la ubicación de entrega.
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
<<<<<<< HEAD
     * Lista de pedidos que utilizan esta dirección (entrega a domicilio).
     */

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    /**
     * Valida que los datos de la dirección estén completos.
     * @return true si todos los campos están presentes
     */

    public boolean validar() {
        return calle != null && !calle.isBlank() &&
                numero != null && !numero.isBlank() &&
                colonia != null && !colonia.isBlank() &&
                ciudad != null && !ciudad.isBlank() &&
                codigoPostal != null && !codigoPostal.isBlank();
    }

    /**
     * Devuelve la ubicación formateada en una sola línea.
     */

    public String obtenerUbicacion() {
        return calle + " " + numero + ", " + colonia + ", " + ciudad + ", CP " + codigoPostal;
=======
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

>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
    }

}
