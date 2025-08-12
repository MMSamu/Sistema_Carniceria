package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para Grupos
 * 
 * @author humbertocervantes
 *
 */
public interface GrupoRepository extends CrudRepository <Grupo, Long> {
	
	/**
	 * Encuentra un grupo a partir de un nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public Grupo findByNombre(String nombre);
	
	

}
