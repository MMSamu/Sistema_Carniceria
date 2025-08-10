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

    /**
     * Registra un nuevo cliente en el sistema a partir de la entidad completa.
     * @param cliente el cliente a registrar
     * @return el cliente guardado
     */
    public Cliente registrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Registra un nuevo cliente usando parámetros básicos.
     * (Conveniencia; útil cuando aún no construyes la entidad).
     * @param nombre   nombre del cliente
     * @param apellido apellido del cliente
     * @param telefono teléfono del cliente
     * @param email    correo del cliente
     * @return el cliente guardado
     */
    public Cliente registrarCliente(String nombre, String apellido, String telefono, String email) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        return clienteRepository.save(cliente);
    }

    /**
     * Obtiene un cliente por su identificador.
     * @param idCliente identificador del cliente
     * @return el cliente, si existe
     */
    public Optional<Cliente> obtenerClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente);
    }

    /**
     * Obtiene la lista completa de clientes.
     * @return lista de clientes
     */
    public List<Cliente> listarClientes() {
        // Si tu repositorio extiende JpaRepository, podrías usar: return clienteRepository.findAll();
        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);
        return clientes;
    }
}
