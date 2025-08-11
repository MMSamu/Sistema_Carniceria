package mx.uam.ayd.proyecto.presentacion.principal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * Ventana principal usando JavaFX con FXML.
 * NOTA: No usamos fx:controller en el FXML; inyectamos ESTE bean como controller
 * con loader.setController(this).
 */
@Component
public class VentanaPrincipal {

	private Stage stage;
	private ControlPrincipal control;
	private boolean initialized = false;

	/** La inyecta ControlPrincipal en @PostConstruct */
	public void setControlPrincipal(ControlPrincipal control) {
		this.control = control;
	}

	/** Crea y carga la UI (solo una vez) */
	private void initializeUI() {
		if (initialized) return;

		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}

		try {
			// Carga FXML
			URL fxml = getClass().getResource("/fxml/ventana-principal.fxml");
			if (fxml == null) {
				mostrarError("No se encontró el FXML: /fxml/ventana-principal.fxml");
				return;
			}

			FXMLLoader loader = new FXMLLoader(fxml);
			loader.setController(this); // <-- usamos este bean Spring como controller
			Scene scene = new Scene(loader.load(), 600, 400);

			stage = new Stage();
			stage.setTitle("Mi Aplicación");
			stage.setScene(scene);

			initialized = true;

		} catch (IOException e) {
			mostrarError("Error cargando la ventana principal: " + e.getMessage());
		}
	}

	/** Muestra la ventana */
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

	// ==========================
	// Handlers definidos en FXML
	// ==========================

	/**
	 * Se enlaza en el FXML con: onAction="#onSeleccionMetodoEntrega"
	 */
	@FXML
	private void onSeleccionMetodoEntrega() {
		if (control != null) {
			control.iniciaSeleccionMetodoEntrega(); // <-- método en ControlPrincipal
		} else {
			mostrarError("El ControlPrincipal no está disponible.");
		}
	}

	// =========
	// Helpers
	// =========

	/** De momento mock: conecta esto a tu selección real de pedido en la UI */
	public Long getPedidoIdActual() {
		return 1L; // TODO: reemplazar por el id del pedido seleccionado en tu UI
	}

	public Stage getStage() {
		return stage;
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
}