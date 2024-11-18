package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Controller.Service.IUsuarioControllerService;
import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;

import java.util.List;

public class CrudUsuario_Controller implements IUsuarioControllerService {
    static ModelFactory modelFactory;

    public CrudUsuario_Controller() {
        modelFactory = ModelFactory.getInstance();
    }

    public List<Usuario> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public boolean crearUsuario(Usuario usuario) {
        return modelFactory.crearUsuario(usuario);
    }

    public boolean eliminarUsuario(String cedula) {
        return modelFactory.eliminarUsuario(cedula);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        return modelFactory.actualizarUsuario(usuario);
    }
}
