/**

Esta clase representa una compra realizada por un cliente. Contiene información sobre la fecha de compra,
los productos comprados, el precio total de la compra, si ya fue entregada o no, y el cliente que realizó
la compra. La clase también incluye métodos para obtener y establecer los atributos, así como constructores
para crear objetos de compra con diferentes combinaciones de atributos.

@author Roberto Sanabria
@version 4.0
@since 2023
*/

package ar.roberto.proyecto3.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Compra {
	private int idCompra;
	private LocalDateTime fecha;
	private Map<Producto, Integer> productosComprados;
	private double precioTotal;
	private boolean entregado;
	private Cliente cliente;

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Map<Producto, Integer> getProductosComprados() {
		return productosComprados;
	}

	public void setProductosComprados(Map<Producto, Integer> productosComprados) {
		this.productosComprados = productosComprados;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Compra(int idCompra, LocalDateTime fecha, Map<Producto, Integer> productosComprados, double precioTotal,
			Cliente cliente) {
		this.idCompra = idCompra;
		this.fecha = fecha;
		this.productosComprados = productosComprados;
		this.precioTotal = precioTotal;
		this.cliente = cliente;
	}

	public Compra(LocalDateTime fecha, Map<Producto, Integer> productosComprados, double precioTotal, Cliente cliente) {
		this.fecha = fecha;
		this.productosComprados = productosComprados;
		this.precioTotal = precioTotal;
		this.cliente = cliente;
	}

	public Compra(LocalDateTime fecha, Map<Producto, Integer> productosComprados, double precioTotal) {
		this.fecha = fecha;
		this.productosComprados = productosComprados;
		this.precioTotal = precioTotal;

	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public Compra(Map<Producto, Integer> productosComprados) {
		this.productosComprados = productosComprados;
		this.precioTotal = 0.0;
		for (Map.Entry<Producto, Integer> entry : productosComprados.entrySet()) {
			Producto producto = entry.getKey();
			int cantidad = entry.getValue();
			this.precioTotal += producto.getPrecio() * cantidad;
		}
		// establece el valor del precio total
		setPrecioTotal(this.precioTotal);
	}

	public Compra() {
		// TODO Auto-generated constructor stub
	}

	public Compra(int idCompra2, Cliente obtenerClientePorID, LocalDateTime ofInstant, double total,
			boolean entregado2) {
		// TODO Auto-generated constructor stub
	}

	public Compra(int idCompra, LocalDateTime fecha, Map<Producto, Integer> productosComprados, double precioTotal,
			boolean entregado, Cliente cliente) {
		this.idCompra = idCompra;
		this.fecha = fecha;
		this.productosComprados = productosComprados;
		this.precioTotal = precioTotal;
		this.entregado = entregado;
		this.cliente = cliente;
	}

	public Compra(int idCompra, LocalDateTime fecha, double precioTotal, boolean entregado, Cliente cliente) {
		this.idCompra = idCompra;
		this.fecha = fecha;
		this.precioTotal = precioTotal;
		this.entregado = entregado;
		this.cliente = cliente;
	}
	
	public Compra(int idCompra, Cliente cliente, LocalDateTime fecha, boolean entregado, double precioTotal) {
	    this.idCompra = idCompra;
	    this.cliente = cliente;
	    this.fecha = fecha;
	    this.entregado = entregado;
	    this.precioTotal = precioTotal;
	}


}
