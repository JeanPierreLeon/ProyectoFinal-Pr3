package co.edu.uniquindio.billeteradigitalapp.Model;

import co.edu.uniquindio.billeteradigitalapp.Utils.Persistencia;

import java.io.Serializable;

public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private double montoTotalAsignado;
    private double montoGastado;
    private  int idPresupuesto;
    private Categoria categoria;

    public Presupuesto() {
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMontoTotalAsignado() {
        return montoTotalAsignado;
    }

    public void setMontoTotalAsignado(double montoTotalAsignado) {
        this.montoTotalAsignado = montoTotalAsignado;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}