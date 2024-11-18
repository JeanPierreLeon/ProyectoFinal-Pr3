package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Controller.Service.ICuentaControllerService;
import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;

import java.util.List;

public class Cuenta_Controller implements ICuentaControllerService {

    static ModelFactory modelFactory;

    public Cuenta_Controller(){
        modelFactory = ModelFactory.getInstance();
    }

    public boolean crearcuenta(Cuenta cuenta) {
        return modelFactory.crearCuenta(cuenta);
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerCuentas();
    }

    public boolean actualizarCuenta(Cuenta cuenta) {
        return modelFactory.actualizarCuenta(cuenta);
    }

    public boolean eliminarCuenta(String idCuenta) {
        return modelFactory.eliminarCuenta(idCuenta);
    }
}
