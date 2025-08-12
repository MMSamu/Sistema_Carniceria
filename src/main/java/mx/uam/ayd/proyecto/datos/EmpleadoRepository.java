package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para la entidad Empleado.
 * Permite realizar operaciones básicas sobre los empleados que
 * procesan pedidos y atienden clientes en el sistema.
 */

public interface EmpleadoRepository extends CrudRepository<Empleado, Long> {

}