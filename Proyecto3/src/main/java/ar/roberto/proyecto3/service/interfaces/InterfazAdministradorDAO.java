package ar.roberto.proyecto3.service.interfaces;

import java.util.List;

import ar.roberto.proyecto3.model.Administrador;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarAdministrador;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;

public interface InterfazAdministradorDAO {
	 void agregarAdministrador(Administrador administrador) throws ExceptionRegistro;
	    Administrador obtenerAdministradorPorEmail(String email);
	    List<Administrador> all();
	    boolean delete(int id);
	    void editarAdministrador(Administrador administrador) throws ExceptionEditarAdministrador;
}
