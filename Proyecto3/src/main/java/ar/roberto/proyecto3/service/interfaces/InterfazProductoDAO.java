package ar.roberto.proyecto3.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import ar.roberto.proyecto3.model.Producto;
import ar.roberto.proyecto3.service.exceptions.ExceptionEliminarProducto;

public interface InterfazProductoDAO {
    
    public static List<Producto> all() {
		return null;
	}
    
    public static boolean eliminarProducto(int codigo) throws SQLException, ExceptionEliminarProducto {
		return false;
	}
    
    public boolean editarProducto(Producto producto);
    
    public static boolean crearProducto(Producto producto) {
		return false;
	}
    
}