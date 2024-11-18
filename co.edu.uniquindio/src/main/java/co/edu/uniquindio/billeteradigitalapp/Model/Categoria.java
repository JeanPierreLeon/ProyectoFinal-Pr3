package co.edu.uniquindio.billeteradigitalapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String idCategoria;
    private String descripcion;
    private static int cantidadCategorias;

    public Categoria() {
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getIdCategoria() {
        return idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static int getCantidadCategorias() {
        return cantidadCategorias;
    }

    public static void setCantidadCategorias(int cantidadCategorias) {
        Categoria.cantidadCategorias = cantidadCategorias;
    }
}