package co.edu.uniquindio.billeteradigitalapp.Controller.Service;

import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;
import co.edu.uniquindio.billeteradigitalapp.Model.Presupuesto;

import java.util.ArrayList;
import java.util.List;

public interface IPresupuestoControllService {

    boolean crearPresupuesto(Presupuesto presupuesto);
    boolean actualizarPresupuesto(Presupuesto presupuesto);
    boolean eliminarPresupuesto(String idPresupuesto);
    List<Presupuesto> obtenerPresupuestos();
    List<Categoria> obtenerCategorias();

    Categoria encontrarCategoria(String value);
}
