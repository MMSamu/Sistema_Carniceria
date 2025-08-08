package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.EmpleadoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio relacionada con los empleados.
 */

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    /**
     * Registra un nuevo empleado en el sistema.
     *
     * @param empleado el empleado a registrar
     * @return el empleado guardado
     */

    public Empleado registrarEmpleado(Empleado empleado) {

        return empleadoRepository.save(empleado);

    }

    /**
     * Obtiene un empleado por su identificador.
     *
     * @param idEmpleado identificador del empleado
     * @return un Optional que puede contener el empleado, si existe
     */

    public Optional<Empleado> obtenerEmpleadoPorId(Long idEmpleado) {

        return empleadoRepository.findById(idEmpleado);

    }

    /**
     * Obtiene la lista completa de empleados registrados.
     *
     * @return lista de empleados
     */

    public List<Empleado> listarEmpleados() {

        return (List<Empleado>) empleadoRepository.findAll();

    }

    /**
     * Permite que un empleado procese un pedido.
     *
     * @param idEmpleado identificador del empleado
     * @param pedido     pedido a procesar
     * @return true si el pedido fue procesado exitosamente, false si no se encontró el empleado
     */

    public boolean procesarPedido(Long idEmpleado, Pedido pedido) {

        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.procesarPedido(pedido);
            empleadoRepository.save(empleado);
            return true;
        }
        return false;
    }
}