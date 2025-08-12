package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Pedido;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Pedido.
 * Reglas de negocio HU-12 cubiertas desde:
 *  RN-01: listar sólo "en proceso"
 *  RN-02: orden por hora de recepción (hora asc)
 */
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    // RN-01 + RN-02
    List<Pedido> findByEstadoOrderByHoraAsc(String estado);

    Optional<Pedido> findById(Long id);

    // RN-04: al marcar "pedido listo" actualizamos timestamp
    @Transactional
    @Modifying
    @Query("UPDATE Pedido p SET p.estado = :nuevoEstado, p.timestampPedidoListo = :ts WHERE p.idPedido = :id")
    int actualizarAListo(long id, String nuevoEstado, LocalDateTime ts);

    // para “listo para entregar”
    @Transactional
    @Modifying
    @Query("UPDATE Pedido p SET p.estado = :nuevoEstado, p.timestampListoParaEntregar = :ts WHERE p.idPedido = :id")
    int actualizarAListoParaEntregar(long id, String nuevoEstado, LocalDateTime ts);
    // Si tu entidad Pedido ya tiene campo "tipoEntrega"
    List<Pedido> findByTipoEntregaContainingIgnoreCase(String tipoEntrega);

    // Si tienes relación @ManyToOne Pedido.repartidor
    // List<Pedido> findByRepartidor_IdRepartidor(Long idRepartidor);

}
