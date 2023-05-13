/**
 * La clase Usuario es una clase abstracta que sirve como base para otros tipos de usuarios en el sistema.
 * A continuación, se detalla el comentario de cada atributo y método de esta clase:
 *
 * Atributos:
 * - id: Entero que representa el identificador único del usuario en el sistema.
 * - email: Cadena de caracteres que representa el correo electrónico del usuario.
 * - password: Cadena de caracteres que representa la contraseña del usuario.
 * - saldo: Número real que representa el saldo actual del usuario.
 *
 * Métodos:
 * - Usuario: Constructor de la clase que inicializa los atributos id, email, password y saldo.
 * - getId: Devuelve el valor del atributo id.
 * - setId: Establece el valor del atributo id.
 * - getEmail: Devuelve el valor del atributo email.
 * - setEmail: Establece el valor del atributo email.
 * - getPassword: Devuelve el valor del atributo password.
 * - setPassword: Establece el valor del atributo password.
 * - getSaldo: Devuelve el valor del atributo saldo.
 * - setSaldo: Establece el valor del atributo saldo.
 * - validarPassword: Devuelve true si la contraseña recibida como argumento coincide con la contraseña del usuario, false en caso contrario.
 * - validarEmail: Devuelve true si el correo electrónico recibido como argumento coincide con el correo electrónico del usuario, false en caso contrario.
 *
 * 
 */

package ar.roberto.proyecto3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Usuario {
    int id;
    String email;
    String password;
    double saldo;

    public Usuario(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.saldo = 0;
    }
    
    public Usuario(int id, String email, String password, double saldo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
    }
  

	public Usuario(int id2, String email2, double saldo2) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Validaciones
    
    public boolean validarPassword(String password) {
        return this.getPassword().equals(password);
    }

    public boolean validarEmail(String email) {
        return this.getEmail().equals(email);
    }
    
    public boolean validarAdminRepetido(Connection conn) throws SQLException {
        boolean repetido = false;
        String sql = "SELECT COUNT(*) FROM Administrador WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, this.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        repetido = true;
                    }
                }
            }
        }

        return repetido;
    }
    
    public boolean validarClienteRepetido(Connection conn, Cliente cliente) throws SQLException {
        boolean repetido = false;
        String sql = "SELECT COUNT(*) FROM Cliente WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        repetido = true;
                    }
                }
            }
        }

        return repetido;
    }

    

    
    
}


