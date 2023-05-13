package ar.roberto.proyecto3.model;


public class Carrito {
    private int idCarrito;
    private int idCliente;
    private int idProducto;
    private int cantidad;
    private double total;

    public Carrito(int idCarrito, int idCliente, int idProducto, int cantidad, double total) {
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Carrito(int idCliente2, int idProducto2, int i) {
		// TODO Auto-generated constructor stub
	}

	public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
