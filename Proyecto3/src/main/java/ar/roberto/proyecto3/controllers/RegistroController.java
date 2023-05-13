package ar.roberto.proyecto3.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.roberto.proyecto3.model.Administrador;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Usuario;
import ar.roberto.proyecto3.service.daos.*;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;

@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<Administrador> administradores = new ArrayList<Administrador>();

	public RegistroController() {
		super();
	}
	/**
	 * Método que maneja una petición GET al controlador. Muestra la vista del
	 * formulario de registro.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Mostrar la vista del formulario de registro
		request.getRequestDispatcher("/views/registro.jsp").forward(request, response);
	}
	/**
	 * Método que maneja una petición POST al controlador. Obtiene los valores del
	 * formulario de registro, valida que las contraseñas coincidan, crea una
	 * instancia de usuario adecuada según el tipo seleccionado y redirige al
	 * usuario a su perfil.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtener los valores del formulario de registro
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String tipoUsuario = request.getParameter("tipo-usuario");

		// Validar que las contraseñas coincidan
		if (!password.equals(confirmPassword)) {
			request.setAttribute("status", "error");
			request.getRequestDispatcher("/views/registro.jsp").forward(request, response);
			return;
		}
		
		// Crear la instancia de usuario adecuada según el tipo seleccionado
		Usuario usuario;
		
		if ("cliente".equals(tipoUsuario)) {
			Cliente cliente = new Cliente(clientes.size() + 1, email, password, 0);
			ClienteDAO clienteDAO = new ClienteDAO();
			try {
				clienteDAO.agregarCliente(cliente);
			} catch (ExceptionRegistro e) {
				e.printStackTrace();
			}
			usuario = cliente;
		} else if ("administrador".equals(tipoUsuario)) {
			Administrador administrador = new Administrador(administradores.size() + 1, email, password, 0);
			AdministradorDAO administradorDAO = new AdministradorDAO();
			try {
				administradorDAO.agregarAdministrador(administrador);
			} catch (ExceptionRegistro e) {
				e.printStackTrace();
			}
			usuario = administrador;

		} else {
			// Si el tipo de usuario es desconocido, mostrar un mensaje de error
			request.setAttribute("status", "error");
			request.getRequestDispatcher("/views/registro.jsp").forward(request, response);
			return;
		}

		// Guardar el email en una variable de sesión
		request.getSession().setAttribute("usuarioEmail", email);
		
		// Crear la sesión y redirigir al usuario a su perfil
		request.getSession().setAttribute("usuario", usuario);
		request.setAttribute("status", "success");
		if (usuario instanceof Cliente) {

			response.sendRedirect(request.getContextPath() + "/cliente");
		} else if (usuario instanceof Administrador) {

			response.sendRedirect(request.getContextPath() + "/administrador");
		}
	}
}
