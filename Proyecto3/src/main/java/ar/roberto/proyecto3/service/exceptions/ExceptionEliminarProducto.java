package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionEliminarProducto extends Exception {
	public ExceptionEliminarProducto(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionEliminarProducto(String mensaje) {
		super(mensaje);
	}
}
