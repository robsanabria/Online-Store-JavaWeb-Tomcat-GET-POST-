package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionCrearProducto extends Exception {
	public ExceptionCrearProducto(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionCrearProducto(String mensaje) {
		super(mensaje);
	}
}
