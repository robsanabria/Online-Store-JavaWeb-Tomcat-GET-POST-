package ar.roberto.proyecto3.model;

public class CompraProducto {
    private int idCompra;
    private int codigoProducto;
    private int cantidad;

    public CompraProducto(int idCompra, int codigoProducto, int cantidad) {
        this.idCompra = idCompra;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
