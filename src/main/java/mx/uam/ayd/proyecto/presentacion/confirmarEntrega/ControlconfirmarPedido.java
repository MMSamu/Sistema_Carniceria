package mx.uam.ayd.proyecto.presentacion.confirmarEntrega;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import mx.uam.ayd.proyecto.negocio.PedidoService;
import mx.uam.ayd.proyecto.presentacion.principal.VentanaPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ControlconfirmarPedido {

    private final ApplicationContext applicationContext;
    private final VentanaPrincipal ventana;
    private final PedidoService pedidoService;

    private VistaConfirmarEntrega vista;

    @Autowired
    public ControlconfirmarPedido(ApplicationContext applicationContext,
            VentanaPrincipal ventana,
            PedidoService pedidoService) {
        this.applicationContext = applicationContext;
        this.ventana = ventana;
        this.pedidoService = pedidoService;
    }

    public void inicia() {
        try {
            // Asegúrate que este archivo exista en resources/fxml/
            URL url = getClass().getResource("/fxml/ventana_confirmarPedido.fxml");
            if (url == null) {
                ventana.mostrarError("No encuentro /fxml/ventana_confirmarPedido.fxml");
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(applicationContext::getBean);
            DialogPane pane = loader.load();

            vista = loader.getController();
            vista.setControl(this);
            vista.cargar(pedidoService.listarListoParaEntregar());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Confirmar pedido");
            dialog.setDialogPane(pane);
            if (ventana.getStage() != null)
                dialog.initOwner(ventana.getStage());
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            ventana.mostrarError("Error al abrir Confirmar pedido:\n" + e.getMessage());
        }
    }

    public void confirmarEntrega(Long idPedido) {
        try {
            pedidoService.confirmarEntrega(idPedido);
            ventana.mostrarInfo("Entrega confirmada para el pedido " + idPedido);
        } catch (Exception e) {
            ventana.mostrarError("No se pudo confirmar entrega: " + e.getMessage());
        }
    }

    public void recargarLista() {
        if (vista != null) {
            vista.cargar(pedidoService.listarListoParaEntregar());
        }
    }
}