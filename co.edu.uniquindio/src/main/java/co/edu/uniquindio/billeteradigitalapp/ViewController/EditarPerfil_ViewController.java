package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.VistaUsuario_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.BilleteraDigital;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;
import co.edu.uniquindio.billeteradigitalapp.Utils.Persistencia;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarPerfil_ViewController {
    BilleteraDigital billeteraDigital;

    private Usuario usuario;
    private VistaUsuario_Controller controller;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField correoField;

    @FXML
    private TextField telefonoField;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.controller = new VistaUsuario_Controller();
        // Inicializar campos con los datos actuales del usuario
        nombreField.setText(usuario.getNombre());
        correoField.setText(usuario.getCorreo());
        telefonoField.setText(usuario.getTelefono());
    }

    @FXML
    private void guardarCambios() {
        // Validar campos
        if (nombreField.getText().isEmpty() || correoField.getText().isEmpty() || telefonoField.getText().isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        // Actualizar los datos del usuario
        usuario.setNombre(nombreField.getText());
        usuario.setCorreo(correoField.getText());
        usuario.setTelefono(telefonoField.getText());

        // Actualizar en el controlador
        controller.actualizarUsuario(usuario);
        System.out.println("Perfil actualizado correctamente.");

        // Cerrar la ventana
        cerrarVentana();
    }


    @FXML
    private void cancelarEdicion() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
}
