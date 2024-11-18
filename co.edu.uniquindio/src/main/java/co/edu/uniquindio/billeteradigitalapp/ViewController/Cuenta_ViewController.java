package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.Cuenta_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Cuenta_ViewController {
    Cuenta_Controller cuentaController;
    Cuenta cuentaSelecionada;
    ObservableList<Cuenta> cuentasList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Cuenta> tableGestionCuenta;

    @FXML
    private TableColumn<Cuenta,String> tcIdCuenta;

    @FXML
    private TableColumn<Cuenta,String> tcNombreBanco;

    @FXML
    private TableColumn<Cuenta,String> tcNumeroCuenta;

    @FXML
    private TableColumn<Cuenta,String> tcTipoCuenta;

    @FXML
    private ComboBox<String> tipoCuentaCB;

    @FXML
    private ComboBox<String> CBNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    void initialize() {
        cuentaController = new Cuenta_Controller();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCuentas();
        iniciarComboBox();
        tableGestionCuenta.setItems(cuentasList);
        listenerSelection();
    }

    private void iniciarComboBox() {
        ObservableList<String> opcionesTipoCuenta = FXCollections.observableArrayList("Corriente", "Ahorro");
        tipoCuentaCB.setItems(opcionesTipoCuenta);

        ObservableList<String> opcionesNombreBanco = FXCollections.observableArrayList(
                "Bancolombia", "Daviplata", "Banco De Bogota", "BBVA"
        );
        CBNombreBanco.setItems(opcionesNombreBanco);
    }

    @FXML
    void OnbtnActualizar(ActionEvent event) {
        actualizarCueta();
    }

    @FXML
    void OnbtnCrear(ActionEvent event) {
        crearCuenta();

    }

    @FXML
    void OnbtnEliminar(ActionEvent event) {
        String id = String.valueOf(cuentaSelecionada.getIdCuenta());
        eliminarCuenta(id);
    }

    private void listenerSelection() {
        tableGestionCuenta.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            cuentaSelecionada= newSelection;
            mostrarInformacionCuenta();
        });
    }

    private void mostrarInformacionCuenta() {
        if(cuentaSelecionada!=null){
            CBNombreBanco.setValue(cuentaSelecionada.getNombreBanco());
            tipoCuentaCB.setValue(cuentaSelecionada.getTipoCuenta());
            txtNumeroCuenta.setText(String.valueOf(cuentaSelecionada.getNumeroCuenta()));
        }
    }

    private void obtenerCuentas() {
        cuentasList.addAll(cuentaController.obtenerCuentas());
    }

    private void initDataBinding() {
        tcIdCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdCuenta())));
        tcNumeroCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumeroCuenta())));
        tcTipoCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoCuenta()));
        tcNombreBanco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreBanco()));
    }

    private void crearCuenta() {
        if(validarDatos()){
            Cuenta cuenta= construirDatosCuenta();
            if(cuentaController.crearcuenta(cuenta)){
                cuentasList.add(cuenta);
                limpiarCamposCuenta();
                tableGestionCuenta.refresh();
                mostrarMensaje("Cuenta", "Cuenta agregada", "La cuenta se agrego correctamente", Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje("Error", "Error de Cuenta", "la cuenta ya Existe", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Error", "Error de Cuenta", "Ingrese todos los datos", Alert.AlertType.ERROR);
        }

    }

    private void actualizarCueta() {
        if(cuentaSelecionada != null) {
            if (validarDatos()){
                    Cuenta cuenta=construirDatosCuenta();
                    if(cuentaController.actualizarCuenta(cuenta)){
                        mostrarMensaje("Información Cuenta", "Cuenta Actualizada", "La cuenta se actualizo correctamente.", Alert.AlertType.INFORMATION);
                        cuentaSelecionada.setNumeroCuenta(Integer.parseInt(txtNumeroCuenta.getText()));
                        cuentaSelecionada.setTipoCuenta(tipoCuentaCB.getValue());
                        cuentaSelecionada.setNombreBanco(CBNombreBanco.getValue());
                        limpiarCamposCuenta();
                        tableGestionCuenta.refresh();
                    }else {
                        mostrarMensaje("Información Cuenta", "Problema Al Actualizar", "Ocurrio un problema al intentar actualizar la cuenta.", Alert.AlertType.ERROR);
                    }
            }else {
                mostrarMensaje("Información Cuenta", "Campos Vacíos", "Porfavor rellene los campos.", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Cuenta", "Seleccione una cuenta", "Porfavor seleccione la cuenta que desea actualizar.", Alert.AlertType.ERROR);
        }
    }

    private void eliminarCuenta(String idCuenta) {
        if(cuentaSelecionada!=null){
            if(cuentaController.eliminarCuenta(idCuenta)){
                cuentasList.remove(cuentaSelecionada);
                limpiarCamposCuenta();
                mostrarMensaje("Información Cuenta", "Cuenta Eliminada", "La cuenta se ha eliminado correctamente", Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje("Información Cuenta", "Cuenta No Eliminada", "La cuenta no se  ha eliminado", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Cuenta", "Seleccione una cuenta", "Porfavor seleccione una cuenta para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposCuenta() {
        CBNombreBanco.setValue(null);
        txtNumeroCuenta.clear();
        tipoCuentaCB.setValue(null);
    }

    private Cuenta construirDatosCuenta() {
        Cuenta cuenta=new Cuenta();
        cuenta.setNombreBanco(CBNombreBanco.getValue());
        cuenta.setNumeroCuenta(Integer.parseInt(txtNumeroCuenta.getText()));
        cuenta.setTipoCuenta(tipoCuentaCB.getValue());
        return cuenta;
    }

    private boolean validarDatos() {
        if(CBNombreBanco.getValue()==null||txtNumeroCuenta.getText().isEmpty()||tipoCuentaCB==null) {
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.show();
    }

}