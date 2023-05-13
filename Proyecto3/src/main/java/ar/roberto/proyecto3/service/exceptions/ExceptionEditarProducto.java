package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionEditarProducto extends Exception {
	public ExceptionEditarProducto(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionEditarProducto(String mensaje) {
		super(mensaje);
	}
}
