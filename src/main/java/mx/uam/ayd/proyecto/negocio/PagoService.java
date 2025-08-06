package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.PagoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para operaciones relacionadas con los pagos de los clientes.
 */
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    /**
     * Registra un nuevo pago en el sistema.
     *
     * @param pago el objeto Pago a registrar
     * @return true si el pago fue válido y se guardó, false en caso contrario
     */
    public boolean registrarPago(Pago pago) {
        if (pago == null || pago.getMonto() <= 0) {
            return false;
        }
        pagoRepository.save(pago);
        return true;
    }

    /**
     * Busca un pago por su identificador único.
     *
     * @param id el ID del pago a buscar
     * @return un Optional con el pago si existe, vacío si no
     */
    public Optional<Pago> buscarPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    /**
     * Devuelve todos los pagos registrados en el sistema.
     *
     * @return iterable con todos los pagos
     */
    public Iterable<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    /**
     * Confirma un pago si existe, cambiando su estado a "Confirmado".
     *
     * @param idPago el ID del pago a confirmar
     * @return true si se confirmó, false si no se encontró
     */
    public boolean confirmarPago(Long idPago) {
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.confirmarPago();
            pagoRepository.save(pago);
            return true;
        }
        return false;
    }


}
