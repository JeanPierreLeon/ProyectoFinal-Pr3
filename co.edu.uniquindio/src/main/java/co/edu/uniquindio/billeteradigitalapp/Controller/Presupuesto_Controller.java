package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Controller.Service.IPresupuestoControllService;
import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;
import co.edu.uniquindio.billeteradigitalapp.Model.Presupuesto;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto_Controller implements IPresupuestoControllService {

    static ModelFactory modelFactory;

    public Presupuesto_Controller() {
        modelFactory = ModelFactory.getInstance();
    }

    public List<Presupuesto> obtenerPresupuestos() {
        return modelFactory.obtenerPresupuestos();
    }

    public boolean crearPresupuesto(Presupuesto presupuesto) {

        return modelFactory.crearPresupuesto(presupuesto);
    }

    public boolean actualizarPresupuesto(Presupuesto presupuesto) {
        return modelFactory.actualizarPresupuesto(presupuesto);
    }

    public boolean eliminarPresupuesto(String idPresupuesto) {
        return modelFactory.eliminarPresupuesto(idPresupuesto);
    }

    public List<Categoria> obtenerCategorias() {
        return modelFactory.obtenerCategorias();
    }

    @Override
    public Categoria encontrarCategoria(String nombreCategoria) {
        return modelFactory.encontarCategoria(nombreCategoria);
    }
}
