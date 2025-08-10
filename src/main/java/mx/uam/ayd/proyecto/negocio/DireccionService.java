package mx.uam.ayd.proyecto.negocio;

<<<<<<< HEAD
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
     * Registra una nueva dirección en el sistema.
     *
     * @param direccion la dirección a registrar
     * @return la dirección guardada
     */

    public Direccion registrarDireccion(Direccion direccion) {

        return direccionRepository.save(direccion);

    }

    /**
     * Obtiene una dirección por su identificador.
     *
     * @param idDireccion identificador de la dirección
     * @return un Optional que puede contener la dirección, si existe
     */

    public Optional<Direccion> obtenerDireccionPorId(Long idDireccion) {

        return direccionRepository.findById(idDireccion);

    }

    /**
     * Obtiene la lista completa de direcciones registradas.
     *
     * @return lista de direcciones
     */

    public List<Direccion> listarDirecciones() {

        return (List<Direccion>) direccionRepository.findAll();

    }
}
=======
public class DireccionService {
}
>>>>>>> 8ac433caaccbbc69b8eb84307c9754fb917738e1
