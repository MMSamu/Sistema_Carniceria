package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ClienteRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los clientes.
 */

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /** Registra un nuevo cliente a partir de la entidad completa. */
  
    public Cliente registrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /** Registra un nuevo cliente usando parámetros básicos. */
  
    public Cliente registrarCliente(String nombre, String apellido, String telefono, String email) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        return clienteRepository.save(cliente);
    }

    /** Obtiene un cliente por su identificador. */
  
    public Optional<Cliente> obtenerClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente);
    }

    /** Obtiene la lista completa de clientes. */
  
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);
        return clientes;
    }
}

