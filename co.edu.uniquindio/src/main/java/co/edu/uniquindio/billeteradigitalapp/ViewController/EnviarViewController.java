package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.CrudTransaccionController;
import co.edu.uniquindio.billeteradigitalapp.Controller.Cuenta_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Cuenta;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;
import co.edu.uniquindio.billeteradigitalapp.rabbit.producer.controller.ModelFactoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

import static co.edu.uniquindio.billeteradigitalapp.rabbit.producer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class EnviarViewController {

    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    Cuenta_Controller cuentaController;
    CrudTransaccionController crudTransaccionController;
    ObservableList<Cuenta> cuentasExistentes = FXCollections.observableArrayList();
    Cuenta cuentaUsuario;
    VistaUsuario_ViewController vistaUsuarioController;  // Agregar referencia al controlador principal

    @FXML
    private TextField txtBanco;

    @FXML
    private TextField txtCuenta;

    @FXML
    private TextField txtMonto;

    // Método para pasar la referencia del controlador principal
    public void setVistaUsuarioController(VistaUsuario_ViewController vistaUsuarioController) {
        this.vistaUsuarioController = vistaUsuarioController;
    }

    @FXML
    void onConfirmarTransferencia(ActionEvent event) {
        enviarDataRabbit();
    }

    private void enviarDataRabbit() {
        if (datosValidos()) {
            String mensaje = "";
            mensaje += "CREAR_TRANSFERENCIA;";
            mensaje += txtBanco.getText();
            mensaje += "#" + txtMonto.getText();
            mensaje += "#" + txtCuenta.getText();
            modelFactoryController.producirMensaje(QUEUE_NUEVA_PUBLICACION, mensaje);
            crearTransaccion();
            actualizarUsuario(Double.parseDouble(txtMonto.getText()));
            actualizarDestinatario(Double.parseDouble(txtMonto.getText()));

            // Después de la transferencia, actualizamos la vista principal con el nuevo saldo
            if (vistaUsuarioController != null) {
                vistaUsuarioController.actualizarSaldo(cuentaUsuario.getSaldo());
                vistaUsuarioController.obtenerTransaccionesCuenta();  // Refresca las transacciones
            }
        } else {
            System.out.println("No se pudo enviar transferencia");
        }
    }

    private void actualizarDestinatario(double v) {
        Cuenta cuentaDestino = obtenerCuentaDestino();
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + v);
        cuentaController.actualizarCuenta(cuentaDestino);
    }

    private void crearTransaccion() {
        crudTransaccionController.crearTransacciones(construirDatosTransacciones());
    }

    private Transaccion construirDatosTransacciones() {
        Transaccion transaccion = new Transaccion();
        transaccion.setTipoTransaccion("Deposito");
        transaccion.setMonto(Double.parseDouble(txtMonto.getText()));
        transaccion.setDescripcionTransaccion("Envio Dinero");
        transaccion.setFecha(String.valueOf(LocalDate.now()));
        transaccion.setCuentaOrigen(cuentaUsuario);
        transaccion.setCuentaDestino(obtenerCuentaDestino());
        return transaccion;
    }

    private Cuenta obtenerCuentaDestino() {
        for (Cuenta cuenta : cuentasExistentes) {
            if (cuenta.getNumeroCuenta() == Integer.parseInt(txtCuenta.getText())) {
                return cuenta;
            }
        }
        return null;
    }

    private void actualizarUsuario(double v) {
        cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - v);
        cuentaController.actualizarCuenta(cuentaUsuario);
    }

    private boolean datosValidos() {
        return !camposVacios() && correctosDatosCuenta();
    }

    private boolean correctosDatosCuenta() {
        int datosBuenos = 0;
        if (Double.parseDouble(txtMonto.getText()) < cuentaUsuario.getSaldo()) {
            datosBuenos++;
        }
        for (Cuenta cuenta : cuentasExistentes) {
            if (cuenta.getNumeroCuenta() == Integer.parseInt(txtCuenta.getText())) {
                datosBuenos++;
                break;
            }
        }
        return datosBuenos == 2;
    }

    private boolean camposVacios() {
        return txtBanco.getText().trim().isEmpty() || txtCuenta.getText().trim().isEmpty();
    }

    public void setCuentaUsuario(Cuenta cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    @FXML
    void initialize() {
        crudTransaccionController = new CrudTransaccionController();
        cuentaController = new Cuenta_Controller();
        obtenerCuentas();
    }

    private void obtenerCuentas() {
        cuentasExistentes.addAll(cuentaController.obtenerCuentas());
    }
}
