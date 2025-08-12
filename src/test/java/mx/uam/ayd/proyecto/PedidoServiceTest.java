package mx.uam.ayd.proyecto;

import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.PedidoService;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PedidoServiceTest {
/*
    private PedidoService pedidoService;
    private PedidoRepository pedidoRepository;

    /*@BeforeEach
    void setUp() {
        pedidoRepository = Mockito.mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);
    }

    @Test
    void testListarMetodosEntrega() {
        List<String> metodos = pedidoService.listarMetodosEntrega();
        assertEquals(3, metodos.size());
        assertTrue(metodos.contains("A domicilio"));
    }

    @Test
    void testAsignarMetodoEntregaCorrecto() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setTipoEntrega(null);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido actualizado = pedidoService.asignarMetodoEntrega(1L, "A domicilio");
        assertEquals("A domicilio", actualizado.getTipoEntrega());
    }

    @Test
    void testAsignarMetodoEntregaYaAsignado() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setTipoEntrega("En tienda");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(IllegalStateException.class,
                () -> pedidoService.asignarMetodoEntrega(1L, "A domicilio"));
    }

    @Test
    void testAsignarMetodoEntregaPedidoNoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> pedidoService.asignarMetodoEntrega(1L, "A domicilio"));
    }*/
}
