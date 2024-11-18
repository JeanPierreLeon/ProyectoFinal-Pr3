package co.edu.uniquindio.billeteradigitalapp.Controller.Service;

public interface ILoginControllerService {
    boolean iniciarSesion(String user, String password);
    boolean registrarUsuario(String user, String password);
}
