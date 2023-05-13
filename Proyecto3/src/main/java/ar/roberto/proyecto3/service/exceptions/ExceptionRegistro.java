package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionRegistro extends Exception {

	public ExceptionRegistro(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionRegistro(String mensaje) {
		super(mensaje);
	}
}
