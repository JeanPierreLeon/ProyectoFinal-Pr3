package co.edu.uniquindio.billeteradigitalapp.Controller;

import co.edu.uniquindio.billeteradigitalapp.Controller.Service.ILoginControllerService;
import co.edu.uniquindio.billeteradigitalapp.Factory.ModelFactory;

public class Login_Controller implements ILoginControllerService {

    ModelFactory modelFactory;

    public Login_Controller() {
        modelFactory = ModelFactory.getInstance();
    }

    public boolean iniciarSesion(String user, String password) {
        return modelFactory.iniciarSesion(user,password);
    }

    public boolean registrarUsuario(String user, String password) {
        return modelFactory.registrarUsuario(user,password);
    }

    public int validation(String user, String password) {
        return modelFactory.validation(user,password);
    }
}