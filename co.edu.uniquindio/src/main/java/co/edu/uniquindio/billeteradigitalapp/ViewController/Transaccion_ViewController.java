package co.edu.uniquindio.billeteradigitalapp.ViewController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteradigitalapp.Controller.CrudTransaccionController;
import co.edu.uniquindio.billeteradigitalapp.Controller.Presupuesto_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;
import co.edu.uniquindio.billeteradigitalapp.Model.Presupuesto;
import co.edu.uniquindio.billeteradigitalapp.Model.Transaccion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Transaccion_ViewController {

    Transaccion transaccionSeleccionado;
    CrudTransaccionController transaccionController;
    ObservableList<Transaccion> transaccionList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> CBTipoTransaccion;

    @FXML
    private TableView<Transaccion> TableGestionTransacciones;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnDetalles;

    @FXML
    private Button btnFiltrar;

    @FXML
    private TextField txtFiltro;



    @FXML
    private TableColumn<Transaccion, String> colDescripcion;

    @FXML
    private TableColumn<Transaccion, String> colFecha;

    @FXML
    private TableColumn<Transaccion, String> colMonto;

    @FXML
    private TableColumn<Transaccion, String> colTipo;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtMonto;

    @FXML
    void OnbtnLimpiarCampos(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        CBTipoTransaccion.setValue(null);
        txtMonto.clear();
        txtFecha.clear();
        txtDescripcion.clear();
    }

    @FXML
    void OnbtnCrear(ActionEvent event) {
        crearTransacciones();
    }

    @FXML
    void initialize() {
        transaccionController = new CrudTransaccionController();
        initView();

        // Listener para restaurar la tabla al borrar el texto del filtro
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                TableGestionTransacciones.setItems(transaccionList); // Restaurar lista original
            }
        });
    }

    private void initView() {
        obtenerTransacciones();
        agregarTipoTransaccion();
        TableGestionTransacciones.setItems(transaccionList);
        initDataBinding();
        listenerSelection();
    }

    private void listenerSelection() {
        TableGestionTransacciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            transaccionSeleccionado = newSelection;
            mostrarInformacionTransaccion(transaccionSeleccionado);
        });
    }

    private void mostrarInformacionTransaccion(Transaccion transaccionSeleccionado) {
        if (transaccionSeleccionado != null) {
            CBTipoTransaccion.setValue(transaccionSeleccionado.getTipoTransaccion());
            txtMonto.setText(Double.toString(transaccionSeleccionado.getMonto()));
            txtDescripcion.setText(transaccionSeleccionado.getDescripcionTransaccion());
            txtFecha.setText(transaccionSeleccionado.getFecha());

        }
    }

    private void initDataBinding() {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTransaccion()));
        colMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDescripcionTransaccion())));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));

    }

    private void agregarTipoTransaccion() {
        ObservableList<String> tipoTransaccionNombres = FXCollections.observableArrayList();
        List<Transaccion> transaccions = transaccionController.obtenerTipoTransaccion();
        for (int i = 0; i < transaccions.size(); i++) {
            Transaccion transaccion = transaccions.get(i);
            tipoTransaccionNombres.add(transaccion.getTipoTransaccion());
        }
        CBTipoTransaccion.setItems(tipoTransaccionNombres);
    }

    private void crearTransacciones() {
        if (validarFormulario()) {
            Transaccion transaccion = construirDatosTransacciones();
            if (transaccion != null) {
                if (transaccionController.crearTransacciones(transaccion)) {
                    transaccionList.add(transaccion);
                    mostrarMensaje("Información Transacción", "Transacción Creada", "La transacción se creó correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposTransaccion();
                } else {
                    mostrarMensaje("Información Transacción", "Transacción No Creada", "Hubo un problema al crear la transacción", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Información Transacción", "Datos Inválidos", "Por favor ingrese datos válidos para la transacción", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Transacción", "Campos Vacíos", "Por favor complete todos los campos obligatorios", Alert.AlertType.ERROR);
        }

    }

    private void limpiarCamposTransaccion() {
        txtMonto.clear();
        CBTipoTransaccion.setValue(null);
        txtDescripcion.clear();
        txtFecha.clear();
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.show();
    }

    private Transaccion construirDatosTransacciones() {
        Transaccion transaccion = new Transaccion();
        transaccion.setTipoTransaccion(CBTipoTransaccion.getValue());
        transaccion.setMonto(Double.parseDouble(txtMonto.getText()));
        transaccion.setDescripcionTransaccion(txtDescripcion.getText());
        transaccion.setFecha(txtFecha.getText());


        return transaccion;
    }

    private void obtenerTransacciones() {
        transaccionList.addAll(transaccionController.obtenerTransacciones());
    }

    private boolean validarFormulario() {
        if (txtDescripcion.getText().isEmpty() || txtFecha.getText().isEmpty() || CBTipoTransaccion == null || txtMonto.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    @FXML
    void OnbtnDetalles(ActionEvent event) {
        if (transaccionSeleccionado != null) {
            try {
                // Cargar la nueva vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteradigitalapp/DetallesTransaccion.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la nueva vista
                DetallesTransaccion_ViewController controller = loader.getController();
                controller.setTransaccion(transaccionSeleccionado); // Pasar los datos de la transacción

                // Crear una nueva ventana para mostrar los detalles
                Stage stage = new Stage();
                stage.setTitle("Detalles de la Transacción");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                mostrarMensaje("Error", "Error al cargar los detalles", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información", "Selecciona una transacción", "Por favor selecciona una transacción para ver los detalles.", Alert.AlertType.WARNING);
        }

    }

    @FXML
    void OnbtnFiltrar(ActionEvent event) {
        String filtro = txtFiltro.getText().toLowerCase();

        // Si el filtro está vacío, restaurar la lista original
        if (filtro.isEmpty()) {
            TableGestionTransacciones.setItems(transaccionList);
            mostrarMensaje("Información Filtrado", "Lista Restaurada", "Se ha restaurado la lista completa de transacciones.", Alert.AlertType.INFORMATION);
            return;
        }

        // Crear una lista para las transacciones filtradas
        ObservableList<Transaccion> transaccionesFiltradas = FXCollections.observableArrayList();
        for (Transaccion transaccion : transaccionList) {
            if (transaccion.getTipoTransaccion().toLowerCase().contains(filtro) ||
                    transaccion.getDescripcionTransaccion().toLowerCase().contains(filtro) ||
                    transaccion.getFecha().toLowerCase().contains(filtro) ||
                    String.valueOf(transaccion.getMonto()).contains(filtro)) {
                transaccionesFiltradas.add(transaccion);
            }
        }

        // Actualizar la tabla con las transacciones filtradas
        if (!transaccionesFiltradas.isEmpty()) {
            TableGestionTransacciones.setItems(transaccionesFiltradas);
        } else {
            mostrarMensaje("Información Filtrado", "Sin Resultados", "No se encontraron transacciones que coincidan con el criterio.", Alert.AlertType.INFORMATION);
            TableGestionTransacciones.setItems(FXCollections.observableArrayList()); // Tabla vacía si no hay coincidencias
        }
    }



}
