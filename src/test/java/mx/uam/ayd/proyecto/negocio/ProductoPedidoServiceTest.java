package mx.uam.ayd.proyecto.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductoPedidoServiceTest {

    private ProductoPedidoService servicio;
    private ProductoPedidoRepository repoMock;

    @BeforeEach
    public void setUp() throws Exception {
        repoMock = mock(ProductoPedidoRepository.class);
        servicio = new ProductoPedidoService();

        // Inyección del mock usando reflexión
        java.lang.reflect.Field field = ProductoPedidoService.class.getDeclaredField("productoPedidoRepository");
        field.setAccessible(true); // habilitamos el acceso a private
        field.set(servicio, repoMock);
    }

    @Test
    public void testAgregarProducto() {
        ProductoPedido producto = new ProductoPedido();
        producto.setNombre("Carne Molida");
        producto.setPrecio(90);
        producto.setPeso(1);

        servicio.agregarProducto(producto);
        verify(repoMock).save(producto);
    }

    @Test
    public void testEliminarProducto() {
        ProductoPedido producto = new ProductoPedido();
        servicio.eliminarProducto(producto);
        verify(repoMock).delete(producto);
    }

    @Test
    public void testActualizarPesoCorrecto() {
        ProductoPedido producto = new ProductoPedido();
        producto.setPeso(1);

        boolean resultado = servicio.actualizarPesoProducto(producto, 2);
        assertTrue(resultado);
        assertEquals(2, producto.getPeso());
        verify(repoMock).save(producto);
    }

    @Test
    public void testActualizarPesoInvalido() {
        ProductoPedido producto = new ProductoPedido();
        producto.setPeso(1);

        boolean resultado = servicio.actualizarPesoProducto(producto, -5);
        assertFalse(resultado);
        verify(repoMock, never()).save(any());
    }

    @Test
    public void testCalcularTotal() {
        ProductoPedido p1 = new ProductoPedido();
        p1.setPrecio(50);
        p1.setPeso(2);

        ProductoPedido p2 = new ProductoPedido();
        p2.setPrecio(30);
        p2.setPeso(1);

        when(repoMock.findAll()).thenReturn(Arrays.asList(p1, p2));

        float total = servicio.calcularTotal();
        assertEquals(130.0f, total, 0.01);
    }

    @Test
    public void testAgregarYObtenerNota() {
        servicio.agregarNota("Por favor, sin grasa");
        assertEquals("Por favor, sin grasa", servicio.obtenerNota());
    }

    @Test
    public void testAgregarNotaMuyLarga() {
        String notaLarga = "a".repeat(201);
        servicio.agregarNota(notaLarga);
        assertNull(servicio.obtenerNota()); // no se debe guardar si excede 200
    }
}