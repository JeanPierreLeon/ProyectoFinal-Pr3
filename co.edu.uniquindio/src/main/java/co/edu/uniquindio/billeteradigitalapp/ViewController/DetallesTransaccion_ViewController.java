package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetallesTransaccion_ViewController {

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFecha;

    private Transaccion transaccion;

    // Método para recibir los datos de la transacción
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
        mostrarDetalles();
    }

    private void mostrarDetalles() {
        if (transaccion != null) {
            txtTipo.setText(transaccion.getTipoTransaccion());
            txtMonto.setText(String.valueOf(transaccion.getMonto()));
            txtDescripcion.setText(transaccion.getDescripcionTransaccion());
            txtFecha.setText(transaccion.getFecha());
        }
    }

    @FXML
    void OnbtnCerrar() {
        Stage stage = (Stage) txtTipo.getScene().getWindow();
        stage.close();
    }
}
