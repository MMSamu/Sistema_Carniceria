package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter @Setter @NoArgsConstructor
public class Pedido {

    // identificador
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    // tiempos base (para orden y filtros)
    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();

    @Column(nullable = false)
    private LocalTime hora = LocalTime.now();

    // HU-06: entrega y pago
    @Column(nullable = false, length = 20)       // "domicilio" | "tienda"
    private String tipoEntrega;

    @Column(nullable = false, length = 20)       // "efectivo"  | "transferencia"
    private String metodoPago;

    // datos complementarios
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 500)
    private String observaciones;                 // notas especiales del cliente

    // HU-12: flujo de estado del pedido
    @Column(nullable = false, length = 30)        // "en proceso" | "pedido listo" | "listo para entregar"
    private String estado = "en proceso";

    private LocalDateTime timestampPedidoListo;       // se llena al pasar a "pedido listo"
    private LocalDateTime timestampListoParaEntregar; // se llena al pasar a "listo para entregar"

    // imports/atributos/relaciones
    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<ProductoPedido> items = new java.util.ArrayList<>();
private String telefonoContacto;  // opcional, para notificaciones
    // helpers
    public void addItem(ProductoPedido it) {
        it.setPedido(this);
        items.add(it);
    }

    public void removeItem(ProductoPedido it) {
        items.remove(it);
        it.setPedido(null);      // rompe la FK para que 'orphanRemoval' funcione
    }

    @OneToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccionEntrega;           // solo aplica si tipoEntrega = "domicilio"

    public void marcarPedidoListo() {
        if (!"en proceso".equals(estado)) {
            throw new IllegalStateException("Sólo puede marcarse 'pedido listo' desde 'en proceso'.");
        }
        estado = "pedido listo";
        timestampPedidoListo = LocalDateTime.now();
    }

    public void marcarListoParaEntregar() {
        if (!"pedido listo".equals(estado)) {
            throw new IllegalStateException("Sólo puede pasar a 'listo para entregar' después de 'pedido listo'.");
        }
        estado = "listo para entregar";
        timestampListoParaEntregar = LocalDateTime.now();
    }
}
