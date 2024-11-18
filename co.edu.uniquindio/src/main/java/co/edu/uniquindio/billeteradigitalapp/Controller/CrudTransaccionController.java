package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;

import java.util.List;

public class CrudTransaccionController {
    static ModelFactory modelFactory;

    public CrudTransaccionController() {
        modelFactory = ModelFactory.getInstance();
    }

    public List<Transaccion> obtenerTransacciones() {
        return modelFactory.obtenerTransacciones();
    }

    public boolean crearTransacciones(Transaccion transaccion) {

        return modelFactory.crearTransaccion(transaccion);
    }


    public List<Transaccion> obtenerTipoTransaccion() {
        return modelFactory.obtenerTransacciones();
    }


}
