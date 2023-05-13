package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionEditarAdministrador extends Exception {

	public ExceptionEditarAdministrador(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionEditarAdministrador(String mensaje) {
		super(mensaje);
	}
}
