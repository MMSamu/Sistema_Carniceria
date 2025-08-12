package mx.uam.ayd.proyecto.presentacion.direccion;

import javax.swing.*;
import java.awt.*;

public class VentanaDireccionEntrega extends JDialog {

    public final JTextField txtTelefono = new JTextField();
    public final JTextField txtNombre = new JTextField();
    public final JTextField txtApellidos = new JTextField();
    public final JTextField txtPais = new JTextField("México");
    public final JTextField txtCalle = new JTextField();
    public final JTextField txtNumero = new JTextField();
    public final JTextField txtReferencia = new JTextField();
    public final JTextField txtCp = new JTextField();
    public final JTextField txtColonia = new JTextField();
    public final JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"CASA","DEPTO","OFICINA"});
    public final JTextField txtLat = new JTextField();
    public final JTextField txtLon = new JTextField();
    public final JButton btnFinalizar = new JButton("Finalizar el pedido");

    public VentanaDireccionEntrega(Window owner) {
        super(owner, "Datos de entrega", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(720, 540));

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        int y=0;

        c.gridx=0;c.gridy=y;p.add(new JLabel("Número de teléfono o móvil"), c);
        c.gridx=1;c.gridy=y++;p.add(txtTelefono, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Nombre"), c);
        c.gridx=1;c.gridy=y++;p.add(txtNombre, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Apellidos"), c);
        c.gridx=1;c.gridy=y++;p.add(txtApellidos, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("País / Región"), c);
        c.gridx=1;c.gridy=y++;p.add(txtPais, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Dirección (calle)"), c);
        c.gridx=1;c.gridy=y++;p.add(txtCalle, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Número"), c);
        c.gridx=1;c.gridy=y++;p.add(txtNumero, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Casa, apto, etc. (opcional)"), c);
        c.gridx=1;c.gridy=y++;p.add(txtReferencia, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Código postal"), c);
        c.gridx=1;c.gridy=y++;p.add(txtCp, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Colonia"), c);
        c.gridx=1;c.gridy=y++;p.add(txtColonia, c);

        c.gridx=0;c.gridy=y;p.add(new JLabel("Tipo de vivienda"), c);
        c.gridx=1;c.gridy=y++;p.add(cmbTipo, c);

        JPanel ubic = new JPanel(new GridLayout(1,4,6,6));
        ubic.add(new JLabel("Lat:")); ubic.add(txtLat);
        ubic.add(new JLabel("Lon:")); ubic.add(txtLon);
        c.gridx=0;c.gridy=y;p.add(new JLabel("Ubicación aproximada"), c);
        c.gridx=1;c.gridy=y++;p.add(ubic, c);

        JLabel soloTransfer = new JLabel("*El pago se realizará únicamente por transferencia (pago-contraentrega)");
        c.gridx=0;c.gridy=y;c.gridwidth=2;p.add(soloTransfer, c); y++;

        c.gridx=0;c.gridy=y;c.gridwidth=2;p.add(btnFinalizar, c);

        getContentPane().add(new JScrollPane(p));
        pack();
        setLocationRelativeTo(owner);
    }
}

