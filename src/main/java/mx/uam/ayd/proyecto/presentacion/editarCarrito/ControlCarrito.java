package mx.uam.ayd.proyecto.presentacion.editarCarrito;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import mx.uam.ayd.proyecto.negocio.ProductoPedidoService; // <-- servicio de negocio
import mx.uam.ayd.proyecto.presentacion.principal.VentanaPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ControlCarrito {

    private final ApplicationContext applicationContext;
    private final VentanaPrincipal ventana;
    private final ProductoPedidoService productoPedidoService; // <-- agregado

    @Autowired
    public ControlCarrito(ApplicationContext applicationContext,
            VentanaPrincipal ventana,
            ProductoPedidoService productoPedidoService) { // <-- agregado
        this.applicationContext = applicationContext;
        this.ventana = ventana;
        this.productoPedidoService = productoPedidoService; // <-- agregado
    }

    public void inicia() {
        try {
            URL url = getClass().getResource("/fxml/ventana_editarcarrito.fxml");
            if (url == null) {
                ventana.mostrarError("No encuentro /fxml/ventana_editarcarrito.fxml");
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(applicationContext::getBean); // inyecta controller con Spring
            DialogPane pane = loader.load();

            // Conectar la vista con este control
            VentanaCarrito controller = loader.getController();
            controller.setControl(this);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Editar carrito");
            dialog.setDialogPane(pane);
            if (ventana.getStage() != null)
                dialog.initOwner(ventana.getStage());
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace(); // para ver la causa exacta en consola
            ventana.mostrarError("Error al abrir Editar carrito:\n" + e);
        }
    }

    // ===== Métodos invocados por la vista con el ID seleccionado =====

    public void onAumentar(long productoPedidoId) {
        try {
            productoPedidoService.aumentarCantidad(productoPedidoId);
            ventana.mostrarInfo("Cantidad aumentada.");
            // TODO: pedir a la vista refrescar tabla/totales si ya la tienes
        } catch (Exception e) {
            ventana.mostrarError("No se pudo aumentar: " + e.getMessage());
        }
    }

    public void onReducir(long productoPedidoId) {
        try {
            productoPedidoService.reducirCantidad(productoPedidoId);
            ventana.mostrarInfo("Cantidad reducida.");
        } catch (Exception e) {
            ventana.mostrarError("No se pudo reducir: " + e.getMessage());
        }
    }

    public void onEliminar(long productoPedidoId) {
        try {
            productoPedidoService.eliminarItem(productoPedidoId);
            ventana.mostrarInfo("Producto eliminado del carrito.");
        } catch (Exception e) {
            ventana.mostrarError("No se pudo eliminar: " + e.getMessage());
        }
    }
}