/**

Clase de prueba para la clase RegistroController. Utiliza Mockito para simular los objetos HttpServletRequest,
HttpServletResponse, RequestDispatcher y HttpSession. Contiene pruebas unitarias para el método doGet y el método
doPost de RegistroController para diferentes casos: registro de un cliente, registro de un administrador, error de
contraseña incorrecta y error de tipo de usuario desconocido.

*/

package ar.roberto.proyecto3.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import ar.roberto.proyecto3.controllers.RegistroController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class RegistroControllerTest {

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	RequestDispatcher requestDispatcher;

	@Mock
	HttpSession session;

	@Test
	void testDoGet() throws Exception {
		MockitoAnnotations.openMocks(this);
		RegistroController controller = new RegistroController();
		when(request.getRequestDispatcher("/views/registro.jsp")).thenReturn(requestDispatcher);
		controller.doGet(request, response);
		verify(requestDispatcher).forward(request, response);
	}

	@Test
	void testDoPostCliente() throws Exception {
		MockitoAnnotations.openMocks(this);
		RegistroController controller = new RegistroController();
		when(request.getParameter("email")).thenReturn("test@test.com");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("confirmPassword")).thenReturn("test");
		when(request.getParameter("tipo-usuario")).thenReturn("cliente");
		when(request.getSession()).thenReturn(session);
		controller.doPost(request, response);
		verify(session).setAttribute("usuarioEmail", "test@test.com");
		verify(response).sendRedirect(request.getContextPath() + "/cliente");
	}

	@Test
	void testDoPostAdmin() throws Exception {
		MockitoAnnotations.openMocks(this);
		RegistroController controller = new RegistroController();
		when(request.getParameter("email")).thenReturn("test@test.com");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("confirmPassword")).thenReturn("test");
		when(request.getParameter("tipo-usuario")).thenReturn("administrador");
		when(request.getSession()).thenReturn(session);
		controller.doPost(request, response);
		verify(session).setAttribute("usuarioEmail", "test@test.com");
		verify(response).sendRedirect(request.getContextPath() + "/administrador");
	}

	@Test
	void testDoPostPasswordsDoNotMatch() throws Exception {
	    MockitoAnnotations.openMocks(this);
	    RegistroController controller = new RegistroController();
	    when(request.getParameter("email")).thenReturn("test@test.com");
	    when(request.getParameter("password")).thenReturn("test");
	    when(request.getParameter("confirmPassword")).thenReturn("wrongPassword");
	    when(request.getRequestDispatcher("/views/registro.jsp")).thenReturn(requestDispatcher);
	    controller.doPost(request, response);
	    verify(request).setAttribute("status", "error");
	    verify(request.getRequestDispatcher("/views/registro.jsp")).forward(request, response);
	}


	@Test
	void testDoPostUnknownUserType() throws Exception {
		MockitoAnnotations.openMocks(this);
		RegistroController controller = new RegistroController();
		when(request.getParameter("email")).thenReturn("test@test.com");
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("confirmPassword")).thenReturn("test");
		when(request.getParameter("tipo-usuario")).thenReturn("unknown");
		when(request.getRequestDispatcher("/views/registro.jsp")).thenReturn(requestDispatcher);
		controller.doPost(request, response);
		verify(request).setAttribute("status", "error");
		verify(request.getRequestDispatcher("/views/registro.jsp")).forward(request, response);

	}
}
