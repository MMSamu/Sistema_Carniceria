package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 
 * Repositorio para usuarios
 * 
 * @author humbertocervantes
 *
 */
public interface UsuarioRepository extends CrudRepository <Usuario, Long> {
	
	public Usuario findByNombreAndApellido(String nombre, String apellido);
	
	public List <Usuario> findByEdadBetween(int edad1, int edad2);
	

}
