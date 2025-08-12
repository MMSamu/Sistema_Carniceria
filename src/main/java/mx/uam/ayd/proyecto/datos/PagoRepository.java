package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Pago;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para la entidad Pago.
 * Maneja el almacenamiento y recuperación de los registros de pagos
 * realizados por los clientes, ya sea en línea o en efectivo.
 */

public interface PagoRepository extends CrudRepository<Pago, Long> {

}