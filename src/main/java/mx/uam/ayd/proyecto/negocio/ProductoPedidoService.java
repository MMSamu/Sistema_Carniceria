// src/main/java/mx/uam/ayd/proyecto/negocio/ProductoPedidoService.java
package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoPedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoPedidoService {

    private final ProductoPedidoRepository productoPedidoRepository;

    // Carrito en memoria (para UI/tests)

    private final List<ProductoPedido> carrito = new ArrayList<>();

    private String nota;

    // Ctor sin args para tests que hacían new ProductoPedidoService()

    public ProductoPedidoService() {

        this.productoPedidoRepository = null; // modo memoria

    }

    // UI esperaba boolean


    public boolean agregarProducto(ProductoPedido pp) {

        if (pp == null) return false;

        carrito.add(pp);

        return true;

    }


    public List<ProductoPedido> obtenerProductosDelCarrito() {

        return new ArrayList<>(carrito);

    }


    public void eliminarProducto(ProductoPedido pp) {

        carrito.remove(pp);

    }


    public void actualizarPesoProducto(ProductoPedido pp, float nuevoPeso) {

        if (pp != null) {

            pp.setPeso(nuevoPeso);

        }

    }


    public BigDecimal calcularTotal() {

        return carrito.stream()

                .map(ProductoPedido::getSubtotal)

                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


    public void agregarNota(String n) { this.nota = n; }

    public String obtenerNota() { return this.nota; }

    // --- Persistencia real (si/ cuando se requiera) ---

    public void eliminarProductoPedido(Long id) {

        if (productoPedidoRepository != null) {

            productoPedidoRepository.deleteById(id);

        }

    }

}

