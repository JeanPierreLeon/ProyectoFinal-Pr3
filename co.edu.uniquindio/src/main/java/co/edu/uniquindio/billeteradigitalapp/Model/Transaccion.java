package co.edu.uniquindio.billeteradigitalapp.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fecha;
    private String tipoTransaccion;
    private double monto;
    private String descripcionTransaccion;
    private int idTransaccion;
    private static int cantidadTransacciones;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;




    public Transaccion() {
        idTransaccion = ++cantidadTransacciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public static int getCantidadTransacciones() {
        return cantidadTransacciones;
    }

    public static void setCantidadTransacciones(int cantidadTransacciones) {
        Transaccion.cantidadTransacciones = cantidadTransacciones;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}