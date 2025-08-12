package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio unificado de notificaciones (HU-05 / HU-10).
 * Mantiene el mismo nombre de interface para no romper llamadas existentes.
 * Usamos JpaRepository (extiende CrudRepository), por lo que métodos básicos siguen disponibles.
 */
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Todas las notificaciones de un pedido (más recientes primero)
    List<Notificacion> findByIdPedidoOrderByFechaHoraDesc(Long idPedido);

    // Solo las de HU-10 (estado del pedido)
    List<Notificacion> findByIdPedidoAndTipoOrderByFechaHoraDesc(Long idPedido, TipoNotificacion tipo);

    // Para la campanita / badge de UI
    long countByIdPedidoAndLeidaFalse(Long idPedido);
}
