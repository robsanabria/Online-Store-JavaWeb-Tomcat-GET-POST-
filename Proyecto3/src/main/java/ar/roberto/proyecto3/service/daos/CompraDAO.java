package ar.roberto.proyecto3.service.daos;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.roberto.proyecto3.factories.ConexionFactory;
import ar.roberto.proyecto3.model.Cliente;
import ar.roberto.proyecto3.model.Compra;
import ar.roberto.proyecto3.model.Producto;

public class CompraDAO {
	/**
	 * Guarda una compra en la base de datos.
	 * 
	 * @param guarda"la compra a guardar
	 * @return el ID generado para la compra guardada
	 * @throws RuntimeException si ocurre algún error al guardar la compra en la base de datos
	 */
	public static int guardarCompra(Compra compra) {

	    int idCompra = 0;

	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Compra (idCliente, fechaCompra, total, entregado) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

	        // Establecer los parámetros de la consulta
	        pstmt.setInt(1, compra.getCliente().getId());
	        pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(compra.getFecha()));
	        pstmt.setDouble(3, compra.getPrecioTotal());
	        pstmt.setBoolean(4, compra.isEntregado());
	        // Ejecutar la consulta y obtener el ID generado
	        pstmt.executeUpdate();
	        ResultSet rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            idCompra = rs.getInt(1);
	        }

	        // Guardar los detalles de la compra (productos comprados) en la tabla Compra_Producto
	        Map<Producto, Integer> productosComprados = compra.getProductosComprados();
	        for (Map.Entry<Producto, Integer> entry : productosComprados.entrySet()) {
	            Producto producto = entry.getKey();
	            int cantidad = entry.getValue();

	            try (PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Compra_Producto (idCompra, codigo, cantidad) VALUES (?, ?, ?)")) {
	                // Establecer los parámetros de la consulta
	                pstmt2.setInt(1, idCompra);
	                pstmt2.setInt(2, producto.getCodigo());
	                pstmt2.setInt(3, cantidad);

	                // Ejecutar la consulta
	                pstmt2.executeUpdate();

	                // Actualizar la cantidad del producto en la tabla producto
	                try (PreparedStatement pstmt3 = conn.prepareStatement("UPDATE producto SET stock = stock - ? WHERE codigo = ?")) {
	                    // Establecer los parámetros de la consulta
	                    pstmt3.setInt(1, cantidad);
	                    pstmt3.setInt(2, producto.getCodigo());

	                    // Ejecutar la consulta
	                    pstmt3.executeUpdate();
	                }
	            }
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        throw new RuntimeException("Error al guardar la compra en la base de datos");
	    }

	    return idCompra;
	}

	  
	  public static List<Compra> recuperarCompras(int idCliente) throws SQLException {
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    List<Compra> compras = new ArrayList<>();

		    try {
		        conn = ConexionFactory.getConexion();
		        String query = "SELECT * FROM Compra WHERE idCliente = ?";
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idCliente);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            int idCompra = rs.getInt("idCompra");
		            LocalDateTime fechaCompra = rs.getObject("fechaCompra", LocalDateTime.class);
		            double total = rs.getDouble("total");
		            boolean entregado = rs.getBoolean("entregado");

		            Cliente cliente = ClienteDAO.obtenerClientePorID(idCliente);

		            Compra compra = new Compra(idCompra, fechaCompra, total, entregado, cliente);
		            compras.add(compra);
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    } finally {
		        ConexionFactory.close(rs);
		        ConexionFactory.close(stmt);
		        ConexionFactory.close(conn);
		    }

		    return compras;
		}
	  
	  public static List<Compra> obtenerCompras() {
		    List<Compra> compras = new ArrayList<>();

		    try (Connection conn = ConexionFactory.getConexion();
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery("SELECT * FROM Compra")) {

		        // Recorrer los resultados de la consulta
		        while (rs.next()) {
		            // Crear un objeto Compra y establecer sus atributos
		            int idCompra = rs.getInt("idCompra");
		            int idCliente = rs.getInt("idCliente");
		            LocalDateTime fecha = rs.getTimestamp("fechaCompra").toLocalDateTime();
		            double total = rs.getDouble("total");
		            boolean entregado = rs.getBoolean("entregado");
		            Cliente cliente = ClienteDAO.obtenerClientePorID(idCliente);
		            Compra compra = new Compra(idCompra, cliente, fecha, entregado, total);

		            // Agregar la compra a la lista
		            compras.add(compra);
		        }

		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        throw new RuntimeException("Error al obtener las compras de la base de datos");
		    }

		    return compras;
		}
	  
	  public static boolean actualizarEstadoEntregado(int idCompra, boolean entregado) {
		    boolean exito = false;
		    Connection con = null;
		    PreparedStatement ps = null;

		    try {
		        con = ConexionFactory.getConexion();
		        String sql = "UPDATE compra SET entregado = ? WHERE idCompra = ?";
		        ps = con.prepareStatement(sql);
		        ps.setBoolean(1, entregado);
		        ps.setInt(2, idCompra);
		        int resultado = ps.executeUpdate();
		        if (resultado > 0) {
		            exito = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return exito;
		}




	 

	
}
