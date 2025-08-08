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
     *
     * @param repartidor el repartidor a registrar
     * @return el repartidor guardado
     */

    public Repartidor registrarRepartidor(Repartidor repartidor) {

        return repartidorRepository.save(repartidor);

    }

    /**
     * Obtiene un repartidor por su ID.
     *
     * @param idRepartidor identificador del repartidor
     * @return el repartidor si existe
     */

    public Optional<Repartidor> obtenerRepartidorPorId(Long idRepartidor) {

        return repartidorRepository.findById(idRepartidor);

    }

    /**
     * Lista todos los repartidores registrados.
     *
     * @return lista de repartidores
     */

    public List<Repartidor> listarRepartidores() {

        return (List<Repartidor>) repartidorRepository.findAll();
    }
}