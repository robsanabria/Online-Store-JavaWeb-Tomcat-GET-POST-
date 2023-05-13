/**

Clase que implementa la interfaz InterfazProductoDAO y provee métodos para el acceso y manipulación de los datos de productos en una base de datos.

@author Roberto Sanabria
@version 4.0
@since 2023
*/
package ar.roberto.proyecto3.service.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.roberto.proyecto3.factories.ConexionFactory;
import ar.roberto.proyecto3.model.Producto;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarProducto;
import ar.roberto.proyecto3.service.interfaces.InterfazProductoDAO;

public class ProductoDAO implements InterfazProductoDAO {

	public static List<Producto> all() {
		List<Producto> productos = new ArrayList<>();
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM producto")) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				double precio = rs.getDouble("precio");
				int stock = rs.getInt("stock");
				Producto producto = new Producto(codigo, nombre, descripcion, precio, stock);
				productos.add(producto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return productos;
	}

	public static boolean eliminarProducto(int codigo) throws SQLException, ExceptionEliminarProducto {
		try (Connection conn = ConexionFactory.getConexion();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM producto WHERE codigo = ?")) {
			stmt.setInt(1, codigo);
			int resultado = stmt.executeUpdate();
			return resultado > 0;
		}
	}
	
	public boolean editarProducto(Producto producto) {
	    boolean resultado = false;
	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement ps = conn.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE codigo = ?")) {
	        ps.setString(1, producto.getNombre());
	        ps.setString(2, producto.getDescripcion());
	        ps.setDouble(3, producto.getPrecio());
	        ps.setInt(4, producto.getStock());
	        ps.setInt(5, producto.getCodigo());
	        resultado = ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resultado;
	}
	
	public static boolean crearProducto(Producto producto) {
	    boolean resultado = false;
	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement ps = conn.prepareStatement("INSERT INTO producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)")) {
	        ps.setString(1, producto.getNombre());
	        ps.setString(2, producto.getDescripcion());
	        ps.setDouble(3, producto.getPrecio());
	        ps.setInt(4, producto.getStock());
	        resultado = ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resultado;
	}
	
	public static Producto obtenerProductoPorCodigo(int codigo) {
	    Producto producto = null;
	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement ps = conn.prepareStatement("SELECT * FROM producto WHERE codigo = ?")) {
	        ps.setInt(1, codigo);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String descripcion = rs.getString("descripcion");
	            double precio = rs.getDouble("precio");
	            int stock = rs.getInt("stock");
	            producto = new Producto(codigo, nombre, descripcion, precio, stock);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return producto;
	}
	
	public static boolean verificarStockDisponible(int codigo, int cantidadDeseada) {
	    boolean stockDisponible = false;
	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement ps = conn.prepareStatement("SELECT stock FROM producto WHERE codigo = ?")) {
	        ps.setInt(1, codigo);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int stockActual = rs.getInt("stock");
	            if (stockActual >= cantidadDeseada) {
	                stockDisponible = true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return stockDisponible;
	}
	
	public static void actualizarStock(int codigo, int cantidadComprada) throws SQLException {
	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement("UPDATE Producto SET stock = stock - ? WHERE codigo = ?")) {
	        pstmt.setInt(1, cantidadComprada);
	        pstmt.setInt(2, codigo);
	        pstmt.executeUpdate();
	    }
	}


}
