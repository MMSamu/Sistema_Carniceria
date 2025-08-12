package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Notificacion;
import mx.uam.ayd.proyecto.negocio.modelo.Notificacion.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio unificado para manejar las notificaciones del sistema.
 *
 * - Cubre tanto HU-05 (auditoría de envíos) como HU-10 (notificaciones de estado de pedido).
 * - Extiende de JpaRepository, que a su vez hereda de CrudRepository,
 *   por lo que se dispone de operaciones CRUD estándar (findAll, findById, save, delete, etc.).
 * - Se agregan métodos de consulta personalizados basados en la convención de nombres de Spring Data JPA.
 *
 * El nombre de la interfaz se mantiene igual para no romper compatibilidad con código existente.
 */
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    /**
     * Obtiene todas las notificaciones asociadas a un pedido, ordenadas desde la más reciente a la más antigua.
     *
     * @param idPedido ID del pedido asociado.
     * @return Lista de notificaciones ordenadas por fecha y hora en orden descendente.
     */
    List<Notificacion> findByIdPedidoOrderByFechaHoraDesc(Long idPedido);

    /**
     * Obtiene todas las notificaciones de un pedido filtrando por tipo específico (ej. ESTADO_PEDIDO),
     * ordenadas de la más reciente a la más antigua.
     *
     * @param idPedido ID del pedido asociado.
     * @param tipo Tipo de notificación (ver enum TipoNotificacion).
     * @return Lista de notificaciones filtradas y ordenadas.
     */
    List<Notificacion> findByIdPedidoAndTipoOrderByFechaHoraDesc(Long idPedido, TipoNotificacion tipo);

    /**
     * Cuenta cuántas notificaciones no leídas tiene un pedido.
     * Esto se puede usar en la interfaz gráfica para mostrar un contador (badge) o una campanita de avisos.
     *
     * @param idPedido ID del pedido asociado.
     * @return Número de notificaciones no leídas para ese pedido.
     */
    long countByIdPedidoAndLeidaFalse(Long idPedido);
}

