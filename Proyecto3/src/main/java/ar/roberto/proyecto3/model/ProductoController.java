package ar.roberto.proyecto3.model;

import java.util.ArrayList;
import java.util.List;

public class ProductoController {

	private List<Producto> productos = new ArrayList<>();

	public ProductoController() {
		// Constructor vacío
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public boolean cargarProducto(int codigo, String nombre, String descripcion, double precio, int stock) {
		Producto producto = new Producto(codigo, nombre, descripcion, precio, stock);
		return productos.add(producto);
	}

	public boolean editarProducto(int codigo, String nombre, String descripcion, double precio, int stock) {
		for (Producto producto : productos) {
			if (producto.getCodigo() == codigo) {
				producto.setNombre(nombre);
				producto.setDescripcion(descripcion);
				producto.setPrecio(precio);
				producto.setStock(stock);
				return true;
			}
		}
		return false;
	}

	public boolean eliminarProducto(int codigo) {
		for (Producto producto : productos) {
			if (producto.getCodigo() == codigo) {
				productos.remove(producto);
				return true;
			}
		}
		return false;
	}
}
