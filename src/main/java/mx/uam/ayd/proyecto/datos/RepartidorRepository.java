package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Repartidor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para la entidad Repartidor.
 * Permite realizar operaciones sobre los repartidores encargados de las entregas,
 * como su registro, disponibilidad y datos personales.
 */

public interface RepartidorRepository extends CrudRepository<Repartidor, Long> {

}