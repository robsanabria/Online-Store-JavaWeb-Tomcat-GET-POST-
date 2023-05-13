package ar.roberto.proyecto3.service.daos;

/**
 * Esta clase implementa la interfaz InterfazAdministradorDAO y proporciona métodos para
 * realizar operaciones de acceso a datos en la tabla Administrador de la base de datos.
 * Contiene métodos para agregar, obtener, editar y eliminar administradores, así como
 * para obtener una lista de todos los administradores en la tabla.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.roberto.proyecto3.factories.ConexionFactory;
import ar.roberto.proyecto3.model.Administrador;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarAdministrador;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;
import ar.roberto.proyecto3.service.interfaces.InterfazAdministradorDAO;

public class AdministradorDAO implements InterfazAdministradorDAO{
	private static final String INSERT_ADMINISTRADOR_SQL = "INSERT INTO Administrador (email, password) VALUES (?, ?)";

	public void agregarAdministrador(Administrador administrador) throws ExceptionRegistro {
		try (Connection conn = ConexionFactory.getConexion()) {
			if (administrador.validarAdminRepetido(conn)) {
				throw new ExceptionRegistro("El administrador ya existe en la base de datos.");
			}

			try (PreparedStatement ps = conn.prepareStatement(INSERT_ADMINISTRADOR_SQL)) {
				ps.setString(1, administrador.getEmail());
				ps.setString(2, administrador.getPassword());

				int filasAfectadas = ps.executeUpdate();

				System.out.println(filasAfectadas + " fila(s) insertada(s) en la tabla Administrador.");
			}
		} catch (SQLException e) {
			throw new ExceptionRegistro("Ocurrió una excepción al agregar un administrador.", e);
		} catch (ExceptionRegistro e) {
			System.err.println(e.getMessage());
		}
	}

	public Administrador obtenerAdministradorPorEmail(String email) {
		String SELECT_ADMINISTRADOR_SQL = "SELECT * FROM Administrador WHERE email = ?";
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement ps = conn.prepareStatement(SELECT_ADMINISTRADOR_SQL)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("id");
					String password = rs.getString("password");
					double saldo = rs.getDouble("saldo");
					return new Administrador(id, email, password, saldo);
				}
			}
		} catch (SQLException e) {
			System.err.println("Ocurrió una excepción al obtener un administrador por email: " + email);
			e.printStackTrace();
		}
		return null;
	}

	public List<Administrador> all() {
		List<Administrador> administradores = new ArrayList<>();
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement stmt = conn.prepareStatement("SELECT id, email, password FROM administrador")) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				Administrador administrador = new Administrador(id, email, password);
				administradores.add(administrador);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return administradores;
	}

	public boolean delete(int id) {
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM administrador WHERE id=?")) {
			stmt.setInt(1, id);
			int rowsDeleted = stmt.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void editarAdministrador(Administrador administrador) throws ExceptionEditarAdministrador {
		try (Connection conn = ConexionFactory.getConexion()) {
		
			String UPDATE_ADMINISTRADOR_SQL = "UPDATE Administrador SET email = ?, password = ? WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(UPDATE_ADMINISTRADOR_SQL)) {
				ps.setString(1, administrador.getEmail());
				ps.setString(2, administrador.getPassword());
				ps.setInt(3, administrador.getId());

				int filasAfectadas = ps.executeUpdate();

				System.out.println(filasAfectadas + " fila(s) actualizada(s) en la tabla Administrador.");
			}
		} catch (SQLException e) {
			throw new ExceptionEditarAdministrador("Ocurrió una excepción al editar un administrador.", e);
		}
	}
}
