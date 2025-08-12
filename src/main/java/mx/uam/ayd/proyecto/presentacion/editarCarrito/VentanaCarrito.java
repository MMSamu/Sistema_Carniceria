package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;

public class VentanaCarrito extends JFrame {

    private final ControlCarrito control;
    private final JTextArea areaProductos;
    private final JTextField campoNota;
    private final JLabel etiquetaTotal;

    public VentanaCarrito(ControlCarrito control) {
        this.control = control;

        setTitle("Carrito de Compras");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Área para mostrar productos
        areaProductos = new JTextArea(10, 40);
        areaProductos.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaProductos);
        panel.add(scroll, BorderLayout.CENTER);

        // Campo para nota
        JPanel panelNota = new JPanel(new BorderLayout());
        campoNota = new JTextField();
        JButton btnGuardarNota = new JButton("Guardar Nota");
        btnGuardarNota.addActionListener(e -> control.agregarNota(campoNota.getText()));
        panelNota.add(new JLabel("Nota: "), BorderLayout.WEST);
        panelNota.add(campoNota, BorderLayout.CENTER);
        panelNota.add(btnGuardarNota, BorderLayout.EAST);
        panel.add(panelNota, BorderLayout.SOUTH);

        // Total
        etiquetaTotal = new JLabel("Total: $0.00");
        panel.add(etiquetaTotal, BorderLayout.NORTH);

        // Botones de acción: Aumentar, Reducir, Eliminar
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnAumentar = new JButton("Aumentar");
        btnAumentar.addActionListener(e -> modificarProductoCantidad(1));

        JButton btnReducir = new JButton("Reducir");
        btnReducir.addActionListener(e -> modificarProductoCantidad(-1));

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProductoDelCarrito());

        panelBotones.add(btnAumentar);
        panelBotones.add(btnReducir);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.NORTH);

        add(panel);
        mostrarProductos();
    }

    // Modificar la cantidad de un producto
    private void modificarProductoCantidad(int cambio) {
        // Aquí encontraríamos el producto en el carrito (por ejemplo, seleccionando el primero)
        List<ProductoPedido> productos = control.obtenerCarrito();
        if (!productos.isEmpty()) {
            ProductoPedido producto = productos.get(0);  // Asumimos que es el primero para efectos de ejemplo
            float nuevoPeso = producto.getPeso() + cambio;
            if (control.actualizarPeso(producto, nuevoPeso)) {
                mostrarProductos();
            }
        }
    }

    // Eliminar un producto del carrito
    private void eliminarProductoDelCarrito() {
        // Similar al ejemplo anterior, eliminamos el primero
        List<ProductoPedido> productos = control.obtenerCarrito();
        if (!productos.isEmpty()) {
            ProductoPedido producto = productos.get(0);
            control.eliminarProducto(producto);
            mostrarProductos();
        }
    }

    public void mostrarProductos() {
        List<ProductoPedido> productos = control.obtenerCarrito();
        StringBuilder texto = new StringBuilder();

        for (ProductoPedido p : productos) {
            texto.append(String.format("• %s - %.2f kg x $%.2f = $%.2f\n",
                    p.getNombre(), p.getPeso(), p.getPrecio(), p.calcularSubtotal()));
        }

        areaProductos.setText(texto.toString());
        etiquetaTotal.setText(String.format("Total: $%.2f", control.calcularTotal()));
    }
}