package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * UI sencilla para visualizar y editar el carrito.
 * Compatible con la API actual del ControlCarrito.
 */

public class VistaCarrito extends JFrame {

    private final ControlCarrito control;

    private final JTextArea areaProductos;
    private final JTextField campoNota;
    private final JLabel etiquetaTotal;

    public VistaCarrito(ControlCarrito control) {

        this.control = control;

        setTitle("Carrito de Compras");
        setSize(560, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principal
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        // Centro: lista productos
        areaProductos = new JTextArea(12, 48);
        areaProductos.setEditable(false);
        areaProductos.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        root.add(new JScrollPane(areaProductos), BorderLayout.CENTER);

        // Norte: total + botones
        etiquetaTotal = new JLabel("Total: $0.00");
        etiquetaTotal.setFont(etiquetaTotal.getFont().deriveFont(Font.BOLD, 14f));

        JButton btnAumentar = new JButton("Aumentar");
        btnAumentar.addActionListener(e -> modificarProductoCantidad(+1));

        JButton btnReducir = new JButton("Reducir");
        btnReducir.addActionListener(e -> modificarProductoCantidad(-1));

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProductoDelCarrito());

        JPanel barraAcciones = new JPanel(new BorderLayout(10, 0));
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.add(btnAumentar);
        botones.add(btnReducir);
        botones.add(btnEliminar);
        barraAcciones.add(etiquetaTotal, BorderLayout.WEST);
        barraAcciones.add(botones, BorderLayout.EAST);

        root.add(barraAcciones, BorderLayout.NORTH);

        // Sur: nota
        JPanel panelNota = new JPanel(new BorderLayout(8, 0));
        panelNota.add(new JLabel("Nota: "), BorderLayout.WEST);

        campoNota = new JTextField();
        panelNota.add(campoNota, BorderLayout.CENTER);

        JButton btnGuardarNota = new JButton("Guardar nota");
        btnGuardarNota.addActionListener(e -> control.agregarNota(campoNota.getText()));
        panelNota.add(btnGuardarNota, BorderLayout.EAST);

        root.add(panelNota, BorderLayout.SOUTH);

        // Cargar estado inicial
        mostrarProductos();
        campoNota.setText(control.obtenerNota());
    }

    /**
     * Cambia el peso del primer producto del carrito (ejemplo simple sin selección).
     * @param delta +1 aumenta 1kg, -1 reduce 1kg (ajusta según tu modelo).
     */

    private void modificarProductoCantidad(int delta) {
        List<ProductoPedido> productos = control.obtenerCarrito();
        if (productos.isEmpty()) {
            return;
        }
        ProductoPedido p = productos.get(0);

        float nuevoPeso = p.getPeso() + delta;
        if (nuevoPeso < 0f) nuevoPeso = 0f;

        // La API del control devuelve boolean por compatibilidad; aquí solo refrescamos.
        control.actualizarPeso(p, nuevoPeso);
        mostrarProductos();
    }

    /** Elimina el primer producto del carrito (ejemplo simple sin selección). */

    private void eliminarProductoDelCarrito() {

        List<ProductoPedido> productos = control.obtenerCarrito();
        if (productos.isEmpty()) {
            return;
        }

        ProductoPedido p = productos.get(0);
        control.eliminarProducto(p);
        mostrarProductos();

    }

    /** Refresca la lista y el total. Maneja BigDecimal -> double para formateo. */

    public void mostrarProductos() {

        List<ProductoPedido> productos = control.obtenerCarrito();
        StringBuilder sb = new StringBuilder();

        double total = 0.0;
        for (ProductoPedido p : productos) {
            BigDecimal subtotal = p.getSubtotal();              // BigDecimal en el modelo
            total += subtotal.doubleValue();

            sb.append(String.format("• %s  —  %.2f kg  x  $%.2f  =  $%.2f%n",
                    p.getNombre(), p.getPeso(), p.getPrecio(), subtotal.doubleValue()));
        }

        areaProductos.setText(sb.toString());
        etiquetaTotal.setText(String.format("Total: $%.2f", total));
    }
}