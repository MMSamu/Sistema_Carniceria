	package mx.uam.ayd.proyecto.presentacion.principal;

	import jakarta.annotation.PostConstruct;
	import mx.uam.ayd.proyecto.negocio.CheckoutService;
	import mx.uam.ayd.proyecto.negocio.PedidoService;
	import mx.uam.ayd.proyecto.negocio.modelo.ProductoPedido;
	import mx.uam.ayd.proyecto.presentacion.checklist.VentanaChecklist;
	import mx.uam.ayd.proyecto.presentacion.seleccionMetodoEntrega.ControlMetodoEntrega;
	import mx.uam.ayd.proyecto.presentacion.seleccionMetodoEntrega.VentanaMetodoEntrega;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Component;

	import java.io.IOException;
	import java.math.BigDecimal;
	import java.util.List;
	import java.util.Optional;

	/**
	 * Lleva el flujo de control de la ventana principal.
	 * Orquesta HU-03: abrir modal y asignar método de entrega al pedido.
	 */
	@Component
	public class ControlPrincipal
	{

		private final VentanaPrincipal ventana;
		private final PedidoService pedidoService; // servicio de negocio
private  final CheckoutService checkoutService; // servicio de negocio para checklist
		@Autowired
		public ControlPrincipal(VentanaPrincipal ventana,
                                PedidoService pedidoService, CheckoutService checkoutService) {
			this.ventana = ventana;
			this.pedidoService = pedidoService;
            this.checkoutService = checkoutService;
        }

		/** Conexión bidireccional ventana <-> control */
		@PostConstruct
		public void init() {

			ventana.setControlPrincipal(this); //
		}

		/** Arranca la UI principal */
		public void inicia() {
			ventana.muestra();
		}

		/**
		 * HU-03: Selección de método de entrega
		 * Lo llama la VentanaPrincipal al pulsar el botón.
		 */
		public void iniciaSeleccionMetodoEntrega() {
			// 1) Obtener el pedido actual (ajusta esto a tu selección real)
			Long idPedidoActual = ventana.getPedidoIdActual();
			if (idPedidoActual == null) {
				ventana.mostrarError("Selecciona primero un pedido válido.");
				return;
			}

			// 2) Traer catálogo de métodos desde negocio
			List<String> metodos = pedidoService.listarMetodosEntrega();

			// 3) Crear controlador y vista del modal (HU-03)
			ControlMetodoEntrega ctrl = new ControlMetodoEntrega();
			VentanaMetodoEntrega vista = new VentanaMetodoEntrega(ctrl);

			try {
				// 4) Mostrar modal y, si el usuario confirma, asignar método
				vista.mostrar(ventana.getStage(), metodos)
						.ifPresent(metodoElegido -> {
							try {
								pedidoService.asignarMetodoEntrega(idPedidoActual, metodoElegido);
								ventana.mostrarInfo("Método asignado: " + metodoElegido);
								// TODO: refrescar tabla/lista de pedidos si aplica
							} catch (IllegalStateException ex) {
								ventana.mostrarError("El pedido ya tenía método de entrega.");
							} catch (Exception ex) {
								ventana.mostrarError("No fue posible asignar el método: " + ex.getMessage());
							}
						});

			} catch (IOException e) {
				ventana.mostrarError("No se pudo abrir la ventana de selección (" + e.getMessage() + ")");
			}
		}
		public void iniciaChecklistCompra() {
			try {
				Long idPedidoActual = ventana.getPedidoIdActual();
				if (idPedidoActual == null) {
					ventana.mostrarError("Selecciona primero un pedido válido.");
					return;
				}

				List<ProductoPedido> items =
						checkoutService.obtenerProductosDelPedido(idPedidoActual);

				String tipoEntrega = checkoutService.obtenerTipoEntregaDelPedido(idPedidoActual);
				BigDecimal subtotal = checkoutService.calcularSubtotal(items);
				BigDecimal envio    = checkoutService.calcularEnvio(tipoEntrega);
				BigDecimal total    = checkoutService.calcularTotal(subtotal, envio);

				VentanaChecklist ventanaChecklist = new VentanaChecklist();
				Optional<VentanaChecklist.Resultado> res =
						ventanaChecklist.mostrar(ventana.getStage(), items, tipoEntrega, subtotal, envio, total);

				if (res.isPresent()) {
					if (res.get() == VentanaChecklist.Resultado.CONFIRMAR) {
						ventana.mostrarInfo("Checklist confirmado. Procede al pago.");
					} else if (res.get() == VentanaChecklist.Resultado.EDITAR) {
						ventana.mostrarInfo("Regresando al carrito para editar.");
					}
				}
			} catch (Exception e) {
				ventana.mostrarError("No se pudo abrir el checklist (" + e.getMessage() + ")");
			}
		}
	}