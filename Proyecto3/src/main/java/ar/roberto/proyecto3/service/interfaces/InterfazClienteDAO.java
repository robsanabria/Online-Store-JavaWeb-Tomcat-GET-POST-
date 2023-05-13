package ar.roberto.proyecto3.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionLogin;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;

public interface InterfazClienteDAO {

    /**
     * Agrega un nuevo cliente a la base de datos si no existe previamente.
     * 
     * @throws Exception si el cliente ya existe.
     */
    void agregarCliente(Cliente cliente) throws ExceptionRegistro;

    /**
     * Obtiene un cliente de la base de datos por su dirección de correo
     * electrónico.
     * 
     * @return el cliente encontrado o null si no existe.
     * @throws ExceptionLogin 
     */
    Cliente obtenerClientePorEmail(String email) throws SQLException, ExceptionLogin;

    

}
