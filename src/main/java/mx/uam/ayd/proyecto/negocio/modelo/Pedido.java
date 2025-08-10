package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido realizado por un cliente.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRepartidor")
    private Repartidor repartidor;


    /** Cliente que realiza el pedido */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    /** Fecha y hora de creación del pedido */
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    /** Estado del pedido (Pendiente, En preparación, Enviado, Entregado, Cancelado) */
    @Column(nullable = false)
    private String estado;

    /** Total del pedido */
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    /** Relación con los productos que incluye el pedido */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductoPedido> productosPedido = new ArrayList<>();

    /** Relación opcional con pago */
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pago pago;

    /* Métodos de dominio */

    /** Inicializa campos por defecto si no se han asignado. */
    public void inicializarSiNecesario() {
        if (this.fechaCreacion == null) this.fechaCreacion = LocalDateTime.now();
        if (!notBlank(this.estado)) this.estado = "Pendiente";
        if (this.total == null) this.total = BigDecimal.ZERO;
    }

    /** Agrega un producto al pedido y ajusta la relación bidireccional. */
    public void agregarProductoPedido(ProductoPedido productoPedido) {
        if (productoPedido == null) return;
        productosPedido.add(productoPedido);
        productoPedido.setPedido(this);
        recalcularTotal();
    }

    /** Elimina un producto del pedido y ajusta la relación bidireccional. */
    public void eliminarProductoPedido(ProductoPedido productoPedido) {
        if (productoPedido == null) return;
        productosPedido.remove(productoPedido);
        if (productoPedido.getPedido() == this) {
            productoPedido.setPedido(null);
        }
        recalcularTotal();
    }

    /** Recalcula el total del pedido a partir de los productos. */
    public void recalcularTotal() {
        this.total = productosPedido.stream()
                .map(ProductoPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Cambia el estado del pedido. */
    public void cambiarEstado(String nuevoEstado) {
        if (notBlank(nuevoEstado)) {
            this.estado = nuevoEstado;
        }
    }

    /** Marca el pedido como entregado. */
    public void marcarEntregado() {
        this.estado = "Entregado";
    }

    /** Marca el pedido como cancelado. */
    public void cancelar() {
        this.estado = "Cancelado";
    }

    @Override
    public String toString() {
        return "Pedido{id=" + idPedido +
                ", cliente=" + (cliente != null ? cliente.getNombreCompleto() : "null") +
                ", estado='" + safe(estado) + '\'' +
                ", total=" + total +
                '}';
    }

   /* Helpers internos */
  
    /* Comprueba que la cadena no sea null ni esté vacía después de quitar espacios. */
    private static boolean notBlank(String s) { return s != null && !s.isBlank(); }
  
    /* Devuelve el valor original sin null (en caso de null devuelve "" vacío). */ 
    private static String safe(String s) { return s == null ? "" : s.trim(); }

    // ====== CAMPO REQUERIDO POR TESTS (si aún no existe) ======
    private String tipoEntrega;

    // ====== ALIAS PARA COMPATIBILIDAD CON TESTS ======
    public void setId(long id) {
        
    // ajusta el nombre del campo si en tu entidad no se llama idPedido
    this.idPedido = id;
        
    }
    
    public long getId() {
    
    return this.idPedido != null ? this.idPedido : 0L;
        
    }

    public String getTipoEntrega() {
    return tipoEntrega;
        
    }
    
    public void setTipoEntrega(String tipoEntrega) {
    this.tipoEntrega = tipoEntrega;
    }

  
}
