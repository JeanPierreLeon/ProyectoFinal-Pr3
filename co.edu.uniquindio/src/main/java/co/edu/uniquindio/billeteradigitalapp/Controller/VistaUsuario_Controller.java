package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class VistaUsuario_Controller {
    static ModelFactory modelFactory;
    public VistaUsuario_Controller() {
            modelFactory = ModelFactory.getInstance();
    }
    public Usuario getUsuario(String correo) {
            return modelFactory.getUsuarioPorCorreo(correo);

    }

    public ArrayList<Transaccion> getTransacciones(Cuenta cuenta) {
        return modelFactory.getTransacciones(cuenta);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        return modelFactory.actualizarUsuario(usuario);

    }


    public List<Transaccion> obtenerTransacciones() {
        return modelFactory.obtenerTransacciones();
    }

    public boolean actualizarSaldoCuenta(Cuenta cuenta) {
     return modelFactory.actualizarCuenta(cuenta);
    }

}
