package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.RepartidorRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Repartidor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los repartidores.
 */

@Service
@RequiredArgsConstructor
public class RepartidorService {

    private final RepartidorRepository repartidorRepository;

    /**
     * Registra un nuevo repartidor en el sistema.
     * @param repartidor entidad Repartidor a registrar
     * @return repartidor registrado
     */
  
    public Repartidor registrarRepartidor(Repartidor repartidor) {
        return repartidorRepository.save(repartidor);
    }

    /**
     * Obtiene un repartidor por su identificador.
     * @param idRepartidor identificador del repartidor
     * @return repartidor si existe
     */
  
    public Optional<Repartidor> obtenerRepartidorPorId(Long idRepartidor) {
        return repartidorRepository.findById(idRepartidor);
    }

    /**
     * Obtiene todos los repartidores registrados.
     * @return lista de repartidores
     */
  
    public List<Repartidor> listarRepartidores() {
        return repartidorRepository.findAll();
    }

    /**
     * Elimina un repartidor por su identificador.
     * @param idRepartidor identificador del repartidor
     */
  
    public void eliminarRepartidor(Long idRepartidor) {
        repartidorRepository.deleteById(idRepartidor);
    }
}
