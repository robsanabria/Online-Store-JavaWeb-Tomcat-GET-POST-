/**
 * La clase Cliente representa a un usuario que tiene la capacidad de agregar productos al carrito, finalizar una compra,
 * visualizar su historial de compras, agregar y transferir dinero, y obtener información sobre el carrito y el historial.
 *
 * Atributos:
 * - carrito: un mapa que relaciona un producto con la cantidad solicitada por el cliente.
 * - historialCompras: una lista que almacena todas las compras realizadas por el cliente.
 *
 * Métodos:
 * - agregarProductoAlCarrito: agrega un producto y la cantidad solicitada al carrito del cliente.
 * - eliminarProductoDelCarrito: elimina un producto del carrito del cliente.
 * - calcularPrecioTotalDelCarrito: calcula el precio total de los productos en el carrito del cliente.
 * - finalizarCompra: resta el precio total de los productos en el carrito del saldo del cliente y agrega la compra al historial.
 * - verHistorialDeCompras: muestra por pantalla todas las compras realizadas por el cliente.
 * - verCarritoDeCompras: muestra por pantalla todos los productos y cantidades en el carrito del cliente.
 * - agregarDinero: agrega una cantidad de dinero al saldo del cliente.
 * - transferirDinero: transfiere una cantidad de dinero desde el saldo del cliente a otro cliente.
 * - removerDinero: resta una cantidad de dinero del saldo del cliente.
 * - agregarCompraAlHistorial: agrega una compra al historial de compras del cliente.
 * - obtenerHistorialCompras: devuelve la lista de todas las compras realizadas por el cliente.
 */

package ar.roberto.proyecto3.model;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import ar.roberto.proyecto3.service.daos.CompraDAO;
import ar.roberto.proyecto3.service.daos.ProductoDAO;

public class Cliente extends Usuario {

	public Cliente(int id, String email, String password, double saldo) {
		super(id, email, password, saldo);

	}

	public void agregarDinero(double monto) {
		this.setSaldo(this.getSaldo() + monto);
	}

	public void transferirDinero(Cliente destinatario, double monto) {
		if (this.getSaldo() >= monto) {
			this.setSaldo(this.getSaldo() - monto);
			destinatario.agregarDinero(monto);
		} else {
			throw new RuntimeException("No tiene suficiente saldo para realizar la transferencia");
		}
	}

	public void removerDinero(double monto) {
		if (this.getSaldo() >= monto) {
			this.setSaldo(this.getSaldo() - monto);
		} else {
			throw new RuntimeException("No tiene suficiente saldo para realizar la operación");
		}
	}

	public boolean tieneSaldo(double precioTotal) {
		return this.getSaldo() >= precioTotal;
	}

	public void realizarCompra(Map<Producto, Integer> productosComprados, double precioTotal) {
		// Verificar que el cliente tenga suficiente saldo
		if (this.getSaldo() < precioTotal) {
			throw new RuntimeException("El cliente no tiene suficiente saldo para realizar la compra");
		}

		// Actualizar saldo del cliente
		this.removerDinero(precioTotal);

		// Generar objeto Compra
		LocalDateTime fecha = LocalDateTime.now();
		Compra compra = new Compra(fecha, productosComprados, precioTotal);

		// Verificar stock de productos comprados
		for (Map.Entry<Producto, Integer> entry : productosComprados.entrySet()) {
			Producto producto = entry.getKey();
			int cantidad = entry.getValue();
			Producto productoEnBD = ProductoDAO.obtenerProductoPorCodigo(producto.getCodigo());
			if (productoEnBD.getStock() < cantidad) {
				throw new RuntimeException("No hay suficiente stock para el producto " + producto.getNombre());
			}
			producto.quitarStock(cantidad);
		}

		// Guardar compra en la base de datos y obtener su ID
		int idCompra = CompraDAO.guardarCompra(compra);
		compra.setIdCompra(idCompra);
	}

}
