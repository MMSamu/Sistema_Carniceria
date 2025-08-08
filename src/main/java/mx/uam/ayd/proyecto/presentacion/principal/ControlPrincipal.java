package mx.uam.ayd.proyecto.presentacion.principal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.presentacion.asignarMetodoEntrega.ControlAsignarMetodoEntrega;

/**
 * Esta clase lleva el flujo de control de la ventana principal
 *
 * @author humbertocervantes
 */
@Component
public class ControlPrincipal {

	private final VentanaPrincipal ventana;
	private final ControlAsignarMetodoEntrega controlAsignarMetodoEntrega;

	@Autowired
	public ControlPrincipal(
			VentanaPrincipal ventana,
			ControlAsignarMetodoEntrega controlAsignarMetodoEntrega) {

		this.ventana = ventana;
		this.controlAsignarMetodoEntrega = controlAsignarMetodoEntrega;
	}

	/**
	 * Método que se ejecuta después de la construcción del bean
	 * y realiza la conexión bidireccional entre el control principal y la ventana principal
	 */
	@PostConstruct
	public void init() {
		ventana.setControlPrincipal(this);
	}

	/**
	 * Inicia el flujo de control de la ventana principal
	 */
	public void inicia() {
		ventana.muestra();
	}

	/**
	 * Método que arranca la historia de usuario "asignar método de entrega"
	 */
	public void asignarMetodoEntrega() {
		controlAsignarMetodoEntrega.inicia();
	}
}
