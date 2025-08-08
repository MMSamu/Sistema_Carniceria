package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.PagoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pago;
import org.springframework.stereotype.Service;

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
     *
     * @param pago el pago a registrar
     * @return el pago guardado
     */

    public Pago registrarPago(Pago pago) {

        return pagoRepository.save(pago);

    }

    /**
     * Obtiene un pago por su identificador.
     *
     * @param idPago identificador del pago
     * @return un Optional que puede contener el pago, si existe
     */

    public Optional<Pago> obtenerPagoPorId(Long idPago) {

        return pagoRepository.findById(idPago);

    }

    /**
     * Obtiene todos los pagos registrados en el sistema.
     *
     * @return lista de pagos
     */

    public List<Pago> listarPagos() {

        return (List<Pago>) pagoRepository.findAll();

    }

    /**
     * Marca el pago como confirmado.
     *
     * @param idPago identificador del pago
     * @return true si fue confirmado exitosamente, false si no se encontró el pago
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