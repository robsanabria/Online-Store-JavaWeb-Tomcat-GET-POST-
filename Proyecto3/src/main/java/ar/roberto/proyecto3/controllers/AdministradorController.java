/**
La clase AdministradorController es un controlador que maneja las solicitudes y respuestas HTTP relacionadas con la administración de clientes.
El controlador utiliza la anotación @WebServlet para indicar que responde a solicitudes HTTP y especifica el mapeo de URL /administrador.
El controlador utiliza la clase ClienteDAO para interactuar con la base de datos y realizar operaciones CRUD (crear, leer, actualizar y eliminar) en los registros de clientes.
El controlador define los métodos doGet y doPost para manejar solicitudes GET y POST, respectivamente.
En el método doGet, el controlador verifica el parámetro accion y ejecuta el método correspondiente para procesar la solicitud.
El método getIndex muestra la página principal del panel de administración.
El método getUsuarios obtiene todos los clientes de la base de datos y los muestra en la página de usuarios.
El método getEliminarCliente elimina un cliente de la base de datos y redirige al usuario de vuelta a la página de usuarios.
El método getExit invalida la sesión actual y redirige al usuario a la página de inicio de sesión.
En el método doPost, el controlador verifica el parámetro accion y ejecuta el método correspondiente para procesar la solicitud.
El método postCrearCliente crea un nuevo cliente en la base de datos y redirige al usuario de vuelta a la página de usuarios.
El método postEditarCliente actualiza un cliente existente en la base de datos y redirige al usuario de vuelta a la página de usuarios.
Tambien se maneja la administración de Administradores.
*/


//
package ar.roberto.proyecto3.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.roberto.proyecto3.model.Administrador;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Compra;
import ar.roberto.proyecto3.model.CompraProducto;
import ar.roberto.proyecto3.service.daos.AdministradorDAO;
import ar.roberto.proyecto3.service.daos.ClienteDAO;
import ar.roberto.proyecto3.service.daos.CompraDAO;
import ar.roberto.proyecto3.service.daos.CompraProductoDAO;
import ar.roberto.proyecto3.service.exceptions.ExceptionCrearCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarAdministrador;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;

@WebServlet("/administrador")
public class AdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministradorController() {
		super();

	}
	
	//Preguntar por email academico.!!

	private ClienteDAO clienteDAO = new ClienteDAO();
	private AdministradorDAO administradorDAO = new AdministradorDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		var path = request.getParameter("accion");
		path = Optional.ofNullable(path).orElse("index");
		System.out.println("Path: " + path);

		switch (path) {
		case "exit":
			getExit(request, response);
			break;
		case "index":
			getIndex(request, response);
			break;
		case "usuarios":
			try {
				getUsuarios(request, response);
			} catch (ServletException | IOException | SQLException e) {

				e.printStackTrace();
			}
			break;
		case "eliminarCliente":
			getEliminarCliente(request, response);
			break;
		case "administradores":
			getAdministradores(request, response);
			break;
		case "eliminarAdministrador":
			getEliminarAdministrador(request, response);
			break;
		case "volver":
			getVolver(request, response);
			break;
		case "saldos":
			try {
				getSaldos(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "ventas":
			getVentas(request, response);
			break;
		case "ventasporProducto":
			getVentasPorProducto(request,response);
			break;

		default:

			var rd = request.getRequestDispatcher("/views/administrador/index.jsp");
			rd.forward(request, response);
			break;
		}

	}

	private void getVentasPorProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompraProducto> comprasProducto = CompraProductoDAO.obtenerComprasProductos();
		request.setAttribute("comprasProducto", comprasProducto);
		var rd = request.getRequestDispatcher("/views/administrador/ventasunitarias.jsp");
		rd.forward(request, response);
		
	}

	private void getVentas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Compra> compras = CompraDAO.obtenerCompras();
		request.setAttribute("compras", compras);
		var rd = request.getRequestDispatcher("/views/administrador/ventas.jsp");
		rd.forward(request, response);

	}

	private void getSaldos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		List<Cliente> clientes = ClienteDAO.listarClientesConSaldo();
		request.setAttribute("clientes", clientes);
		var rd = request.getRequestDispatcher("/views/administrador/saldos.jsp");
		rd.forward(request, response);

	}

	private void getVolver(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var session = request.getSession();
		var rd = request.getRequestDispatcher("views/administrador/index.jsp");
		rd.forward(request, response);
	}

	private void getEliminarAdministrador(HttpServletRequest request, HttpServletResponse response)
			throws IOException, NumberFormatException {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean eliminado = administradorDAO.delete(id);
		if (eliminado) {
			// redirigir a la página de administradores
			response.sendRedirect("administrador?accion=administradores");
		} else {
			// mostrar un mensaje de error
			// o redirigir a una página de error
		}
	}

	private void getAdministradores(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Administrador> administradores = administradorDAO.all();
		request.setAttribute("administradores", administradores);
		var rd = request.getRequestDispatcher("/views/administrador/administradores.jsp");
		rd.forward(request, response);

	}

	private void getEliminarCliente(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			clienteDAO.eliminarCliente(id);
			response.sendRedirect("administrador?accion=usuarios");
		} catch (ExceptionEliminarCliente e) {
			e.printStackTrace();

		}
	}

	private void getUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Cliente> usuarios = null;
		try {
			usuarios = ClienteDAO.obtenerTodosLosClientes();
			request.setAttribute("usuarios", usuarios);
			var rd = request.getRequestDispatcher("/views/administrador/usuarios.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var rd = request.getRequestDispatcher("views/administrador/index.jsp");
		rd.forward(request, response);

	}

	private void getExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("login"); // redirige al usuario primero
		HttpSession session = request.getSession(false); // obtiene la sesion actual y en caso que no exista devuelve
															// null
		if (session != null) {
			session.invalidate(); // invalida la sesión después de redirigir al usuario
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var path = request.getParameter("accion");
		path = Optional.ofNullable(path).orElse("index");
		System.out.println("Path: " + path);
		switch (path) {
		case "crearCliente":
			postCrearCliente(request, response);
			break;
		case "editarCliente":
			postEditarCliente(request, response);
			break;
		case "editarAdministrador":
			try {
				postEditarAdministrador(request, response);
			} catch (IOException | ExceptionRegistro e) {

				e.printStackTrace();
			} catch (ExceptionEditarAdministrador e) {

				e.printStackTrace();
			}
			break;
		case "editarSaldo":
			try {
				postEditarSaldo(request, response);
			} catch (ServletException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "actualizarEstadoEntregado":
			postActualizarEstadoEntregado(request, response);
			break;

		default:
			var session = request.getSession();
			var rd = request.getRequestDispatcher("views/administrador/index.jsp");
			rd.forward(request, response);
			break;
		}
	}

	private void postActualizarEstadoEntregado(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("estado: " + request.getParameter("estadoEntregado"));
		
		int idCompra = Integer.parseInt(request.getParameter("idCompra"));
		boolean entregado = false;
		if (request.getParameter("estadoEntregado").equals("on")) {
			entregado = request.getParameter("estadoEntregado").equals("on") ? true : false;
		}
		if (request.getParameter("estadoEntregado").equals("off")) {
			entregado = request.getParameter("estadoEntregado").equals("off") ? false : true;
		}
		CompraDAO.actualizarEstadoEntregado(idCompra, entregado);
		List<Compra> compras = CompraDAO.obtenerCompras();
		request.setAttribute("compras", compras);
		var rd = request.getRequestDispatcher("/views/administrador/ventas.jsp");
		rd.forward(request, response);
	}

	private void postEditarSaldo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, SQLException {
		int idCliente = Integer.parseInt(request.getParameter("idCliente"));
		double nuevoSaldo = Double.parseDouble(request.getParameter("nuevoSaldo"));

		Cliente cliente = new Cliente(idCliente, "", "", nuevoSaldo);

		try {
			ClienteDAO.actualizarSaldoCliente(cliente);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// redirigir a la página de saldo
		try {
			List<Cliente> clientes = ClienteDAO.listarClientesConSaldo();
			request.setAttribute("clientes", clientes);
			request.setAttribute("status", "success");
			var rd = request.getRequestDispatcher("/views/administrador/saldos.jsp");
			rd.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void postEditarAdministrador(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ExceptionRegistro, ExceptionEditarAdministrador {
		int id = Integer.parseInt(request.getParameter("id"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Administrador administrador = new Administrador(id, email, password);
		AdministradorDAO administradorDAO = new AdministradorDAO();

		administradorDAO.editarAdministrador(administrador);
		response.sendRedirect("administrador?accion=administradores");
	}

	private void postEditarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		double saldo = Double.parseDouble(request.getParameter("saldo"));

		Cliente cliente = new Cliente(id, email, password, saldo);

		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.editarCliente(cliente);
			response.sendRedirect("administrador?accion=usuarios");
			System.out.println("Cliente editado correctamente");
		} catch (ExceptionEditarCliente e) {
			System.out.println("Error al editar el cliente: " + e.getMessage());
			request.setAttribute("mensaje", "Error al editar el cliente: " + e.getMessage());
			return;
		}
	}

	private void postCrearCliente(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		double saldo = Double.parseDouble(request.getParameter("saldo"));
		try {
			ClienteDAO.crearCliente(email, password, saldo);
			response.sendRedirect("administrador?accion=usuarios");
		} catch (ExceptionCrearCliente e) {
			// manejar la excepción
			request.setAttribute("mensaje", "No se pudo crear el cliente. Por favor verifique los datos ingresados.");
		}
	}

}
