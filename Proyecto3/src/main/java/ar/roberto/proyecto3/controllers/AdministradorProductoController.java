/**
 La clase AdministradorProductoController extiende de HttpServlet para manejar las solicitudes HTTP y procesar las acciones en el servidor.
 
 El método doGet() maneja las solicitudes GET y el método doPost() maneja las solicitudes POST.
 
 La acción que se va a procesar se obtiene del parámetro "accion" de la solicitud HTTP. Si este parámetro es nulo, se asume que la acción es "index".
 
 La acción "index" muestra la página principal del administrador, que contiene enlaces a otras páginas.
 
 La acción "exit" cierra la sesión del usuario y lo redirige a la página de inicio de sesión.
 
 La acción "productos" muestra la lista de productos.
 
 La acción "eliminarProducto" elimina un producto de la base de datos.
 
 El método getEliminarProducto() recupera el parámetro "codigo" de la solicitud HTTP, que contiene el código del producto a eliminar. Luego, llama al método eliminarProducto() de la clase ProductoDAO para eliminar el producto y redirige al usuario a la página de productos.
 
 El método getProductos() llama al método all() de la clase ProductoDAO para recuperar la lista de todos los productos y luego los envía a la página de productos para mostrarlos.
 
 El método getIndex() redirige al usuario a la página principal del administrador.
 
 El método getExit() cierra la sesión del usuario y lo redirige a la página de inicio de sesión.
 
 El método doPost() procesa las acciones que modifican la base de datos, como crear o editar un producto.
  
 La acción "crearProducto" crea un nuevo objeto Producto con los datos del formulario y lo guarda en la base de datos usando el método crearProducto() de la clase ProductoDAO. Si se guarda correctamente, el usuario es redirigido a la página de productos con un mensaje de éxito. Si hay algún error, se muestra un mensaje de error.
 
 La acción "editarProducto" recupera los datos del formulario y crea un nuevo objeto Producto con ellos. Luego, llama al método editarProducto() de la clase ProductoDAO para actualizar el producto y redirige al usuario a la página de productos. Si hay algún error, se muestra un mensaje de error.
 
 */
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

import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Producto;
import ar.roberto.proyecto3.service.daos.ClienteDAO;
import ar.roberto.proyecto3.service.daos.ProductoDAO;
import ar.roberto.proyecto3.service.exceptions.ExceptionCrearProducto;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarProducto;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarProducto;

@WebServlet("/administradorProductos")
public class AdministradorProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministradorProductoController() {
		super();

	}
	
	private ProductoDAO productoDAO = new ProductoDAO();

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
		case "productos":
			getProductos(request, response);
			break;
		case "eliminarProducto":
			try {
				getEliminarProducto(request, response);
			} catch (IOException e) {

				e.printStackTrace();
			} catch (ExceptionEliminarProducto e) {

				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			var rd = request.getRequestDispatcher("/views/administrador/productos.jsp");
			rd.forward(request, response);
			break;
		}
	}

	private void getEliminarProducto(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ExceptionEliminarProducto, SQLException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		ProductoDAO.eliminarProducto(codigo);
		response.sendRedirect("administradorProductos?accion=productos");

	}

	private void getProductos(HttpServletRequest request, HttpServletResponse response) {
		List<Producto> productos = null;
		try {
			productos = ProductoDAO.all();
			request.setAttribute("productos", productos);
			var rd = request.getRequestDispatcher("/views/administrador/productos.jsp");
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
		case "crearProducto":
			try {
				crearProducto(request, response);
			} catch (ExceptionCrearProducto | IOException | ServletException e1) {

				e1.printStackTrace();
			}
			break;
		case "editarProducto":
			try {
				editarProducto(request, response);
			} catch (ExceptionEditarProducto e) {
				e.printStackTrace();
			}
			break;

		default:
			var session = request.getSession();
			var rd = request.getRequestDispatcher("/views/administrador/productos.jsp");
			rd.forward(request, response);
			break;
		}

	}

	private void editarProducto(HttpServletRequest request, HttpServletResponse response)
			throws ExceptionEditarProducto, NumberFormatException, IOException, ServletException {

		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		double precio = Double.parseDouble(request.getParameter("precio"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		String codigo = request.getParameter("codigo");

		Producto producto = new Producto(Integer.parseInt(codigo), nombre, descripcion, precio, stock);
		ProductoDAO productoDAO = new ProductoDAO();
		productoDAO.editarProducto(producto);

		response.sendRedirect("administradorProductos?accion=productos");

	}

	private void crearProducto(HttpServletRequest request, HttpServletResponse response)
			throws ExceptionCrearProducto, IOException, ServletException {
		// Recuperar los datos del formulario
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		double precio = Double.parseDouble(request.getParameter("precio"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		// Crear un nuevo objeto Producto
		Producto producto = new Producto(nombre, descripcion, precio, stock);

		// Guardar el objeto en la base de datos usando el método DAO correspondiente
		if (ProductoDAO.crearProducto(producto)) {
		    // Si se guardó correctamente, redirigir al usuario a la página de productos
		    String mensaje = "El producto se creó con éxito";
		    request.setAttribute("mensaje", mensaje);
		    request.setAttribute("tipo", "success");
		    response.sendRedirect("administradorProductos?accion=productos");
		} else {
		    // Si hubo un error, mostrar un mensaje de error
		    request.setAttribute("mensaje", "Hubo un error al crear el producto.");
		    request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
