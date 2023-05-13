package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionEliminarCliente extends Exception {
	public ExceptionEliminarCliente(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionEliminarCliente(String mensaje) {
		super(mensaje);
	}
}
