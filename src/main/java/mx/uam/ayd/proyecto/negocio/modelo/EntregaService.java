package mx.uam.ayd.proyecto.negocio.modelo;

import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    public String obtenerDatosEntrega(Long idPedido) {
        // Aquí puedes leer de tu entidad Pedido/Entrega real
        return "Dirección: Calle 1 #123 | Empresa: 55-11-22-33 | Repartidor: 55-55-55-55";
    }
}
