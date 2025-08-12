package mx.uam.ayd.proyecto;

import mx.uam.ayd.proyecto.negocio.PagoService;
import mx.uam.ayd.proyecto.presentacion.pago.ControlPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ControlPagoTest {
    @Mock
    private PagoService pagoService;

    @InjectMocks
    private ControlPago control; // solo prueba registrarPago (no requiere JavaFX)

    @BeforeEach
    void setup() {
        // nada especial; @InjectMocks ya inyecta el mock en el constructor
    }

    @Test
    void registrarPago_exitoso_llamaServicioYDevuelveTrue() {
        when(pagoService.registrarPago("Juan","Pérez","5551234567","Efectivo"))
                .thenReturn(true);

        boolean ok = control.registrarPago("Juan","Pérez","5551234567","Efectivo");

        assertTrue(ok);
        verify(pagoService).registrarPago("Juan","Pérez","5551234567","Efectivo");
    }

    @Test
    void registrarPago_camposVacios_noLlamaServicioYDevuelveFalse() {
        boolean ok = control.registrarPago("", "Pérez", "5551234567", "Efectivo");
        assertFalse(ok);
        verify(pagoService, never()).registrarPago(any(), any(), any(), any());
    }

    @Test
    void registrarPago_trimeaEntradasAntesDeLlamarServicio() {
        when(pagoService.registrarPago("Ana","López","5512345678","Transferencia"))
                .thenReturn(true);

        boolean ok = control.registrarPago("  Ana  ", "  López ", " 5512345678 ", " Transferencia ");

        assertTrue(ok);
        verify(pagoService).registrarPago("Ana","López","5512345678","Transferencia");
    }

    @Test
    void registrarPago_metodoVacio_false() {
        boolean ok = control.registrarPago("Ana","López","5512345678","");
        assertFalse(ok);
        verifyNoInteractions(pagoService);
    }
}

