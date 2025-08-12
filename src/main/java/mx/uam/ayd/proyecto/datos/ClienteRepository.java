package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para la entidad Cliente.
 * Proporciona métodos para realizar operaciones CRUD sobre los registros de clientes
 * como guardar, buscar, actualizar y eliminar.
 */

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}