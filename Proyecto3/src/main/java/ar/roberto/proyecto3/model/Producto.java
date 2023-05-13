/**
 * La clase Producto representa un objeto que contiene información sobre un producto
 * en particular, como su código, nombre, descripción, precio y stock. También incluye
 * métodos para agregar y quitar stock del producto.
 * 
@author Roberto Sanabria
@version 4.0
@since 2023
 */

package ar.roberto.proyecto3.model;

public class Producto {
	private int codigo;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;

	public Producto(int codigo, String nombre, String descripcion, double precio, int stock) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}

	public Producto(String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public Producto(String nombre, String descripcion, double precio, int stock) {
		this(0, nombre, descripcion, precio, stock);
	}

	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean agregarStock(int stock) {
		this.stock += stock;
		return true;
	}

	public boolean quitarStock(int stock) {
		if (this.stock >= stock) {
			this.stock -= stock;
			return true;
		} else {
			return false;
		}
	}

	public void actualizarStock(int cantidad) {
		// TODO Auto-generated method stub

	}

	public int getIdProducto() {
		return codigo;
	}
}