package ar.roberto.proyecto3.service.exceptions;

import java.sql.SQLException;

public class ExceptionEditarCliente extends Exception{
	
	public ExceptionEditarCliente(String mensaje, SQLException causa) {
		super(mensaje, causa);
	}

	public ExceptionEditarCliente(String mensaje) {
		super(mensaje);
	}
}
