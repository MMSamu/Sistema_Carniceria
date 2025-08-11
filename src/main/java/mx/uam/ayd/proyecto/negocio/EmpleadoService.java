package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.EmpleadoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor

public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    /* CRUD & CONSULTAS */

    public Empleado registrarEmpleado(Empleado empleado) {

        return empleadoRepository.save(empleado);

    }

    public Empleado obtenerEmpleadoPorId(Long idEmpleado) {

        return empleadoRepository.findById(idEmpleado)

                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado: " + idEmpleado));

    }

    public List<Empleado> listarEmpleados() {

        List<Empleado> out = new ArrayList<>();

        empleadoRepository.findAll().forEach(out::add);

        return out;

    }

    /*  OPERACIONES DE NEGOCIO  */

    public void actualizarContacto(Long idEmpleado, String telefono, String email) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.actualizarContacto(telefono, email);

        empleadoRepository.save(e);

    }

    public void cambiarPuesto(Long idEmpleado, String nuevoPuesto) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.cambiarPuesto(nuevoPuesto);

        empleadoRepository.save(e);

    }

    public void cambiarTurno(Long idEmpleado, String nuevoTurno) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.cambiarTurno(nuevoTurno);

        empleadoRepository.save(e);

    }

    public void ajustarSalario(Long idEmpleado, BigDecimal nuevoSalario) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.ajustarSalario(nuevoSalario);

        empleadoRepository.save(e);

    }

    public void marcarActivo(Long idEmpleado, boolean activo) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.marcarActivo(activo);

        empleadoRepository.save(e);

    }

    /*  COMPAT CON OTRAS CAPAS */

    /** Algunas UIs/servicios piden procesar pedido por id de empleado. */

    public void procesarPedido(Long idEmpleado, Pedido pedido) {

        Empleado e = obtenerEmpleadoPorId(idEmpleado);

        e.procesarPedido(pedido);

        empleadoRepository.save(e);

    }

    /** Overload por si ya traen el Empleado cargado. */

    public void procesarPedido(Empleado empleado, Pedido pedido) {

        if (empleado == null) throw new IllegalArgumentException("Empleado nulo");

        empleado.procesarPedido(pedido);

        empleadoRepository.save(empleado);

    }
}
