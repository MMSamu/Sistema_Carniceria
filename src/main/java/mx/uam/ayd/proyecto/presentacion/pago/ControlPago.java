package mx.uam.ayd.proyecto.presentacion.pago;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.negocio.PagoService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ControlPago {

    private final PagoService pagoService;

    /**
     * Registra el pago y (si no exixte ) da de alta al cliene por el sistema
     */
    public boolean registrarPago(String nombre, String apellido, String telefono, String metodo){
        if (nombre == null || nombre.isBlank()) return false;
        if (apellido == null || apellido.isBlank()) return false;
        if (telefono == null || telefono.isBlank()) return false;
        if (metodo == null || metodo.isBlank()) return false;

        return pagoService.registrarPago(nombre.trim(), apellido.trim(), telefono.trim(), metodo.trim());
    }

}
