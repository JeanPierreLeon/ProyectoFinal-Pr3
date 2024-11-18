package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.NequiController;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;
import co.edu.uniquindio.billeteradigitalapp.Utils.RabbitMessageListener;
import co.edu.uniquindio.billeteradigitalapp.rabbit.consumer.controller.ModelFactoryController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NequiViewController implements RabbitMessageListener {

    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    Cuenta nequiCuenta;
    Usuario usuarioNequi;
    NequiController nequiController;

    ObservableList<Transaccion> transacciones = FXCollections.observableArrayList();
    @FXML
    private TableView<Transaccion> TableViewTransaccion;

    @FXML
    private Button btnAgregarNequi;

    @FXML
    private Button btnRetirarNequi;

    @FXML
    private Button btnTransferirNequi;

    @FXML
    private TableColumn<Transaccion, String> coNumCuenta;

    @FXML
    private TableColumn<Transaccion, String> colMonto;

    @FXML
    private TableColumn<Transaccion, String> colUsuario;

    @FXML
    private Label txtSaldoDisponibleNequi;

    @FXML
    private Label labelUser;

    @FXML
    void initialize() {
        nequiController = new NequiController();
        initDataBinding();
        obtenerTransaccionesCuenta();
        TableViewTransaccion.setItems(transacciones);
        modelFactoryController.addRabbitMessageListener(this);
        modelFactoryController.iniciarConsumidor();
    }

    private void initDataBinding() {
        coNumCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuentaDestino().getNumeroCuenta())));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCuentaOrigen().getNumeroCuenta())));
        colMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
    }

    @FXML
    void onAgregarNequi(ActionEvent event) {
        // Aquí va la lógica del botón "Agregar"
    }

    @FXML
    void onRetirarNequi(ActionEvent event) {
        // Aquí va la lógica del botón "Retirar"
    }

    @FXML
    void onTransferirNequi(ActionEvent event) {
        // Aquí va la lógica del botón "Transferir"
    }

    @Override
    public void onMessageReceived(String message) {
        System.out.println(message);
        String[] splitMessage = message.split("#");
        System.out.println(splitMessage[2]);

        // Obtener la cuenta de forma asincrónica
        obtenerCuenta(splitMessage[2]);

        // Obtener las transacciones de la cuenta de forma asincrónica
        obtenerTransaccionesCuenta();

        // Actualizar la UI en el hilo correcto
        Platform.runLater(() -> {
            initLabel(); // Actualización de los labels en el hilo de JavaFX
        });
    }

    private void initLabel() {
        if (nequiCuenta != null) {
            // Obtener el usuario asociado a la cuenta mediante NequiController
            Usuario usuario = nequiController.obtenerUsuarioDeCuenta(nequiCuenta);
            if (usuario != null) {
                labelUser.setText(usuario.getNombre()); // Actualiza el label con el nombre del usuario
            } else {
                labelUser.setText("Usuario no disponible");
            }
        }
        txtSaldoDisponibleNequi.setText(String.valueOf(nequiCuenta.getSaldo())); // Actualiza el saldo disponible
    }

    private void obtenerCuenta(String s) {
        // Obtiene la cuenta a partir del número de cuenta
        nequiCuenta = nequiController.obtenerCuentaNum(s);
    }

    private void obtenerTransaccionesCuenta() {
        // Obtiene las transacciones de la cuenta
        transacciones.clear();
        transacciones.addAll(nequiController.getTransacciones(nequiCuenta));
        TableViewTransaccion.setItems(transacciones);
        TableViewTransaccion.refresh();
    }
}
