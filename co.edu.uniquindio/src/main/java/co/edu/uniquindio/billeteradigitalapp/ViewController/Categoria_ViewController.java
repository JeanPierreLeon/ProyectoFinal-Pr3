package co.edu.uniquindio.billeteradigitalapp.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteradigitalapp.Controller.CrudCategoria_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Categoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Categoria_ViewController {

    Categoria categoriaSeleccionada;
    CrudCategoria_Controller crudCategoriaController;
    ObservableList<Categoria> categoriasList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Categoria> TableViewCategorias;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Categoria, String> tcDescripcion;

    @FXML
    private TableColumn<Categoria, String> tcIdCategoria;

    @FXML
    private TableColumn<Categoria, String> tcNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtIdCategoria;

    @FXML
    private TextField txtNombre;

    @FXML
    void onActualizarCategoria(ActionEvent event) {
        actualizarCategoria();

    }

    @FXML
    void onCrearCategoria(ActionEvent event) {
        crearCategoria();

    }

    @FXML
    void onEliminarCategoria(ActionEvent event) {
        eliminarCategoria(txtIdCategoria.getText());

    }

    @FXML
    void initialize() {
        crudCategoriaController = new CrudCategoria_Controller();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCategoria();
        TableViewCategorias.setItems(categoriasList);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDescripcion())));
        tcIdCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdCategoria())));
    }

    private void obtenerCategoria() {
        categoriasList.clear();
        categoriasList.addAll(crudCategoriaController.obtenerCategorias());
        TableViewCategorias.setItems(categoriasList);
        TableViewCategorias.refresh();
    }

    private void actualizarCategoria() {
        if (categoriaSeleccionada != null) {
            if (validarFormulario()) {
                if(txtIdCategoria.getText().equals(categoriaSeleccionada.getIdCategoria())){
                    Categoria categoria = construirDatosCategoria();
                    if(crudCategoriaController.actualizarCategoria(categoria)){
                        mostrarMensaje("Información Categoria", "Categoria Actualizada", "La Categoria se actualizo correctamente.", Alert.AlertType.INFORMATION);
                        categoriaSeleccionada.setNombre(txtNombre.getText());
                       categoriaSeleccionada.setIdCategoria(txtIdCategoria.getText());
                        categoriaSeleccionada.setDescripcion(txtDescripcion.getText());
                        limpiarCamposCategoria();
                        TableViewCategorias.refresh();
                    }else {
                        mostrarMensaje("Información Categoria", "Problema Al Actualizar", "Ocurrio un problema al intentar actualizar la categoria.", Alert.AlertType.ERROR);
                    }
                }else {
                    mostrarMensaje("Información Categoria", "Dato No Modificable", "el ID de la categoria  no se puede modificar.", Alert.AlertType.ERROR);
                }
            }else {
                mostrarMensaje("Información Categoria", "Campos Vacíos", "Porfavor rellene los campos.", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Categoria", "Seleccione una categoria", "Porfavor seleccione la categoria que desea actualizar.", Alert.AlertType.ERROR);
        }
    }


    private void crearCategoria() {
        if (validarFormulario()) {
            Categoria categoria = construirDatosCategoria();
            if (categoria != null) {
                if (crudCategoriaController.crearCategoria(categoria)) {
                    categoriasList.add(categoria);
                    mostrarMensaje("Información Categoria", "Categoria Creada", "La Categoria se creó correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposCategoria();
                } else {
                    mostrarMensaje("Información Categoria", "Categoria No Creada", "Hubo un problema al crear la Categoria", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Información Categoria", "Datos Inválidos", "Por favor ingrese datos válidos para la categoria", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Usuario", "Campos Vacíos", "Por favor complete todos los campos obligatorios", Alert.AlertType.ERROR);
        }

    }

    private Categoria construirDatosCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre(txtNombre.getText());
        categoria.setIdCategoria(txtIdCategoria.getText());
        categoria.setDescripcion(txtDescripcion.getText());

        return categoria;
    }

    private boolean validarFormulario() {
        if(txtNombre.getText().isEmpty()||txtIdCategoria.getText().isEmpty()||txtDescripcion.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private void eliminarCategoria(String idCategoria) {
        if(categoriaSeleccionada!=null){
            if (crudCategoriaController.eliminarCategoria(idCategoria)) {
                categoriasList.remove(categoriaSeleccionada);
                limpiarCamposCategoria();
                mostrarMensaje("Información Categoria", "Categoria Eliminada", "La Categoria se ha eliminado correctamente", Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje("Información Categoria", "Categoria No Eliminada", "La Categoria no se  ha eliminado", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Categoria", "Seleccione una categoria", "Porfavor seleccione una categoria para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposCategoria() {
        txtNombre.clear();
        txtIdCategoria.clear();
        txtDescripcion.clear();
    }


    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.show();
    }

    private void listenerSelection() {
        TableViewCategorias.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            categoriaSeleccionada = newSelection;
            mostrarInformacionCategoria();
        });
    }

    private void mostrarInformacionCategoria() {
        if(categoriaSeleccionada!=null){
            txtNombre.setText(categoriaSeleccionada.getNombre());
            txtIdCategoria.setText(categoriaSeleccionada.getIdCategoria());
            txtDescripcion.setText(categoriaSeleccionada.getDescripcion());

        }
    }
}

