package ar.roberto.proyecto3.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ar.roberto.proyecto3.controllers.LoginController;

//La anotación @RunWith(MockitoJUnitRunner.class) especifica que se utilizará la clase MockitoJUnitRunner para ejecutar las pruebas.
@RunWith(MockitoJUnitRunner.class)

public class LoginControllerTest {

	private LoginController loginController;

//    Los objetos HttpServletRequest, HttpServletResponse y HttpSession se han anotado con @Mock
//    para que Mockito cree objetos simulados de estos tipos.
	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

//    El método setUp() se utiliza para crear una instancia del controlador LoginController.
	@Before
	public void setUp() {
		loginController = new LoginController();
	}

//    El método testDoGet() prueba el método doGet() del controlador. Se utiliza Mockito para simular la llamada al método getRequestDispatcher(), 
//    y luego se verifica que se haya llamado al método una vez y que se haya llamado al método forward() del objeto RequestDispatcher.
	@Test
	public void testDoGet() throws Exception {
		// Simular la llamada al método getRequestDispatcher()
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("/views/login.jsp")).thenReturn(dispatcher);

		// Ejecutar el método doGet()
		loginController.doGet(request, response);

		// Verificar que se llame al método getRequestDispatcher()
		verify(request, times(1)).getRequestDispatcher("/views/login.jsp");
		verify(dispatcher, times(1)).forward(request, response);
	}
//	El método testDoPost() prueba el método doPost() del controlador. Se utilizan las simulaciones de Mockito para los métodos getParameter() y getSession(), 
//	y luego se verifica que se hayan llamado los métodos getParameter() y getSession() una vez cada uno.
	@Test
    public void testDoPost() throws Exception {
        // Simular la llamada al método getParameter()
        when(request.getParameter("email")).thenReturn("usuario@example.com");
        when(request.getParameter("password")).thenReturn("contraseña");

        // Simular la llamada al método getSession()
        when(request.getSession()).thenReturn(session);

        // Ejecutar el método doPost()
        loginController.doPost(request, response);

        // Verificar que se llamen los métodos adecuados
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getSession();
    }
}
