package co.edu.uniquindio.billeteradigitalapp.Controller.Service;

import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;

import java.util.List;

public interface IUsuarioControllerService {

    public List<Usuario> obtenerUsuarios();

    public boolean crearUsuario(Usuario usuario);

    public boolean actualizarUsuario(Usuario usuario);

    public boolean eliminarUsuario(String cedula);
}
