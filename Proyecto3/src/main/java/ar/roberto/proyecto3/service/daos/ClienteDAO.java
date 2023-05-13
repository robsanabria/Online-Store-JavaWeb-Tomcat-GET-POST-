package ar.roberto.proyecto3.service.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import ar.roberto.proyecto3.factories.ConexionFactory;
import ar.roberto.proyecto3.model.Carrito;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Compra;
import ar.roberto.proyecto3.model.Producto;
import ar.roberto.proyecto3.service.exceptions.ExceptionCrearCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionEditarCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarCliente;
import ar.roberto.proyecto3.service.exceptions.ExceptionLogin;
import ar.roberto.proyecto3.service.exceptions.ExceptionRegistro;
import ar.roberto.proyecto3.service.interfaces.InterfazClienteDAO;

public class ClienteDAO implements InterfazClienteDAO {
	private static final String INSERT_CLIENTE_SQL = "INSERT INTO Cliente (email, password) VALUES (?, ?)";

	/**
	 * Agrega un nuevo cliente a la base de datos si no existe previamente.
	 * 
	 * @throws Exception si el cliente ya existe.
	 */
	public void agregarCliente(Cliente cliente) throws ExceptionRegistro {

		try (Connection conn = ConexionFactory.getConexion()) {
			if (cliente.validarClienteRepetido(conn, cliente)) {
				throw new ExceptionRegistro("El cliente ya existe en la base de datos.");
			}

			try (PreparedStatement ps = conn.prepareStatement(INSERT_CLIENTE_SQL)) {
				ps.setString(1, cliente.getEmail());
				ps.setString(2, cliente.getPassword());

				int filasAfectadas = ps.executeUpdate();

				if (filasAfectadas == 0) {
					throw new ExceptionRegistro("No se pudo agregar el cliente.");
				}

				System.out.println(filasAfectadas + " fila(s) insertada(s) en la tabla Cliente.");
			}
		} catch (SQLException e) {
			throw new ExceptionRegistro("Ocurrió una excepción al agregar un cliente.", e);
		}
	}

	/**
	 * Obtiene un cliente de la base de datos por su identificador único (ID).
	 * 
	 * @return el cliente encontrado o null si no existe.
	 */
	public static Cliente obtenerClientePorID(int id) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("SELECT * FROM Cliente WHERE id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");
				double saldo = rs.getDouble("saldo");
				cliente = new Cliente(id, email, password, saldo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Ocurrió una excepción al recuperar el cliente por ID.", e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return cliente;
	}

	/**
	 * 
	 * Obtiene un cliente de la base de datos por su dirección de correo
	 * electrónico.
	 * 
	 * @return el cliente encontrado o null si no existe.
	 */
	public Cliente obtenerClientePorEmail(String email) throws ExceptionLogin, SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("SELECT * FROM Cliente WHERE email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String password = rs.getString("password");
				double saldo = rs.getDouble("saldo");
				cliente = new Cliente(id, email, password, saldo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionLogin("Ocurrió una excepción al recuperar el cliente.", e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return cliente;
	}

	/**
	 * 
	 * Devuelve una lista con todos los clientes almacenados en la base de datos.
	 * 
	 * @return la lista de clientes.
	 */
	public static List<Cliente> obtenerTodosLosClientes() throws SQLException {

		List<Cliente> clientes = new ArrayList<>();
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cliente");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				double saldo = rs.getDouble("saldo");
				Cliente cliente = new Cliente(id, email, password, saldo);
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Ocurrió una excepción al obtener todos los clientes.", e);
		}
		return clientes;
	}

	/**
	 * 
	 * Elimina un cliente de la base de datos a partir de su ID.
	 * 
	 * @param id el identificador único del cliente a eliminar.
	 */
	public void eliminarCliente(int id) throws ExceptionEliminarCliente {

		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Cliente WHERE id = ?")) {
			ps.setInt(1, id);
			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas == 0) {
				throw new ExceptionEliminarCliente("No se pudo eliminar el cliente con ID " + id);
			}
			System.out.println(filasAfectadas + " fila(s) eliminada(s) de la tabla Cliente.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionEliminarCliente("Ocurrió una excepción al eliminar el cliente con ID " + id, e);
		}
	}

	/**
	 * Este método se encarga de editar un cliente existente en la base de datos,
	 * modificando su email, password y saldo. La edición se realiza mediante una
	 * sentencia SQL UPDATE. Si ocurre un error durante la edición, se lanzará una
	 * excepción de tipo ExceptionEditarCliente.
	 * 
	 * @param cliente el objeto Cliente que se desea editar en la base de datos
	 * @throws ExceptionEditarCliente si ocurre un error durante la edición del
	 *                                cliente
	 */
	public void editarCliente(Cliente cliente) throws ExceptionEditarCliente {

		String sql = "UPDATE Cliente SET email = ?, password = ?, saldo = ? WHERE id = ?";
		try (Connection conn = ConexionFactory.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, cliente.getEmail());
			pstmt.setString(2, cliente.getPassword());
			pstmt.setDouble(3, cliente.getSaldo());
			pstmt.setInt(4, cliente.getId());
			pstmt.executeUpdate();
			System.out.println("Cliente editado con éxito");
		} catch (SQLException e) {
			System.out.println("Error al editar el cliente: " + e.getMessage());
			throw new ExceptionEditarCliente("Error al editar el cliente", e);
		}
	}

	/**
	 * Este método permite crear un nuevo cliente en la base de datos, insertando su
	 * email, password y saldo mediante una sentencia SQL INSERT. Si ocurre un error
	 * durante la creación, se lanzará una excepción de tipo ExceptionCrearCliente.
	 * 
	 * @param email    el email del nuevo cliente
	 * @param password el password del nuevo cliente
	 * @param saldo    el saldo inicial del nuevo cliente
	 * @throws ExceptionCrearCliente si ocurre un error durante la creación del
	 *                               cliente
	 */
	public static void crearCliente(String email, String password, double saldo) throws ExceptionCrearCliente {
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO Cliente (email, password, saldo) VALUES (?, ?, ?)")) {
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setDouble(3, saldo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionCrearCliente("Ocurrió una excepción al crear un nuevo cliente.", e);
		}
	}

	/**
	 * Este método permite actualizar los datos de un cliente existente en la base
	 * de datos, mediante una sentencia SQL UPDATE que modifica su email, password y
	 * saldo. Si ocurre un error durante la actualización, se lanzará una excepción
	 * de tipo ExceptionEditarCliente.
	 * 
	 * @param cliente el objeto Cliente con los nuevos datos que se desean
	 *                actualizar en la base de datos
	 * @throws ExceptionEditarCliente si ocurre un error durante la actualización
	 *                                del cliente
	 */
	public static void actualizarCliente(Cliente cliente) throws ExceptionEditarCliente {
		try (Connection conn = ConexionFactory.getConexion()) {

			try (PreparedStatement ps = conn
					.prepareStatement("UPDATE Cliente SET email=?, password=?, saldo=? WHERE id=?")) {
				ps.setString(1, cliente.getEmail());
				ps.setString(2, cliente.getPassword());
				ps.setDouble(3, cliente.getSaldo());
				ps.setInt(4, cliente.getId());

				int filasAfectadas = ps.executeUpdate();

				if (filasAfectadas == 0) {
					throw new ExceptionEditarCliente("No se pudo actualizar el cliente.");
				}

				System.out.println(filasAfectadas + " fila(s) actualizada(s) en la tabla Cliente.");
			}
		} catch (SQLException e) {
			throw new ExceptionEditarCliente("Ocurrió una excepción al actualizar el cliente.", e);
		}
	}

	/**
	 * Este método permite obtener el saldo de un cliente a partir de su
	 * identificador único (ID), mediante una sentencia SQL SELECT que recupera el
	 * saldo de la tabla Cliente. Si no se encuentra un cliente con el ID
	 * especificado, se lanzará una excepción de tipo SQLException.
	 * 
	 * @param id el identificador único (ID) del cliente del que se desea obtener el
	 *           saldo
	 * @return el saldo del cliente
	 * @throws SQLException si ocurre un error durante la obtención del saldo del
	 *                      cliente
	 */
	public static double obtenerSaldoPorId(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double saldo = -1;

		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("SELECT saldo FROM Cliente WHERE id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				saldo = rs.getDouble("saldo");
			} else {
				throw new SQLException("No se encontró el cliente con ID " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Ocurrió una excepción al obtener el saldo del cliente por ID.", e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return saldo;
	}
	
	public static List<Cliente> listarClientesConSaldo() throws SQLException {
	    List<Cliente> clientes = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = ConexionFactory.getConexion();
	        String sql = "SELECT id, email, password, saldo FROM cliente WHERE saldo IS NOT NULL";
	        stmt = conn.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String email = rs.getString("email");
	            String password = rs.getString("password");
	            double saldo = rs.getDouble("saldo");
	            Cliente cliente = new Cliente(id, email, password, saldo);
	            clientes.add(cliente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            rs.close();
	        }
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	    }

	    return clientes;
	}
	
	public static void actualizarSaldoCliente(Cliente cliente) throws SQLException {
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = ConexionFactory.getConexion();
	        String sql = "UPDATE cliente SET saldo = ? WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setDouble(1, cliente.getSaldo());
	        stmt.setInt(2, cliente.getId());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}




}
