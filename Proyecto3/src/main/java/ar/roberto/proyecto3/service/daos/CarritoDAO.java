/**

Clase que permite acceder a los datos de la tabla "carrito" en la base de datos.
Contiene métodos para listar, agregar, actualizar y eliminar carritos, así como para
obtener carritos por su ID o por el ID de cliente. También incluye métodos estáticos
para obtener los productos en el carrito de un cliente y para agregar un producto al carrito.

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

import ar.roberto.proyecto3.model.Carrito;
import ar.roberto.proyecto3.factories.ConexionFactory;

public class CarritoDAO {

    private static Connection conn;

    public CarritoDAO() throws SQLException {
        conn = ConexionFactory.getConexion();
    }

    public List<Carrito> listarCarritos() throws SQLException {
        String sql = "SELECT * FROM carrito";
        List<Carrito> listaCarritos = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idCarrito = rs.getInt("idCarrito");
                int idCliente = rs.getInt("idCliente");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");
                double total = rs.getDouble("total");
                Carrito carrito = new Carrito(idCarrito, idCliente, idProducto, cantidad, total);
                listaCarritos.add(carrito);
            }
        }
        return listaCarritos;
    }

    public void agregarCarrito(Carrito carrito) throws SQLException {
        String sql = "INSERT INTO carrito (idCliente, idProducto, cantidad, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getIdCliente());
            ps.setInt(2, carrito.getIdProducto());
            ps.setInt(3, carrito.getCantidad());
            ps.setDouble(4, carrito.getTotal());
            ps.executeUpdate();
        }
    }

    public void actualizarCarrito(Carrito carrito) throws SQLException {
        String sql = "UPDATE carrito SET idCliente = ?, idProducto = ?, cantidad = ?, total = ? WHERE idCarrito = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getIdCliente());
            ps.setInt(2, carrito.getIdProducto());
            ps.setInt(3, carrito.getCantidad());
            ps.setDouble(4, carrito.getTotal());
            ps.setInt(5, carrito.getIdCarrito());
            ps.executeUpdate();
        }
    }

    public static void eliminarCarrito(int idCarrito) throws SQLException {
        String sql = "DELETE FROM carrito WHERE idCarrito = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            ps.executeUpdate();
        }
    }

    public Carrito obtenerCarritoPorId(int idCarrito) throws SQLException {
        String sql = "SELECT * FROM carrito WHERE idCarrito = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idCliente = rs.getInt("idCliente");
                    int idProducto = rs.getInt("idProducto");
                    int cantidad = rs.getInt("cantidad");
                    double total = rs.getDouble("total");
                    Carrito carrito = new Carrito(idCarrito, idCliente, idProducto, cantidad, total);
                    return carrito;
                }
            }
        }
        return null;
    }
    
    public static List<Carrito> obtenerProductosCarritoPorIdCliente(int idCliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Carrito> carritos = new ArrayList<>();

        try {
            conn = ConexionFactory.getConexion();
            stmt = conn.prepareStatement("SELECT * FROM carrito WHERE idCliente = ?");
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idCarrito = rs.getInt("idCarrito");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");
                double total = rs.getDouble("total");

                Carrito carrito = new Carrito(idCarrito, idCliente, idProducto, cantidad, total);
                carritos.add(carrito);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return carritos;
    }
    
    public static boolean agregarProductoAlCarrito(int idCliente, int idProducto, int cantidad, double total) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;

        try {
            conn = ConexionFactory.getConexion();
            stmt = conn.prepareStatement("INSERT INTO carrito(idCliente, idProducto, cantidad, total) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, total);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }
    
    public static boolean eliminarProductoDelCarrito(int idCliente, int idProducto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;

        try {
            conn = ConexionFactory.getConexion();
            stmt = conn.prepareStatement("DELETE FROM carrito WHERE idCliente = ? AND idProducto = ?");
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }

    public static double obtenerTotalCarritoPorIdCliente(int idCliente) throws SQLException {
	    double total = 0;

	    try (Connection conn = ConexionFactory.getConexion();
	         PreparedStatement ps = conn.prepareStatement("SELECT SUM(precio * cantidad) as total " +
	                                                      "FROM Carrito " +
	                                                      "JOIN Producto ON Carrito.idProducto = Producto.codigo " +
	                                                      "WHERE idCliente = ?")) {
	        ps.setInt(1, idCliente);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            total = rs.getDouble("total");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new SQLException("Ocurrió una excepción al obtener el total del carrito del cliente con ID " + idCliente, e);
	    }

	    return total;
	}


	public static boolean eliminarProductosDelCarrito(int idCliente) {
		  Connection conn = null;
	        PreparedStatement stmt = null;
	        boolean resultado = false;

	        try {
	            conn = ConexionFactory.getConexion();
	            stmt = conn.prepareStatement("DELETE FROM carrito WHERE idCliente = ?");
	            stmt.setInt(1, idCliente);

	            int filasAfectadas = stmt.executeUpdate();

	            if (filasAfectadas > 0) {
	                resultado = true;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) {
	                    stmt.close();
	                }
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return resultado;
		
	}

   

}
