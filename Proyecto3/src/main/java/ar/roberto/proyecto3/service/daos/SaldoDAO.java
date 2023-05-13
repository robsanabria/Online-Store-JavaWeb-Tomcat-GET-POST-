package ar.roberto.proyecto3.service.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.roberto.proyecto3.factories.ConexionFactory;

public class SaldoDAO {

	public static double getSaldo(int idCliente) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double saldo = 0;

		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("SELECT saldo FROM cliente WHERE id = ?");
			stmt.setInt(1, idCliente);
			rs = stmt.executeQuery();

			if (rs.next()) {
				saldo = rs.getDouble("saldo");
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

		return saldo;
	}

	public static void sumarSaldo(int idCliente, double monto) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("UPDATE cliente SET saldo = saldo + ? WHERE id = ?");
			stmt.setDouble(1, monto);
			stmt.setInt(2, idCliente);
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

	public static boolean transferirSaldo(int idClienteOrigen, String emailClienteDestino, double monto)
			throws SQLException {
		Connection conn = null;
		PreparedStatement stmtOrigen = null;
		PreparedStatement stmtDestino = null;
		ResultSet rs = null;

		try {
			conn = ConexionFactory.getConexion();
			conn.setAutoCommit(false); // deshabilitar la confirmación automática de la transacción

			// Obtener el saldo actual del cliente origen
			stmtOrigen = conn.prepareStatement("SELECT saldo FROM cliente WHERE id = ?");
			stmtOrigen.setInt(1, idClienteOrigen);
			rs = stmtOrigen.executeQuery();

			double saldoOrigen = 0;
			if (rs.next()) {
				saldoOrigen = rs.getDouble("saldo");
			} else {
				return false; // el cliente origen no existe
			}

			rs.close();
			stmtOrigen.close();

			// Verificar si el cliente origen tiene suficiente saldo para la transferencia
			if (saldoOrigen < monto) {
				return false; // el cliente origen no tiene suficiente saldo
			}

			// Buscar el id del cliente destino a partir del correo electrónico
			int idClienteDestino = -1;
			stmtDestino = conn.prepareStatement("SELECT id FROM cliente WHERE email = ?");
			stmtDestino.setString(1, emailClienteDestino);
			rs = stmtDestino.executeQuery();

			if (rs.next()) {
				idClienteDestino = rs.getInt("id");
			} else {
				return false; // el cliente destino no existe
			}

			rs.close();
			stmtDestino.close();

			// Actualizar el saldo del cliente origen
			stmtOrigen = conn.prepareStatement("UPDATE cliente SET saldo = saldo - ? WHERE id = ?");
			stmtOrigen.setDouble(1, monto);
			stmtOrigen.setInt(2, idClienteOrigen);
			int count = stmtOrigen.executeUpdate();
			if (count != 1) {
				conn.rollback(); // deshacer la transacción
				return false; // no se pudo actualizar el saldo del cliente origen
			}

			// Actualizar el saldo del cliente destino
			stmtDestino = conn.prepareStatement("UPDATE cliente SET saldo = saldo + ? WHERE id = ?");
			stmtDestino.setDouble(1, monto);
			stmtDestino.setInt(2, idClienteDestino);
			count = stmtDestino.executeUpdate();
			if (count != 1) {
				conn.rollback(); // deshacer la transacción
				return false; // no se pudo actualizar el saldo del cliente destino
			}

			conn.commit(); // confirmar la transacción
			return true; // la transferencia fue exitosa

		} catch (SQLException e) {
			if (conn != null) {
				conn.rollback(); // deshacer la transacción
			}
			e.printStackTrace();
			return false; // hubo un error en la transferencia
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmtOrigen != null) {
				stmtOrigen.close();
			}
			if (stmtDestino != null) {
				stmtDestino.close();
			}
			if (conn != null) {
				conn.setAutoCommit(true); // habilitar la confirmación automática de la transacción
				conn.close();
			}
		}
	}

}
