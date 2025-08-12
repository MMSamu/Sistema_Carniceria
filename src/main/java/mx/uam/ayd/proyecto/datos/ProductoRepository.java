package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Producto.
 * Proporciona acceso a las operaciones básicas CRUD sobre la base de datos.
 */

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}