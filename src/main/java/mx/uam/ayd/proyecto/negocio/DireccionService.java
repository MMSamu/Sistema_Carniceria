package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.DireccionRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Direccion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de direcciones.
 */
@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;

    /**
     * Registra una nueva dirección.
     */
    public Direccion registrarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    /**
     * Obtiene una dirección por su id.
     */
    public Optional<Direccion> obtenerDireccionPorId(Long idDireccion) {
        return direccionRepository.findById(idDireccion);
    }

    /**
     * Iterable -> List para compatibilidad con CrudRepository.
     */
    public List<Direccion> listarDirecciones() {
        List<Direccion> list = new ArrayList<>();
        direccionRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Elimina una dirección por su id.
     */
    public void eliminarDireccion(Long idDireccion) {
        direccionRepository.deleteById(idDireccion);
    }
}
