package mx.uam.ayd.proyecto.presentacion.principal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import mx.uam.ayd.proyecto.presentacion.checklist.ControlChecklist;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;

@Component
public class VentanaPrincipal {

	private final ApplicationContext applicationContext;

	public VentanaPrincipal(ApplicationContext applicationContext) { // <-- NUEVO constructor con Spring
		this.applicationContext = applicationContext;
	}


	@Getter
	private Stage stage;
	private ControlPrincipal control;
	private boolean initialized = false;

	public void setControlPrincipal(ControlPrincipal control) {
		this.control = control;
	}

	private void initializeUI() {
		if (initialized) return;

		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}

		try {
			URL fxml = getClass().getResource("/fxml/ventana-principal.fxml");
			if (fxml == null) {
				mostrarError("No se encontró el FXML: /fxml/ventana-principal-spring.fxml");
				return;
			}

			FXMLLoader loader = new FXMLLoader(fxml);
			//loader.setController(this);
			loader.setControllerFactory(applicationContext::getBean);
			Scene scene = new Scene(loader.load(), 600, 400);

			stage = new Stage();
			stage.setTitle("Mi Aplicación - Spring Managed");
			stage.setScene(scene);

			initialized = true;

		} catch (IOException e) {
			mostrarError("Error cargando la ventana principal: " + e.getMessage());
		}
	}

	public void muestra() {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::muestra);
			return;
		}
		initializeUI();
		if (stage != null) {
			stage.show();
		}
	}

	@FXML
	private void onSeleccionMetodoEntrega() {
		if (control != null) {
			control.iniciaSeleccionMetodoEntrega();
		} else {
			mostrarError("El ControlPrincipal no está disponible.");
		}
	}

	public Long getPedidoIdActual() {
		return 1L;
	}

	public void mostrarError(String mensaje) {
		Platform.runLater(() -> {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText(null);
			a.setContentText(mensaje);
			if (stage != null) a.initOwner(stage);
			a.showAndWait();
		});
	}

	public void mostrarInfo(String mensaje) {
		Platform.runLater(() -> {
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("Información");
			a.setHeaderText(null);
			a.setContentText(mensaje);
			if (stage != null) a.initOwner(stage);
			a.showAndWait();
		});
	}



	@FXML
	private void onChecklist() {
		if (control != null) {
			control.iniciaChecklistCompra();
		} else {
			mostrarError("No se pudo abrir el checklist (control no inicializado).");
		}
	}

	@FXML
	private void onAbrirPago() {
		if (control != null) {
			control.iniciaPago(); // abrirá la ventana de pago (otro Stage)
		} else {
			mostrarError("El ControlPrincipal no está disponible.");
		}
	}

}