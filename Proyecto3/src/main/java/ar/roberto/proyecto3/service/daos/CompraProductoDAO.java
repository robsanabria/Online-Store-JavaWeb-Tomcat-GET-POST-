package ar.roberto.proyecto3.service.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.roberto.proyecto3.factories.ConexionFactory;
import ar.roberto.proyecto3.model.CompraProducto;

public class CompraProductoDAO {
	
	public static List<CompraProducto> obtenerComprasProductos() {
		
		List<CompraProducto> comprasProductos = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ConexionFactory.getConexion();
			stmt = conn.prepareStatement("SELECT idCompra, codigo, cantidad FROM compra_producto");
			rs = stmt.executeQuery();

			while (rs.next()) {
				int idCompra = rs.getInt("idCompra");
				int codigo = rs.getInt("codigo");
				int cantidad = rs.getInt("cantidad");

				CompraProducto compraProducto = new CompraProducto(idCompra, codigo, cantidad);
				comprasProductos.add(compraProducto);
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener las compras de productos: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("Error al cerrar las conexiones: " + e.getMessage());
			}
		}

		return comprasProductos;
	}

}
