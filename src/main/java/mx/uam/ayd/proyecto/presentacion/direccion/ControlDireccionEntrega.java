package mx.uam.ayd.proyecto.presentacion.direccion;

import mx.uam.ayd.proyecto.negocio.DireccionService;
import mx.uam.ayd.proyecto.negocio.modelo.Direccion;

import javax.swing.*;
import java.awt.*;

public class ControlDireccionEntrega {

    private final DireccionService direccionService;

    public ControlDireccionEntrega(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    public void inicio(Window owner, Long pedidoId) {
        VentanaDireccionEntrega v = new VentanaDireccionEntrega(owner);

        v.btnFinalizar.addActionListener(e -> {
            try {
                Direccion d = new Direccion();
                d.setTelefono(v.txtTelefono.getText().trim());
                d.setNombre(v.txtNombre.getText().trim());
                d.setApellidos(v.txtApellidos.getText().trim());
                d.setPaisRegion(v.txtPais.getText().trim());
                d.setReferencia(v.txtReferencia.getText().trim());
                d.setCalle(v.txtCalle.getText().trim());
                d.setNumero(v.txtNumero.getText().trim());
                d.setColonia(v.txtColonia.getText().trim());
                d.setCiudad("Ciudad de México");
                d.setCodigoPostal(v.txtCp.getText().trim());
                d.setTipoVivienda(String.valueOf(v.cmbTipo.getSelectedItem()));
                d.setLatitud(parseOrNull(v.txtLat.getText()));
                d.setLongitud(parseOrNull(v.txtLon.getText()));

                Long idDir = direccionService.registrarYAsociar(d, pedidoId);

                JOptionPane.showMessageDialog(v,
                        "Dirección guardada (#"+idDir+"). Pago por TRANSFERENCIA habilitado.",
                        "Dirección", JOptionPane.INFORMATION_MESSAGE);

                v.dispose();
                // TODO: ir a pantalla de resumen / confirmación
                // controlFinalizacion.inicio(owner, pedidoId);

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(v, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(v, "Ocurrió un error al guardar la dirección.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        v.setVisible(true);
    }

    private static Double parseOrNull(String s) {
        try { return (s==null || s.isBlank()) ? null : Double.parseDouble(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }
}
