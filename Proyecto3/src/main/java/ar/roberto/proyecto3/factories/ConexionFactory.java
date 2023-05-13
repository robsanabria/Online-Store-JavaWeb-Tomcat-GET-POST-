
//La ConnectionFactory se utiliza generalmente como un pool de conexiones, 
//lo que significa que en lugar de crear una nueva conexión cada vez que se necesita una, 
//se obtiene una conexión existente del pool y se devuelve a él cuando se termina de usar.
//Esto puede mejorar significativamente
//el rendimiento de la aplicación al reducir la sobrecarga de la creación y eliminación de conexiones.
//En este caso le facilito la direccion a la bd "proyectojava" indicando el usuario y contraseña de acceso

//Patron de diseño que se utiliza para crear objetos sin tener que especificar la clase exacta del objeto creado
//En lugar de crrear objetos creando un constructor, se utiliza una factory para crear el objeto y devolver una instancia del mismo

package ar.roberto.proyecto3.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionFactory {

	private static final String SERVER_URL = "jdbc:mysql://localhost:3306/proyectojava";

	private static final String USER = "root";

	private static final String PASSWORD = "";

	public static Connection getConexion() throws SQLException {

//factories son objetos que se encargan de crear otros objetivos, este caso es una factory estatica.

		try {
			// levanta conector de la base de datos y atravez del Driver levanta la conexion
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);

		return conn;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
