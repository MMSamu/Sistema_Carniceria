package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// servicio que maneja la lógica de negocio de productos
// implementa la HU-01 (mostrar productos disponibles en catálogo)
@Service
@RequiredArgsConstructor
public class ProductoService {

    // repositorio para acceder a los datos de productos
    private final ProductoRepository productoRepository;

    // metodo que filtra productos según los criterios dados
    // implementa HU-01: filtra productos disponibles para el catálogo
    public List<Producto> obtenerProductosFiltrados(String textoBusqueda,
                                                    String tipoCorte,
                                                    Boolean esMenudeo,
                                                    Boolean esCongelado) {
        List<Producto> resultado = new ArrayList<>();

        // Normaliza parámetros
        String q = (textoBusqueda == null) ? null : textoBusqueda.trim().toLowerCase();
        if (q != null && q.isEmpty()) {
            q = null;
        }
        String corteParam = (tipoCorte == null) ? null : tipoCorte.trim();
        if (corteParam != null && corteParam.isEmpty()) {
            corteParam = null;
        }

        // Puede ser Iterable (CrudRepository) o List (JpaRepository); ambos sirven en for-each
        Iterable<Producto> fuente = productoRepository.findAll();

        for (Producto p : fuente) {
            if (p == null) {
                continue;
            }

            // cantidad disponible > 0
            if (p.getCantidadDisponible() <= 0) {
                continue;
            }

            // filtro por texto en nombre (case-insensitive)
            if (q != null) {
                String nombre = p.getNombre();
                if (nombre == null || !nombre.toLowerCase().contains(q)) {
                    continue;
                }
            }

            // filtro por tipo de corte (case-insensitive)
            if (corteParam != null) {
                String corteProd = p.getTipoCorte();
                if (corteProd == null || !corteProd.equalsIgnoreCase(corteParam)) {
                    continue;
                }
            }

            // filtro por venta al menudeo
            if (esMenudeo != null) {
                if (p.isEsMenudeo() != esMenudeo.booleanValue()) {
                    continue;
                }
            }

            // filtro por congelado
            if (esCongelado != null) {
                if (p.isEsCongelado() != esCongelado.booleanValue()) {
                    continue;
                }
            }

            resultado.add(p);
        }

        return resultado;
    }
}