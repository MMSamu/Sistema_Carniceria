package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.RepartidorRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Repartidor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de repartidores.
 */
@Service
@RequiredArgsConstructor
public class RepartidorService {

    private final RepartidorRepository repartidorRepository;

    /**
     * Registra un nuevo repartidor.
     */
    public Repartidor registrarRepartidor(Repartidor repartidor) {
        return repartidorRepository.save(repartidor);
    }

    /**
     * Obtiene un repartidor por su id.
     */
    public Optional<Repartidor> obtenerRepartidorPorId(Long idRepartidor) {
        return repartidorRepository.findById(idRepartidor);
    }

    /**
     * Iterable -> List para compatibilidad con CrudRepository.
     */
    public List<Repartidor> listarRepartidores() {
        List<Repartidor> list = new ArrayList<>();
        repartidorRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Elimina un repartidor por su id.
     */
    public void eliminarRepartidor(Long idRepartidor) {
        repartidorRepository.deleteById(idRepartidor);
    }
}
