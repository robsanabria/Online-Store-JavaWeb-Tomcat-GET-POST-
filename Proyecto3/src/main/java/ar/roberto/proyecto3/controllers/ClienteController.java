package ar.roberto.proyecto3.controllers;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.roberto.proyecto3.model.Carrito;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Compra;
import ar.roberto.proyecto3.model.Producto;
import ar.roberto.proyecto3.service.daos.CarritoDAO;
import ar.roberto.proyecto3.service.daos.ClienteDAO;
import ar.roberto.proyecto3.service.daos.CompraDAO;
import ar.roberto.proyecto3.service.daos.ProductoDAO;
import ar.roberto.proyecto3.service.daos.SaldoDAO;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarCliente;

/**
 * 
 * Esta clase es un controlador Servlet para el cliente. Maneja las solicitudes
 * GET y POST para las páginas de cliente.
 */
@WebServlet("/cliente")
public class ClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClienteController() throws SQLException {
		this.pdao = new ProductoDAO();
		this.cdao = new ClienteDAO();
		this.cadao = new CarritoDAO();

	}

	private ProductoDAO pdao;
	private ClienteDAO cdao;
	private CarritoDAO cadao;

	/**
	 * 
	 * Este método maneja las solicitudes GET para la página principal del cliente.
	 * Muestra la vista de la página principal del cliente.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var session = request.getSession();
		var path = request.getParameter("accion");
		path = Optional.ofNullable(path).orElse("index");
		System.out.println("Path: " + path);

		switch (path) {
		case "index":
			getIndex(request, response);
			break;
		case "exit":
			getExit(request, response);
			break;
		case "carrito":
			getCarrito(request, response);
			break;
		case "pedidos":
			getPedidos(request, response);
			break;
		case "saldo":
			getSaldo(request, response);
			break;
		default:

			var rd = request.getRequestDispatcher("/views/cliente/index.jsp");
			rd.forward(request, response);
			break;

		}
	}

	/**
	 * 
	 * Método que obtiene el saldo del cliente actual a partir de su ID, y lo
	 * establece como un atributo en el objeto request para ser procesado por la
	 * vista saldo.jsp. Si el ID del cliente no está presente en la sesión, redirige
	 * al usuario a la página de inicio de sesión.
	 * 
	 * @param request  objeto HttpServletRequest que contiene información de la
	 *                 petición HTTP
	 * @param response objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP
	 * @throws ServletException si ocurre un error de Servlet
	 * @throws IOException      si ocurre un error de entrada/salida
	 */
	private void getSaldo(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			Integer idCliente = (Integer) session.getAttribute("idCliente");
			String email = (String) session.getAttribute("email");

			Cliente cliente = (Cliente) session.getAttribute("cliente");

			if (idCliente != null) {

				// Obtener el saldo del DAO
				double saldo = SaldoDAO.getSaldo(idCliente);
				// Establecer el saldo como un atributo en el objeto request
				request.setAttribute("saldo", saldo);

				// Enviar el objeto request al JSP para su procesamiento
				var rd = request.getRequestDispatcher("/views/cliente/saldo.jsp");
				rd.forward(request, response);

			} else {
				// Si idCliente es nulo, redirige a la página de inicio de sesión
				response.sendRedirect(request.getContextPath() + "/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Obtiene los pedidos realizados por un cliente y los muestra en una página
	 * JSP.
	 * 
	 * Si el cliente no ha iniciado sesión, redirige a la página de inicio de
	 * sesión.
	 * 
	 * @param request  objeto HttpServletRequest que contiene información de la
	 *                 solicitud HTTP
	 * @param response objeto HttpServletResponse que contiene información de la
	 *                 respuesta HTTP
	 * @throws ServletException si ocurre un error en el servlet
	 * @throws IOException      si ocurre un error de entrada/salida
	 */
	private void getPedidos(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Integer idCliente = (Integer) session.getAttribute("idCliente");

			if (idCliente != null) {

				List<Compra> compras = CompraDAO.recuperarCompras(idCliente);

				// Agregar la lista de compras al objeto request
				request.setAttribute("compras", compras);
				// Enviar el objeto request al JSP para su procesamiento
				var rd = request.getRequestDispatcher("/views/cliente/pedidos.jsp");
				rd.forward(request, response);

			} else {
				// Si idCliente es nulo, redirige a la página de inicio de sesión
				response.sendRedirect(request.getContextPath() + "/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Este método obtiene el carrito del cliente y muestra la vista del carrito de
	 * compras.
	 * 
	 * @param request  la solicitud HTTP
	 * @param response la respuesta HTTP
	 */
	private void getCarrito(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Integer idCliente = (Integer) session.getAttribute("idCliente");

			if (idCliente != null) {

				// Obtiene la lista de todos los productos
				List<Producto> productos = ProductoDAO.all();
				request.setAttribute("productos", productos);

				// Obtiene el carrito del cliente
				List<Carrito> carritos = CarritoDAO.obtenerProductosCarritoPorIdCliente(idCliente);
				request.setAttribute("carritos", carritos);
				// Se redirige a la vista del carrito
				var rd = request.getRequestDispatcher("/views/cliente/carrito.jsp");
				rd.forward(request, response);

			} else {
				// Si idCliente es nulo, redirige a la página de inicio de sesión
				response.sendRedirect(request.getContextPath() + "/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Método que redirige al usuario a la página de inicio de sesión y luego
	 * invalida la sesión actual.
	 * 
	 * @param request  objeto HttpServletRequest con la información de la solicitud
	 *                 HTTP
	 * @param response objeto HttpServletResponse con la información de la respuesta
	 *                 HTTP
	 * @throws IOException si ocurre un error al redirigir al usuario
	 */
	private void getExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("login"); // redirige al usuario primero
		HttpSession session = request.getSession(false); // obtiene la sesion actual y en caso que no exista devuelve
															// null
		if (session != null) {
			session.invalidate(); // invalida la sesión después de redirigir al usuario
		}
	}

	/**
	 * Muestra la página principal, donde se listan los productos disponibles. Si el
	 * usuario está autenticado como cliente, también se muestra su carrito de
	 * compras.
	 */
	private void getIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var rd = request.getRequestDispatcher("views/cliente/index.jsp");
		rd.forward(request, response);

	}

	/**
	 * 
	 * Maneja las solicitudes POST realizadas en la aplicación. Elige la acción
	 * correspondiente según el valor del parámetro "accion" recibido y llama al
	 * método adecuado para procesarla. Si el valor de "accion" no se encuentra, se
	 * redirige al usuario a la página del carrito de compras.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP que se enviará al cliente.
	 * @throws ServletException Si se produce un error de servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var path = request.getParameter("accion");
		path = Optional.ofNullable(path).orElse("articulos");
		System.out.println("Path: " + path);
		switch (path) {

		case "agregar":
			postAgregar(request, response);
			break;
		case "eliminar":
			postEliminar(request, response);
			break;
		case "comprar":
			try {
				postComprar(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExceptionEditarCliente e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "sumarSaldo":
			postSumar(request, response);
			break;
		case "transferir":
			try {
				postTransferir(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "volver":
			getVolver(request, response);
			break;

		default:
			var session = request.getSession();
			var rd = request.getRequestDispatcher("/views/cliente/carrito.jsp");
			rd.forward(request, response);
			break;
		}
	}

	private void getVolver(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var session = request.getSession();
		var rd = request.getRequestDispatcher("views/cliente/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * 
	 * Realiza la transferencia de saldo desde el cliente de la sesión hacia otro
	 * cliente seleccionado en el formulario de transferencia.
	 * 
	 * @param request  Objeto HttpServletRequest con la solicitud HTTP del cliente.
	 * @param response Objeto HttpServletResponse con la respuesta HTTP a enviar al
	 *                 cliente.
	 * @throws ServletException Si ocurre un error en el servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 * @throws SQLException     Si ocurre un error en la comunicación con la base de
	 *                          datos.
	 */
	protected void postTransferir(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// Obtener el idClienteOrigen de la sesión
		HttpSession session = request.getSession();
		Integer idClienteOrigen = (Integer) session.getAttribute("idCliente");

		// Obtener el idClienteDestino, el monto y demás parámetros del formulario
		String emailClienteDestino = request.getParameter("destinatario");
		double monto = Double.parseDouble(request.getParameter("monto"));

		// Realizar la transferencia de saldo
		boolean transferenciaExitosa = false;
		try {
			transferenciaExitosa = SaldoDAO.transferirSaldo(idClienteOrigen, emailClienteDestino, monto);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Redirigir al usuario a la página correspondiente según el resultado de la
		// transferencia
		if (transferenciaExitosa) {
			Cliente cliente = ClienteDAO.obtenerClientePorID(idClienteOrigen);
			session.setAttribute("cliente", cliente);
			request.setAttribute("status", "transferenciaOk");
			getSaldo(request, response);
		} else {
			Cliente cliente = ClienteDAO.obtenerClientePorID(idClienteOrigen);
			session.setAttribute("cliente", cliente);
			request.setAttribute("status", "error");
			getSaldo(request, response);
		}
	}

	/**
	 * 
	 * Método para agregar saldo a la cuenta del cliente.
	 * 
	 * @param request  objeto HttpServletRequest con la información de la solicitud
	 *                 HTTP
	 * @param response objeto HttpServletResponse con la información de la respuesta
	 *                 HTTP
	 */
	private void postSumar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			int idCliente = (int) session.getAttribute("idCliente");
			double saldoSumar = Double.parseDouble(request.getParameter("cantidad"));

			// Actualizar saldo en la base de datos
			SaldoDAO.sumarSaldo(idCliente, saldoSumar);

			// Actualizar saldo en la sesión
			Cliente cliente = ClienteDAO.obtenerClientePorID(idCliente);
			session.setAttribute("cliente", cliente);

			// notificacion
			request.setAttribute("status", "success");
			getSaldo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Este método agrega un producto al carrito del cliente y muestra la vista del
	 * carrito actualizado.
	 * 
	 * @param request  la solicitud HTTP
	 * @param response la respuesta HTTP
	 * @return void
	 * @throws IOException      si ocurre un error al redirigir la solicitud HTTP
	 * @throws ServletException si ocurre un error al manejar la solicitud HTTP
	 */
	private void postAgregar(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Obtener los datos del formulario
		List<Producto> productos = null;

		int idCliente = Integer.parseInt(request.getSession().getAttribute("idCliente").toString());
		int idProducto = Integer.parseInt(request.getParameter("codigo"));
		int cantidad = Integer.parseInt(request.getParameter("cantidad"));
		double total = cantidad * Double.parseDouble(request.getParameter("precio"));

		boolean agregado = CarritoDAO.agregarProductoAlCarrito(idCliente, idProducto, cantidad, total);

		// Obtiene el carrito del cliente
		List<Carrito> carritos = CarritoDAO.obtenerProductosCarritoPorIdCliente(idCliente);

		if (agregado) {

			request.setAttribute("status", "success");
			productos = ProductoDAO.all();
			request.setAttribute("productos", productos);
			request.setAttribute("carritos", carritos);

			var rd = request.getRequestDispatcher("/views/cliente/carrito.jsp");
			rd.forward(request, response);

		} else {
			// Mostrar un mensaje de error si no se pudo agregar el producto
			request.setAttribute("mensaje", "No se pudo agregar el producto al carrito.");

		}
	}

	/**
	 * 
	 * Método que se encarga de procesar la solicitud de compra realizada por el
	 * cliente.
	 * 
	 * Obtiene los productos y cantidades del formulario y crea un objeto Compra con
	 * la información recibida.
	 * 
	 * Verifica si el cliente tiene suficiente saldo para realizar la compra y
	 * realiza el descuento correspondiente en caso afirmativo.
	 * 
	 * Guarda la compra en la base de datos y redirige al usuario a la página de
	 * confirmación de compra.
	 * 
	 * Si el saldo es insuficiente, muestra un mensaje de error y redirige al
	 * usuario de vuelta al carrito de compras.
	 * 
	 * @param request  objeto HttpServletRequest con la información de la solicitud
	 *                 HTTP
	 * 
	 * @param response objeto HttpServletResponse con la información de la respuesta
	 *                 HTTP
	 * 
	 * @throws ServletException       si ocurre un error en el servlet
	 * 
	 * @throws IOException            si ocurre un error de entrada/salida
	 * 
	 * @throws SQLException           si ocurre un error con la base de datos
	 * 
	 * @throws ExceptionEditarCliente si ocurre un error al editar el cliente
	 */
	protected void postComprar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ExceptionEditarCliente {

		// Obtener el idCliente de la sesión
		int idCliente = Integer.parseInt(request.getSession().getAttribute("idCliente").toString());

		// Obtener el cliente a partir del idCliente
		Cliente cliente = ClienteDAO.obtenerClientePorID(idCliente);

		// Obtener los valores de los productos y cantidades enviados desde el
		// formulario
		String[] productos = request.getParameterValues("productos[]");
		String[] cantidades = request.getParameterValues("cantidades[]");

		// Crear un objeto Compra con los valores recibidos
		Compra compra = new Compra();
		compra.setCliente(cliente);
		compra.setFecha(LocalDateTime.now());

		Map<Producto, Integer> productosComprados = new HashMap<>();

		// Obtener los productos y sus cantidades de la base de datos
		for (int i = 0; i < productos.length; i++) {
			int codigoProducto = Integer.parseInt(productos[i]);
			int cantidad = Integer.parseInt(cantidades[i]);

			Producto producto = ProductoDAO.obtenerProductoPorCodigo(codigoProducto);
			productosComprados.put(producto, cantidad);
		}

		compra.setProductosComprados(productosComprados);

		// Calcular el precio total de la compra
		double precioTotal = 0;
		for (Map.Entry<Producto, Integer> entry : productosComprados.entrySet()) {
			Producto producto = entry.getKey();
			int cantidad = entry.getValue();
			precioTotal += producto.getPrecio() * cantidad;
		}

		compra.setPrecioTotal(precioTotal);
		compra.setEntregado(false);

		// Comprobar que el cliente tiene suficiente saldo para realizar la compra
		if (cliente.getSaldo() >= precioTotal) {
			// Descontar el precio total de la compra del saldo del cliente
			cliente.setSaldo(cliente.getSaldo() - precioTotal);
			// Actualizar el cliente en la base de datos
			ClienteDAO.actualizarCliente(cliente);
			// Guardar la compra en la base de datos
			int idCompra = CompraDAO.guardarCompra(compra);
			// Eliminar todos los productos del carrito del cliente
			CarritoDAO.eliminarProductosDelCarrito(idCliente);
			// Redirigir al usuario a la página de confirmación de compra
			RequestDispatcher rd = request.getRequestDispatcher("/views/cliente/compraRealizada.jsp");
			rd.forward(request, response);
			// Reproducir sonido de éxito
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("var audio = new Audio('webapp/sounds/success.mp3');");
			out.println("audio.play();");
			out.println("</script>");
		} else {
			// Obtener los datos actualizados del carrito y mostrarlos en el JSP
			List<Carrito> carritos = CarritoDAO.obtenerProductosCarritoPorIdCliente(idCliente);
			List<Producto> productosDisponibles = ProductoDAO.all();

			// Comprobar el saldo del cliente
			double saldoCliente = ClienteDAO.obtenerSaldoPorId(idCliente);
			double totalCarrito = CarritoDAO.obtenerTotalCarritoPorIdCliente(idCliente);
			if (saldoCliente < totalCarrito) {
				request.setAttribute("saldoInsuficiente", true);
			}

			request.setAttribute("carritos", carritos);
			request.setAttribute("productos", productosDisponibles);

			RequestDispatcher rd = request.getRequestDispatcher("/views/cliente/carrito.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * Procesa una petición POST para eliminar un artículo del carrito de compras de
	 * un cliente. Obtiene el id del producto a eliminar de la solicitud y lo
	 * elimina del carrito de compras del cliente actual. Si el cliente no tiene
	 * suficiente saldo para la compra, muestra una advertencia y actualiza la
	 * información del carrito. Redirige al usuario de vuelta a la página de carrito
	 * después de la eliminación.
	 * 
	 * @param request  la solicitud HTTP que contiene el id del producto a eliminar
	 * @param response la respuesta HTTP que se enviará al navegador
	 * @throws ServletException si ocurre un error al manejar la solicitud
	 * @throws IOException      si ocurre un error de entrada/salida al manejar la
	 *                          solicitud
	 * @throws SQLException     si ocurre un error al interactuar con la base de
	 *                          datos
	 */
	private void postEliminar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int idCliente = (int) session.getAttribute("idCliente");

		try {
			int idProducto = Integer.parseInt(request.getParameter("idProducto"));

			boolean eliminado = CarritoDAO.eliminarProductoDelCarrito(idCliente, idProducto);

			if (eliminado) {
				request.setAttribute("status", "arteliminadook");
			} else {
				request.setAttribute("mensaje", "Error al eliminar el producto del carrito");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("mensaje", "Error: el ID del producto no es válido");
		}

		// Obtener los datos actualizados del carrito y mostrarlos en el JSP
		List<Carrito> carritos = CarritoDAO.obtenerProductosCarritoPorIdCliente(idCliente);
		List<Producto> productos = ProductoDAO.all();

		request.setAttribute("carritos", carritos);
		request.setAttribute("productos", productos);

		RequestDispatcher rd = request.getRequestDispatcher("/views/cliente/carrito.jsp");
		rd.forward(request, response);

	}
}

//TODO:

//1) Agregar el .gitignore para java
//2) Desglozar JSON a objetos. ?? quizas..
//3) Intencion de compra - classs
//4) Documentar
//5) Modulo Trasnferencias / Saldo
//6) Modulo Pedidos admin