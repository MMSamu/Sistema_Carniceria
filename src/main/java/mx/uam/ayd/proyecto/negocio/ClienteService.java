package mx.uam.ayd.proyecto.negocio;

import mx.uam.ayd.proyecto.datos.ClienteRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Registra un nuevo cliente con sus datos.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param telefono Teléfono del cliente.
     * @param metodoPago (Ignorado por ahora, ya que Cliente no lo usa)
     * @return El cliente registrado.
     */
    public Cliente registrarCliente(String nombre, String apellido, String telefono, String metodoPago) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setEmail("");
        cliente.setMetodoPago(metodoPago);

        return clienteRepository.save(cliente);
    }
}
