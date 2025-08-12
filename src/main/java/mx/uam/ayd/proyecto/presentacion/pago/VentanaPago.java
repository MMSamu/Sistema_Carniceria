package mx.uam.ayd.proyecto.presentacion.pago;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class VentanaPago extends JFrame {

    // Campos del formulario
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtNumProductos = new JTextField();
    private final JRadioButton rbEfectivo = new JRadioButton("Efectivo", true);
    private final JRadioButton rbTransferencia = new JRadioButton("Transferencia");
    private final JLabel lblSubtotal = new JLabel();
    private final JLabel lblTotal = new JLabel();

    private final NumberFormat monedaMx = NumberFormat.getCurrencyInstance(new Locale("es","MX"));

    public VentanaPago(BigDecimal subtotal) {
        construirUI(subtotal);
    }

    private void construirUI(BigDecimal subtotal) {
        Color naranja = new Color(255,153,0);
        Color grisBarra = new Color(220,220,220);

        setTitle("Información adicional - Recogida en tienda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 640);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(8,8,8,8));
        setContentPane(root);

        // Barra gris superior
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(grisBarra);
        barra.setPreferredSize(new Dimension(0,56));
        JLabel titulo = new JLabel("Cárnicos Benjamín");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        titulo.setBorder(new EmptyBorder(0,12,0,0));
        barra.add(titulo, BorderLayout.WEST);
        root.add(barra, BorderLayout.NORTH);

        // Cuerpo naranja
        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(naranja);
        cuerpo.setBorder(new EmptyBorder(16,20,16,20));
        root.add(cuerpo, BorderLayout.CENTER);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,8,8,8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        Font fTitulo = getFont().deriveFont(Font.BOLD, 16f);

        // Contacto
        JLabel s1 = new JLabel("Contacto"); s1.setFont(fTitulo);
        g.gridx=0; g.gridy=0; g.gridwidth=2; cuerpo.add(s1,g);
        g.gridy++; cuerpo.add(labeled("Número de teléfono o móvil", txtTelefono), g);

        // Recogida en tienda
        g.gridy++; JLabel s2 = new JLabel("Recogida en tienda"); s2.setFont(fTitulo);
        cuerpo.add(s2,g);
        g.gridy++; g.gridwidth=1; g.gridx=0; cuerpo.add(labeled("Nombre", txtNombre), g);
        g.gridx=1; cuerpo.add(labeled("Apellido", txtApellido), g);
        g.gridy++; g.gridx=0; g.gridwidth=2;
        cuerpo.add(labeled("Número de productos a adquirir", txtNumProductos), g);

        g.gridy++;
        JLabel leyenda = new JLabel("*Es obligatorio traer una identificación oficial que coincida con los datos del comprador");
        leyenda.setFont(leyenda.getFont().deriveFont(Font.PLAIN, 11f));
        cuerpo.add(leyenda, g);

        // Pago (radios)
        g.gridy++; JLabel s3 = new JLabel("Pago"); s3.setFont(fTitulo);
        cuerpo.add(s3, g);
        ButtonGroup grupo = new ButtonGroup(); grupo.add(rbEfectivo); grupo.add(rbTransferencia);
        JPanel pagoPanel = new JPanel(new GridLayout(2,1,6,6)); pagoPanel.setOpaque(false);
        pagoPanel.add(wrap(rbEfectivo)); pagoPanel.add(wrap(rbTransferencia));
        g.gridy++; cuerpo.add(pagoPanel, g);

        // Resumen
        g.gridy++; JLabel s4 = new JLabel("Resumen del pedido"); s4.setFont(fTitulo);
        cuerpo.add(s4, g);
        lblSubtotal.setText("Subtotal: " + monedaMx.format(subtotal));
        lblTotal.setText("Total: " + monedaMx.format(subtotal)); // ajusta si hay cargos
        JPanel resumen = new JPanel(new GridLayout(2,1,4,4)); resumen.setOpaque(false);
        resumen.add(lblSubtotal); resumen.add(lblTotal);
        g.gridy++; cuerpo.add(resumen, g);

        // Botón Finalizar
        JButton btnFinalizar = new JButton("Finalizar el pedido");
        btnFinalizar.setFont(btnFinalizar.getFont().deriveFont(Font.BOLD, 14f));
        g.gridy++; cuerpo.add(btnFinalizar, g);

        // Acción: solo valida y deja TODO para enlazar a servicio/BD
        btnFinalizar.addActionListener(this::onFinalizar);
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(6,4));
        p.setOpaque(false);
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
    private JPanel wrap(JComponent c){ JPanel p=new JPanel(new BorderLayout()); p.setOpaque(false); p.add(c); return p; }

    private void onFinalizar(ActionEvent e) {
        // Validaciones mínimas
        if (txtTelefono.getText().isBlank() || txtNombre.getText().isBlank()
                || txtApellido.getText().isBlank() || txtNumProductos.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int n = Integer.parseInt(txtNumProductos.getText().trim());
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de productos debe ser entero positivo.", "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String metodo = rbEfectivo.isSelected() ? "Efectivo" : "Transferencia";

        // TODO: aquí enlazas con tu servicio (PagoService / PedidoService)
        // Ejemplo futuro:
        // pagoService.registrarPago(txtNombre.getText().trim(), txtApellido.getText().trim(),
        //                           txtTelefono.getText().trim(), metodo);

        JOptionPane.showMessageDialog(this, "Formulario válido.\nMétodo: " + metodo + "\n(Conectar a servicio/BD aquí)");
        dispose();
    }

    // Getters por si necesitas leer desde fuera
    public String getTelefono(){ return txtTelefono.getText().trim(); }
    public String getNombre(){ return txtNombre.getText().trim(); }
    public String getApellido(){ return txtApellido.getText().trim(); }
    public int getNumProductos(){ return Integer.parseInt(txtNumProductos.getText().trim()); }
    public String getMetodoPago(){ return rbEfectivo.isSelected() ? "Efectivo" : "Transferencia"; }


}
