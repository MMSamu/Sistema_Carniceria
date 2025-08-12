package mx.uam.ayd.proyecto.negocio;

import lombok.RequiredArgsConstructor;
import mx.uam.ayd.proyecto.datos.DireccionRepository;
import mx.uam.ayd.proyecto.datos.PedidoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Direccion;
import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de HU-06: valida y registra direcciones y las asocia a un pedido.
 * Reglas:
 *  - Teléfono de 10 dígitos
 *  - Código postal de 5 dígitos
 *  - Cobertura CDMX (lat/lon en rango)
 *  - Al asociar a pedido: tipoEntrega = "DOMICILIO" y metodoPago = "TRANSFERENCIA"
 */
@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final PedidoRepository pedidoRepository;

    // validaciones basicas
    public boolean validarTelefono(String numero) {
        return numero != null && numero.replaceAll("\\D", "").matches("\\d{10}");
    }

    public boolean validarCodigoPostal(String cp) {
        return cp != null && cp.matches("\\d{5}");
    }

    // cobertura simple CDMX
    public boolean verificarAreaCdmx(Double lat, Double lon) {
        if (lat == null || lon == null) return false;
        double latMin = 19.047, latMax = 19.593;
        double lonMin = -99.365, lonMax = -98.941;
        return lat >= latMin && lat <= latMax && lon >= lonMin && lon <= lonMax;
    }

    // helpers
    private void normalizarCampos(Direccion d) {
        if (d.getCiudad() == null || d.getCiudad().isBlank())
            d.setCiudad("Ciudad de México");
        if (d.getPaisRegion() == null || d.getPaisRegion().isBlank())
            d.setPaisRegion("México");
    }

    private void validarOExplotar(Direccion d) {
        if (d == null) throw new IllegalArgumentException("La dirección es requerida.");
        if (!validarTelefono(d.getTelefono()))
            throw new IllegalArgumentException("El teléfono debe tener 10 dígitos.");
        if (!validarCodigoPostal(d.getCodigoPostal()))
            throw new IllegalArgumentException("El código postal debe tener 5 dígitos.");
        if (!verificarAreaCdmx(d.getLatitud(), d.getLongitud()))
            throw new IllegalArgumentException("Dirección fuera de cobertura (solo CDMX).");
    }

    // API principal

    /**
     * Registra la dirección y la asocia al pedido indicado.
     * Además, impone:
     *  - tipoEntrega = "DOMICILIO"
     *  - metodoPago  = "TRANSFERENCIA"
     * @return id de la dirección persistida.
     */
    @Transactional
    public Long registrarYAsociar(Direccion dir, Long pedidoId) {
        normalizarCampos(dir);
        validarOExplotar(dir);

        Direccion guardada = direccionRepository.save(dir);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));

        pedido.setDireccionEntrega(guardada);
        pedido.setTipoEntrega("DOMICILIO");
        pedido.setMetodoPago("TRANSFERENCIA");

        pedidoRepository.save(pedido);
        return guardada.getIdDireccion();
    }

    /**
     * Guarda solo la dirección (sin asociar). Útil si queremos pre-crear o reutilizar.
     */
    @Transactional
    public Direccion guardarSoloDireccion(Direccion dir) {
        normalizarCampos(dir);
        validarOExplotar(dir);
        return direccionRepository.save(dir);
    }

    // reemplaza la dirección asociada a un pedido (manteniendo DOMICILIO/TRANSFERENCIA)
    @Transactional
    public Direccion actualizarDireccionDePedido(Long pedidoId, Direccion nueva) {
        normalizarCampos(nueva);
        validarOExplotar(nueva);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));

        Direccion guardada = direccionRepository.save(nueva);
        pedido.setDireccionEntrega(guardada);
        pedido.setTipoEntrega("DOMICILIO");
        pedido.setMetodoPago("TRANSFERENCIA");
        pedidoRepository.save(pedido);

        return guardada;
    }
}
