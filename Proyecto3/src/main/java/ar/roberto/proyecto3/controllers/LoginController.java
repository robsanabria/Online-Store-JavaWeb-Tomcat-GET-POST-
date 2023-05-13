/**
 El controlador LoginController es responsable de manejar la autenticación de los usuarios en la aplicación web. 
 El controlador tiene dos métodos, doGet() y doPost(), para manejar las solicitudes HTTP GET y POST respectivamente. 

 El método doGet() simplemente muestra la vista del formulario de inicio de sesión al usuario. El formulario tiene 
 campos para ingresar el correo electrónico y la contraseña.
 
 El método doPost() maneja la solicitud POST del formulario de inicio de sesión. El método obtiene los valores del 
 formulario, verifica si el correo electrónico pertenece a un cliente o administrador y luego verifica si la 
 contraseña coincide. Si el correo electrónico y la contraseña son correctos, inicia la sesión del usuario y lo 
 redirige a la página correspondiente. Si no, muestra un mensaje de error y vuelve a mostrar el formulario de 
  inicio de sesión.
 */


package ar.roberto.proyecto3.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.roberto.proyecto3.model.Administrador;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Usuario;
import ar.roberto.proyecto3.service.daos.AdministradorDAO;
import ar.roberto.proyecto3.service.daos.ClienteDAO;
import ar.roberto.proyecto3.service.exceptions.ExceptionLogin;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Mostrar la vista del formulario de inicio de sesión
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener los valores del formulario de inicio de sesión
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Verificar si el usuario existe en la tabla de clientes
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = null;
		try {
			cliente = clienteDAO.obtenerClientePorEmail(email);
		} catch (ExceptionLogin e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Si el usuario no existe en la tabla de clientes, verificar si existe en la
		// tabla de administradores
		if (cliente == null) {
			AdministradorDAO administradorDAO = new AdministradorDAO();
			Administrador administrador = administradorDAO.obtenerAdministradorPorEmail(email);

			// Si el usuario no existe en la tabla de administradores, mostrar un mensaje de
			// error y volver a mostrar la página de inicio de sesión
			if (administrador == null) {
				request.setAttribute("status", "error");
				request.getRequestDispatcher("/views/login.jsp").forward(request, response);
				return;
			}

			// Si el usuario existe en la tabla de administradores, verificar si la
			// contraseña coincide
			if (!administrador.getPassword().equals(password)) {
				request.setAttribute("status", "error");
				request.getRequestDispatcher("/views/login.jsp").forward(request, response);
				return;
			}

			// Si la contraseña coincide, iniciar la sesión del usuario y redirigirlo a la
			// página de administrador
			request.getSession().setAttribute("usuario", administrador);

			// Obtener el email del usuario logeado
			String usuarioEmail = administrador.getEmail();

			// Guardar el email del usuario logeado en la sesión para utilizarlo en otras
			// vistas
			request.getSession().setAttribute("usuarioEmail", usuarioEmail);

			response.sendRedirect(request.getContextPath() + "/administrador");
			return;
		}

		// Si el usuario existe en la tabla de clientes, verificar si la contraseña
		// coincide
		if (!cliente.getPassword().equals(password)) {
			request.setAttribute("status", "error");
			request.getRequestDispatcher("/views/login.jsp").forward(request, response);
			return;
		}

		// Si la contraseña coincide, iniciar la sesión del usuario y redirigirlo a la
		// página de cliente
		request.getSession().setAttribute("usuario", cliente);

		// Obtener el email y ID del usuario logeado
		String usuarioEmail = cliente.getEmail();
		int idCliente = cliente.getId(); // Agrega esta línea para obtener el ID del cliente

		// Guardar el email y ID del usuario logeado en la sesión para utilizarlo en otras vistas
		request.getSession().setAttribute("usuarioEmail", usuarioEmail);
		request.getSession().setAttribute("idCliente", idCliente); // Agrega esta línea para guardar el ID del cliente en la sesión
		
		response.sendRedirect(request.getContextPath() + "/cliente");

	}

}
