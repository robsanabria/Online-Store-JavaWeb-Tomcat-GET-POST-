package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionCrearCliente extends Exception {
	
	public ExceptionCrearCliente(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionCrearCliente(String mensaje) {
		super(mensaje);
	}
}
