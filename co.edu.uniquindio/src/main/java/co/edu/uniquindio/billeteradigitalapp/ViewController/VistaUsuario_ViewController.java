package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.Cuenta_Controller;
import co.edu.uniquindio.billeteradigitalapp.Controller.VistaUsuario_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaUsuario_ViewController {


    public VistaUsuario_ViewController() {

    }

    Transaccion transaccionSeleccionada;
    Usuario usuario;
    Cuenta cuenta;
    ObservableList<Transaccion> transaccions = FXCollections.observableArrayList();
    VistaUsuario_Controller controller;

    @FXML
    private TableColumn<Transaccion, String> colDescripcion;

    @FXML
    private TableColumn<Transaccion, String> colFecha;

    @FXML
    private TableColumn<Transaccion, String> colMonto;

    @FXML
    private ListView<?> TransaccionesListView;

    @FXML
    private TableView<Transaccion> transaccionesTableView;

    @FXML
    private Button btnDepositar;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPresupuestos;

    @FXML
    private Button btnRetirar;

    @FXML
    private Button btnTransacciones;

    @FXML
    private Label labelMount;
    @FXML
    private Label labelUser;







    @FXML
    void onEnviar(ActionEvent event) {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteradigitalapp/VistaEnviar.fxml"));
            Parent root = loader.load();

            EnviarViewController controllerEnviar = loader.getController();
            controllerEnviar.setCuentaUsuario(cuenta);  // Se asegura que el controlador de la vista de envío tenga la cuenta del usuario
            controllerEnviar.setVistaUsuarioController(this);  // Pasa la referencia de VistaUsuario_ViewController

            // Crear una nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Enviar Dinero");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPerfil(ActionEvent event) {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteradigitalapp/EditarPerfil.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista y pasarle el usuario actual
            EditarPerfil_ViewController editarPerfilController = loader.getController();
            editarPerfilController.setUsuario(usuario);

            // Crear una nueva ventana para la edición del perfil
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void onRetirar(ActionEvent event) {
        transaccionRetirar();

    }

    private void transaccionRetirar() {
        try {
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteradigitalapp/Retirar.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista y pasarle la cuenta actual
            RetirarViewController retirarController = loader.getController();

            retirarController.setCuenta(cuenta);

            // Crear una nueva ventana para el retiro
            Stage stage = new Stage();
            stage.setTitle("Retirar Dinero");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onDepositar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        controller = new VistaUsuario_Controller();
//        initLabel();

    }

    private void initView() {
        initDataBinding();
        obtenerTransaccionesCuenta();
        transaccionesTableView.setItems(transaccions);
        listenerSelection();
    }

    private void listenerSelection() {
        transaccionesTableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            transaccionSeleccionada = newSelection;
                 });
    }




    private void initDataBinding() {
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcionTransaccion()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));
        colMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
    }

    public void initLabel() {
        labelMount.setText(String.valueOf(cuenta.getSaldo()));
        labelUser.setText(usuario.getNombre());

    }

    public void setUsuario(String correo) {
        usuario = controller.getUsuario(correo);
        for (Cuenta cuenta1: usuario.getCuentasAsociadas()){
            if (cuenta1.getNombreBanco().equals("WalletLink")){
                cuenta = cuenta1;
            }
        }

        initLabel();
        initView();
    }

    public void obtenerTransaccionesCuenta(){
        transaccions.clear();
        transaccions.addAll(controller.getTransacciones(cuenta));
        transaccionesTableView.setItems(transaccions);
        transaccionesTableView.refresh();
    }

    public void actualizarSaldo(double saldo) {
        labelMount.setText(String.valueOf(saldo));

    }
    public void actualizarLabel(Double monto) {
        labelMount.setText(String.valueOf(monto));
    }

    public void onRedirigirNequi(ActionEvent actionEvent) {
        try {
            // Cargar el nuevo FXML (debes reemplazar "NequiView.fxml" con la ruta correcta)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteradigitalapp/NequiView.fxml"));
            AnchorPane newRoot = loader.load();

            // Crear una nueva escena con el nuevo FXML
            Scene newScene = new Scene(newRoot);

            // Obtener el escenario actual y cambiar la escena
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(newScene);

            // Mostrar la nueva escena
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
