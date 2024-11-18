package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.VistaUsuario_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RetirarViewController {
    private Cuenta cuenta;
    private VistaUsuario_Controller controller;
    public static double monto1;

    @FXML
    private TextField montoField;
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        this.controller = new VistaUsuario_Controller();
    }

    @FXML
    private void confirmarRetiro() {
        try {
            // Validar que el monto sea un número válido
            String montoText = montoField.getText();
            if (montoText.isEmpty() || !montoText.matches("\\d+(\\.\\d{1,2})?")) {
                mostrarAlerta("Error", "Por favor ingrese un monto válido.", Alert.AlertType.ERROR);
                return;
            }

            double monto = Double.parseDouble(montoText);

            // Verificar que el monto no exceda el saldo disponible
            if (monto > cuenta.getSaldo()) {
                mostrarAlerta("Saldo Insuficiente", "El monto a retirar excede el saldo disponible.", Alert.AlertType.WARNING);
                return;
            }

            // Actualizar el saldo de la cuenta
            double retiro = cuenta.getSaldo() - monto;
            cuenta.setSaldo(retiro);
            controller.actualizarSaldoCuenta(cuenta);
            mostrarAlerta("Éxito", "Retiro realizado con éxito. Saldo actual: $" + cuenta.getSaldo(), Alert.AlertType.INFORMATION);
            // Cerrar la ventana
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelarRetiro() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) montoField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
