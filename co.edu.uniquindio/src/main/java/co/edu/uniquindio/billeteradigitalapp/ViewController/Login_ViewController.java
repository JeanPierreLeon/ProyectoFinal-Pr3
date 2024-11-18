package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.BilleteraDigitalApplication;
import co.edu.uniquindio.billeteradigitalapp.Controller.Login_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login_ViewController {

    String adminPath = "Crud-Administrador.fxml";
    String userPath = "VistaUsuario.fxml";
    Login_Controller loginController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void onIniciar(ActionEvent event) {
        String user = txtUser.getText().trim();
        String password = txtPassword.getText().trim();

        if (verificarDatos()) {
            if (loginController.iniciarSesion(user, password)) {
                if(user.equals("admin")){

                    cargarVistaAdmin();
                    cerrarVentanaLogin();
                }else {
                    crearVentana(event,userPath );
                    cerrarVentanaLogin();
                }
                mostrarMensaje("Inicio De Sesion", "Sesion Iniciada", "Sesion iniciada correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Error", "Error De Inicio De sesion", "Usuario o contraseña invalida.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Error", "Datos Nulos", "Rellene los campos solicitados", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onRegistrarse(ActionEvent event) {
//        registrarUsuario();
        String user = txtUser.getText().trim();
        String password = txtPassword.getText().trim();

        if (verificarDatos()) {
            if(loginController.registrarUsuario(user,password)){
                crearVentana(event,userPath );
                cerrarVentanaLogin();
                mostrarMensaje("Registro","Registro Exitoso","Se registro correctamente el usuario " + user, Alert.AlertType.INFORMATION);
            }else {
                mostrarMensaje("Error","Error De Registro","El usuario ya existe.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Error", "Datos Nulos", "Rellene los campos solicitados.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void initialize() {
        loginController = new Login_Controller();
    }


    private void crearVentana(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(BilleteraDigitalApplication.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Obtén el controlador de la nueva vista
            if (userPath.equals(fxmlPath)){
                VistaUsuario_ViewController controller = loader.getController();
                controller.setUsuario(txtUser.getText());
            }
             // Pasa el usuario

            // Muestra la nueva ventana
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cierra la ventana actual
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void registrarUsuario() {
//        String user = txtUser.getText().trim();
//        String password = txtPassword.getText().trim();
//
//        if (verificarDatos()) {
//            if(loginController.registrarUsuario(user,password)){
//                cerrarVentanaLogin();
//                mostrarMensaje("Registro","Registro Exitoso","Se registro correctamente el usuario " + user, Alert.AlertType.INFORMATION);
//            }else {
//                mostrarMensaje("Error","Error De Registro","El usuario ya existe.", Alert.AlertType.ERROR);
//            }
//        } else {
//            mostrarMensaje("Error", "Datos Nulos", "Rellene los campos solicitados.", Alert.AlertType.ERROR);
//        }
//    }

    private void cargarVistaAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BilleteraDigitalApplication.class.getResource("Crud-Administrador.fxml"));
            Parent root = fxmlLoader.load();
            Stage nuevaVentana = new Stage();
            Scene scene = new Scene(root);
            nuevaVentana.setTitle("Billetera Digital");
            nuevaVentana.setScene(scene);
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVistaUsuario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BilleteraDigitalApplication.class.getResource("VistaUsuario.fxml"));
            Parent root = fxmlLoader.load();
            Stage nuevaVentana = new Stage();
            Scene scene = new Scene(root);
            nuevaVentana.setTitle("Billetera Digital");
            nuevaVentana.setScene(scene);
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cerrarVentanaLogin() {
        Stage stage = (Stage) btnIniciar.getScene().getWindow();
        stage.close();
    }

    private boolean verificarDatos() {
        if(txtUser.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.show();
    }

}