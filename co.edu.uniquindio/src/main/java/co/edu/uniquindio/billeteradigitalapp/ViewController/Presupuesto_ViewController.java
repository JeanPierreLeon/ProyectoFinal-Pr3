package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.Presupuesto_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;
import co.edu.uniquindio.billeteradigitalapp.Model.Presupuesto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Presupuesto_ViewController {
    Presupuesto presupuestoSeleccionado;
    Presupuesto_Controller presupuestoController;
    ObservableList<Presupuesto> presupuestosList = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> CBcategoria;

    @FXML
    private TableView<Presupuesto> TableGestionPresupuesto;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Presupuesto, String> tcIdPresupuesto;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoAsignado;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoGastado;

    @FXML
    private TableColumn<Presupuesto, String> tcNombre;

    @FXML
    private TableColumn<Presupuesto, String> tcCategoria;

    @FXML
    private TableColumn<Presupuesto, String> tcNumeroPresupuesto;


    @FXML
    private TextField txtMontoAsignado;

    @FXML
    private TextField txtNombre;


    @FXML
    void OnbtnActualizar(ActionEvent event) {
        actualizarPresupuesto();
    }

    @FXML
    void OnbtnCrear(ActionEvent event) {
        crearPresupuesto();
    }

    @FXML
    void OnbtnEliminar(ActionEvent event) {
        String id = String.valueOf(presupuestoSeleccionado.getIdPresupuesto());
        eliminarPresupuesto(id);
    }

    @FXML
    void initialize() {
        presupuestoController = new Presupuesto_Controller();
        initView();
    }

    private void eliminarPresupuesto(String idPresupuesto) {
        if (presupuestoSeleccionado != null) {
            if (presupuestoController.eliminarPresupuesto(idPresupuesto)) {
                presupuestosList.remove(presupuestoSeleccionado);
                limpiarCamposPresupuesto();
                TableGestionPresupuesto.refresh();
                mostrarMensaje("Información Presupuesto", "Presupuesto Eliminado", "El Presupuesto se ha eliminado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Información Presupuesto", "Presupuesto No Eliminado", "El Presupuesto no se  ha eliminado", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Presupuesto", "Seleccione un Presupuesto", "Porfavor seleccione un Presupuesto para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void actualizarPresupuesto() {
        if (presupuestoSeleccionado != null) {
            if (validarFormulario()) {
                Presupuesto presupuesto = construirDatosPresupuesto();
                if (presupuesto != null) {
                    if (presupuestoController.actualizarPresupuesto(presupuestoSeleccionado)) {
                        mostrarMensaje("Información Presupuesto", "Presupuesto Actualizado", "El presupuesto se actualizo correctamente.", Alert.AlertType.INFORMATION);
                        presupuestoSeleccionado.setNombre(txtNombre.getText());
                        presupuestoSeleccionado.setMontoTotalAsignado(Double.parseDouble(txtMontoAsignado.getText()));
                        presupuestoSeleccionado.setCategoria(presupuestoController.encontrarCategoria(CBcategoria.getValue()));
                        TableGestionPresupuesto.refresh();
                    } else {
                        mostrarMensaje("Información Presupuesto", "Problema Al Actualizar", "Ocurrio un problema al intentar actualizar el Presupuesto.", Alert.AlertType.ERROR);
                    }
                } else {
                    mostrarMensaje("Información Presupuesto", "Problema Al Crear", "Ocurrio un problema al intentar actualizar el Presupuesto.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Información Presupuesto", "Campos Vacíos", "Porfavor rellene los campos.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Presupuesto", "Seleccione un Presupuesto", "Porfavor seleccione el Presupuesto que desea actualizar.", Alert.AlertType.ERROR);
        }
    }

    private void crearPresupuesto() {
        if (validarFormulario()) {
            Presupuesto presupuesto = construirDatosPresupuesto();
            if (presupuesto != null) {
                if (presupuestoController.crearPresupuesto(presupuesto)) {
                    presupuestosList.add(presupuesto);
                    mostrarMensaje("Información Presupuesto", "Presupuesto Creado", "El presupuesto se creó correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposPresupuesto();
                } else {
                    mostrarMensaje("Información Presupuesto", "Presupuesto No Creado", "Hubo un problema al crear el presupuesto", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Información Presupuesto", "Datos Inválidos", "Por favor ingrese datos válidos para el presupuesto", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Presupuesto", "Campos Vacíos", "Por favor complete todos los campos obligatorios", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposPresupuesto() {
        txtMontoAsignado.clear();
        CBcategoria.setValue(null);
        txtNombre.clear();

    }

    private Presupuesto construirDatosPresupuesto() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombre(txtNombre.getText());
        try {
            presupuesto.setMontoTotalAsignado(Double.parseDouble(txtMontoAsignado.getText()));
            presupuesto.setCategoria(presupuestoController.encontrarCategoria(CBcategoria.getValue()));
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingresa números válidos.");
            return null;
        }
        return presupuesto;
    }

    private boolean validarFormulario() {
        if (txtNombre.getText().isEmpty() || txtMontoAsignado.getText().isEmpty() || CBcategoria == null) {
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

    private void initView() {
        obtenerPresupuestos();
        agregarCategorias();
        TableGestionPresupuesto.setItems(presupuestosList);
        initDataBinding();
        listenerSelection();
    }

    private void agregarCategorias() {
        ObservableList<String> categoriasNombres = FXCollections.observableArrayList();
        List<Categoria> categorias = presupuestoController.obtenerCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoria = categorias.get(i);
            categoriasNombres.add(categoria.getNombre());
        }
        CBcategoria.setItems(categoriasNombres);
    }

    private void listenerSelection() {
        TableGestionPresupuesto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            presupuestoSeleccionado = newSelection;
            mostrarInformacionUsuario(presupuestoSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Presupuesto presupuestoSeleccionado) {
        if (presupuestoSeleccionado != null) {
            txtNombre.setText(presupuestoSeleccionado.getNombre());
            txtMontoAsignado.setText(Double.toString(presupuestoSeleccionado.getMontoTotalAsignado()));
            CBcategoria.setValue(presupuestoSeleccionado.getCategoria().getNombre());
        }
    }

    private void obtenerPresupuestos() {
        presupuestosList.addAll(presupuestoController.obtenerPresupuestos());
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdPresupuesto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdPresupuesto())));
        tcMontoAsignado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMontoTotalAsignado())));
        tcMontoGastado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMontoGastado())));
        tcCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoria().getNombre())));
    }
}
