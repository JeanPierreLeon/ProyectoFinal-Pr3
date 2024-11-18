package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class NequiController {
    ModelFactory modelFactory;
    private List<Cuenta> cuentas;

    public NequiController() {
        modelFactory = ModelFactory.getInstance();
        cuentas = new ArrayList<>();
    }

    public Cuenta obtenerCuentaNum(String s) {
       return modelFactory.obtenerCuenta(s);
    }
    public ArrayList<Transaccion> getTransacciones(Cuenta cuenta) {
        return modelFactory.getTransacciones(cuenta);
    }

    /**
     * Obtiene el usuario asociado a una cuenta.
     *
     * @param cuenta La cuenta de la cual se desea obtener el usuario.
     * @return El usuario asociado a la cuenta, o null si no se encuentra.
     */
    public Usuario obtenerUsuarioDeCuenta(Cuenta cuenta) {
        // Recorremos la lista de cuentas para encontrar la cuenta que coincida
        for (Cuenta c : cuentas) {
            if (c.getNumeroCuenta() == cuenta.getNumeroCuenta()) { // Comparación de int utilizando '=='
                return c.getUsuario();  // Devolver el usuario asociado
            }
        }
        return null;  // Si no se encuentra la cuenta, devolvemos null
    }
}
