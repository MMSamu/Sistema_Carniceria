package mx.uam.ayd.proyecto.presentacion.catalogo;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// ventana para mostrar el catálogo de productos
public class VentanaCatalogo extends JFrame {

    // controlador para manejar la lógica del catálogo
    private final ControlCatalogo control;

    // campo de texto para buscar productos
    private final JTextField campoBusqueda = new JTextField(20);

    // modelo de la lista para mostrar productos
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();

    //lista visual que muestra los productos
    private final JList<String> lista = new JList<>(modeloLista);

    // constructor de la ventana
    public VentanaCatalogo(ControlCatalogo control) {
        this.control = control;
        setTitle("Catálogo de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        construirUI();
    }

    // metodo que construye la interfaz gráfica
    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout());

        // panel superior con campo de búsqueda y botón
        JPanel barra = new JPanel();
        barra.add(new JLabel("Buscar:"));
        barra.add(campoBusqueda);
        JButton btnBuscar = new JButton("Buscar");
        barra.add(btnBuscar);

        // agregar componentes al panel principal
        panel.add(barra, BorderLayout.NORTH);
        panel.add(new JScrollPane(lista), BorderLayout.CENTER);

        // agregar acción al botón de búsqueda
        btnBuscar.addActionListener(e -> actualizar());

        getContentPane().add(panel);
    }

    // metodo que actualiza la lista de productos
    private void actualizar() {
        // obtener texto de búsqueda
        String texto = campoBusqueda.getText();

        // buscar productos usando el controlador
        List<Producto> productos = control.obtenerProductos(texto, null, null, null);

        // limpiar la lista actual
        modeloLista.clear();

        // agregar cada producto a la lista
        for (Producto p : productos) {
            // calcular precio final (oferta o precio normal)
            double precioFinal = p.isEnOferta() && p.getPrecioOferta() > 0 ? p.getPrecioOferta() : p.getPrecio();

            // crear texto para mostrar en la lista
            String linea = "Nombre: " + p.getNombre()
                    + ", Precio: $" + precioFinal
                    + (p.isEnOferta() ? " (OFERTA)" : "")
                    + ", Disponible: " + p.getCantidadDisponible();

            // agregar línea a la lista
            modeloLista.addElement(linea);
        }
    }
}