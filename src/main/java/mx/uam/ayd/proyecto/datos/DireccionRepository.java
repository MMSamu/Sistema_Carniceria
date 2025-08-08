package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Direccion;

/**
 * Repositorio para la entidad Direccion.
 * Permite manejar la persistencia de direcciones asociadas a los clientes.
 * Las direcciones pueden ser buscadas, agregadas o eliminadas.
 */

public interface DireccionRepository extends CrudRepository<Direccion, Long> {

}