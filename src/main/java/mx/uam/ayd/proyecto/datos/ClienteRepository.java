package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Cliente;

/**
 * Repositorio para la entidad Cliente.
 * Proporciona métodos para realizar operaciones CRUD sobre los registros de clientes
 * como guardar, buscar, actualizar y eliminar.
 */

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}