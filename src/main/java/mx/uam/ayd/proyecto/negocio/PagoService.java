package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PagoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pago;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los pagos.
 */
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    /**
     * Registra un nuevo pago en el sistema.
     * @param pago entidad Pago a registrar
     * @return pago registrado
     */
    public Pago registrarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    /**
     * Obtiene un pago por su identificador.
     * @param idPago identificador del pago
     * @return el pago si existe
     */
    public Optional<Pago> obtenerPagoPorId(Long idPago) {
        return pagoRepository.findById(idPago);
    }

    /**
     * Iterable -> List para compatibilidad con CrudRepository.
     * @return lista de pagos
     */
    public List<Pago> listarPagos() {
        List<Pago> list = new ArrayList<>();
        pagoRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Elimina un pago por su identificador.
     * @param idPago identificador del pago
     */
    public void eliminarPago(Long idPago) {
        pagoRepository.deleteById(idPago);
    }
}

