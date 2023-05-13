/**
 * La clase Administrador es una subclase de Usuario que representa a un administrador en el sistema.
 * Tiene métodos para la gestión de productos, clientes y ventas.
 */

package ar.roberto.proyecto3.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Administrador extends Usuario {
	private List<Producto> productos;
	private Map<Cliente, Double> saldosClientes;
	private List<Compra> ventas;

	public Administrador(int id, String email, String password, double saldo) {
		super(id, email, password, saldo);
		this.productos = new ArrayList<>();
		this.saldosClientes = new HashMap<>();
		this.ventas = new ArrayList<>();
	}

	public Administrador(int id, String email, String password) {
	    super(id, email, password);
	   
	}
	// Métodos para la gestión de productos

	public void agregarProducto(Producto producto) {
		this.productos.add(producto);
	}

	public void editarProducto(Producto producto) {
		for (int i = 0; i < productos.size(); i++) {
			if (productos.get(i).getCodigo() == producto.getCodigo()) {
				productos.set(i, producto);
				break;
			}
		}
	}

	public void eliminarProducto(int codigo) {
		for (int i = 0; i < productos.size(); i++) {
			if (productos.get(i).getCodigo() == codigo) {
				productos.remove(i);
				break;
			}
		}
	}

	public List<Producto> getProductos() {
		return productos;
	}

	// Métodos para la gestión de clientes

	public void agregarSaldoCliente(Cliente cliente, double saldo) {
		double saldoActual = saldosClientes.getOrDefault(cliente, 0.0);
		saldosClientes.put(cliente, saldoActual + saldo);
	}

	public void quitarSaldoCliente(Cliente cliente, double saldo) {
		double saldoActual = saldosClientes.getOrDefault(cliente, 0.0);
		saldosClientes.put(cliente, saldoActual - saldo);
	}

	public double consultarSaldoCliente(Cliente cliente) {
		return saldosClientes.getOrDefault(cliente, 0.0);
	}

	// Métodos para la gestión de ventas

	public void registrarVenta(Cliente cliente, List<Producto> productosComprados) {
		double precioTotal = productosComprados.stream().mapToDouble(Producto::getPrecio).sum();
		Compra compra = new Compra(LocalDateTime.now(), (Map<Producto, Integer>) productosComprados, precioTotal);
		ventas.add(compra);
		agregarSaldoCliente(cliente, -precioTotal);
	}

	public void marcarVentaEntregada(Compra compra) {
		compra.setEntregado(true);
	}

	public List<Compra> getVentas() {
		return ventas;
	}
}
