package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.PagoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uam.ayd.proyecto.negocio.modelo.Cliente;

import java.util.Optional;

/**
 * Servicio para operaciones relacionadas con los pagos de los clientes.
 */
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ClienteService clienteService; // Inyectamos ClienteService

    /**
     * Registra un pago y el cliente en el sistema.
     * 
     * @param nombre     El nombre del cliente.
     * @param apellido   El apellido del cliente.
     * @param telefono   El teléfono del cliente.
     * @param metodoPago El método de pago seleccionado (efectivo, tarjeta).
     * @return true si el pago fue registrado exitosamente, false si ocurrió un
     *         error.
     */

    public boolean registrarPago(String nombre, String apellido, String telefono, String metodoPago) {
        // primero, registrar al cliente usando ClienteService
        Cliente cliente = clienteService.registrarCliente(nombre, apellido, telefono, metodoPago); // Utilizamos
                                                                                                   // ClienteService
        if (cliente == null) {
            return false;
        }

        // Luego, creamos el objeto de pago
        Pago pago = new Pago();
        pago.setCliente(cliente); // Asociamos el cliente con el pago

        // Guardamos el pago en la base de datos
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
