package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.DireccionRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Direccion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con las direcciones.
 */

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;

    /**
     * Registra una nueva dirección.
     * @param direccion entidad Dirección a registrar
     * @return dirección registrada
     */
  
    public Direccion registrarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    /**
     * Obtiene una dirección por su identificador.
     * @param idDireccion identificador de la dirección
     * @return la dirección si existe
     */
  
    public Optional<Direccion> obtenerDireccionPorId(Long idDireccion) {
        return direccionRepository.findById(idDireccion);
    }

    /**
     * Obtiene todas las direcciones registradas.
     * @return lista de direcciones
     */
  
    public List<Direccion> listarDirecciones() {
        return direccionRepository.findAll();
    }

    /**
     * Elimina una dirección por su identificador.
     * @param idDireccion identificador de la dirección
     */
  
    public void eliminarDireccion(Long idDireccion) {
        direccionRepository.deleteById(idDireccion);
    }
}

