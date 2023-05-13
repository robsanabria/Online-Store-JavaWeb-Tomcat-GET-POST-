package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionLogin extends Exception {

	public ExceptionLogin(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionLogin(String mensaje) {
		super(mensaje);
	}
}
