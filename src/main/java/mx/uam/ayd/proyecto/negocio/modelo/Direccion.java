package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa una dirección física asociada a un pedido.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
    }

}
