package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Representa un pedido realizado por un cliente.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idPedido;
    private LocalDate fecha;
    private LocalTime hora;
    private String tipoEntrega;
    private String estadoEntrega;
    private String observaciones;
    private Double total;

    /**
     * Cliente que realizó este pedido.
     */

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Dirección asociada al pedido (en caso de entrega a domicilio).
     */

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    /**
     * Empleado que procesó este pedido (opcional).
     */

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    /**
     * Repartidor asignado para entregar el pedido (solo si aplica).
     */

    @ManyToOne
    @JoinColumn(name = "id_repartidor")
    private Repartidor repartidor;

    /**
     * Relación uno a uno con el pago de este pedido.
     */

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pago pago;

    /**
     * Lista de productos incluidos en el pedido.
     */

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ProductoPedido> productos;

    /**
     * Metodo para marcar el pedido como confirmado.
     */

    public void confirmar() {

        this.estadoEntrega = "Confirmado";

    }

    /**
     * Metodo que cancela el pedido.
     */

    public void cancelar() {

        this.estadoEntrega = "Cancelado";

    }

    /**
     * Calcula el total sumando los precios de cada productoPedido.
     */

    public void calcularTotal() {

        if (productos != null && !productos.isEmpty()) {
            this.total = productos.stream()
                    .mapToDouble(ProductoPedido::getSubtotal)
                    .sum();

        } else {

            this.total = 0.0;

        }

    }

    /**
     * Confirma que el pedido ha sido entregado.
     */

    public void confirmarEntrega() {

        this.estadoEntrega = "Entregado";

    }

    /**
     * Actualiza el estado del pedido.
     * @param nuevoEstado nuevo estado deseado
     */

    public void actualizarEstado(String nuevoEstado) {

        this.estadoEntrega = nuevoEstado;

    }
}
