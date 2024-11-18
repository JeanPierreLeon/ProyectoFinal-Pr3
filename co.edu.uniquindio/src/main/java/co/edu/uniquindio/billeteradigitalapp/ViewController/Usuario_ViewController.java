package co.edu.uniquindio.billeteradigitalapp.ViewController;

import co.edu.uniquindio.billeteradigitalapp.Controller.CrudUsuario_Controller;
import co.edu.uniquindio.billeteradigitalapp.Model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Usuario_ViewController {
    Usuario usuarioSeleccionado;
    CrudUsuario_Controller crudUsuarioController;
    ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Usuario> TableViewUsuarios;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Usuario, String> tcCedula;

    @FXML
    private TableColumn<Usuario, String> tcCorreo;

    @FXML
    private TableColumn<Usuario, String> tcDireccion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TableColumn<Usuario, String> tcTelefono;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void onCrear(ActionEvent event) {
        crearUsuario();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarUsuario(txtCedula.getText());

    }

    @FXML
    void initialize() {
        crudUsuarioController = new CrudUsuario_Controller();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        TableViewUsuarios.setItems(usuariosList);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        usuariosList.clear();
        usuariosList.addAll(crudUsuarioController.obtenerUsuarios());
        TableViewUsuarios.setItems(usuariosList);
        TableViewUsuarios.refresh();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCorreo())));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTelefono())));
        tcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDireccion())));
        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdUsuario())));
    }

    private void crearUsuario() {
        if (validarFormulario()) {
            Usuario usuario = construirDatosUsuario();
            if (usuario != null) {
                if (crudUsuarioController.crearUsuario(usuario)) {
                    usuariosList.add(usuario);
                    mostrarMensaje("Información Usuario", "Usuario Creado", "El cliente se creó correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuario();
                } else {
                    mostrarMensaje("Información Usuario", "Usuario No Creado", "Hubo un problema al crear el cliente", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Información Usuario", "Datos Inválidos", "Por favor ingrese datos válidos para el cliente", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Información Usuario", "Campos Vacíos", "Por favor complete todos los campos obligatorios", Alert.AlertType.ERROR);
        }
    }

    private void eliminarUsuario(String cedula) {
        if(usuarioSeleccionado!=null){
            if (crudUsuarioController.eliminarUsuario(cedula)) {
                usuariosList.remove(usuarioSeleccionado);
                limpiarCamposUsuario();
                mostrarMensaje("Información Usuario", "Usuario Eliminado", "El usuario se ha eliminado correctamente", Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje("Información Usuario", "Usuario No Eliminado", "El usuario no se  ha eliminado", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Usuario", "Seleccione un usuario", "Porfavor seleccione un usuario para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void actualizarUsuario() {
        if (usuarioSeleccionado != null) {
            if (validarFormulario()) {
                if(txtCedula.getText().equals(usuarioSeleccionado.getIdUsuario())){
                    Usuario usuario = construirDatosUsuario();
                    if(crudUsuarioController.actualizarUsuario(usuario)){
                        mostrarMensaje("Información Usuario", "Usuario Actualizado", "El usuario se actualizo correctamente.", Alert.AlertType.INFORMATION);
                        usuarioSeleccionado.setDireccion(txtDireccion.getText());
                        usuarioSeleccionado.setTelefono(txtTelefono.getText());
                        usuarioSeleccionado.setNombre(txtNombre.getText());
                        usuarioSeleccionado.setCorreo(txtCorreo.getText());
                        limpiarCamposUsuario();
                        TableViewUsuarios.refresh();
                    }else {
                        mostrarMensaje("Información Usuario", "Problema Al Actualizar", "Ocurrio un problema al intentar actualizar el usuario.", Alert.AlertType.ERROR);
                    }
                }else {
                    mostrarMensaje("Información Usuario", "Dato No Modificable", "La cedula del usuario no se puede modificar.", Alert.AlertType.ERROR);
                }
            }else {
                mostrarMensaje("Información Usuario", "Campos Vacíos", "Porfavor rellene los campos.", Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("Información Usuario", "Seleccione un usuario", "Porfavor seleccione el usuario que desea actualizar.", Alert.AlertType.ERROR);
        }
    }

    private Usuario construirDatosUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre(txtNombre.getText());
        usuario.setCorreo(txtCorreo.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setDireccion(txtDireccion.getText());
        usuario.setIdUsuario(txtCedula.getText());

        return usuario;
    }

    private boolean validarFormulario() {
        if(txtNombre.getText().isEmpty()||txtCedula.getText().isEmpty()||txtCorreo.getText().isEmpty()||txtDireccion.getText().isEmpty()||txtTelefono.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private void limpiarCamposUsuario() {
        txtCedula.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtNombre.clear();
        txtTelefono.clear();
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.show();
    }

    private void listenerSelection() {
        TableViewUsuarios.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if(usuarioSeleccionado!=null){
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtCorreo.setText(usuarioSeleccionado.getCorreo());
            txtCedula.setText(usuarioSeleccionado.getIdUsuario());
            txtDireccion.setText(usuarioSeleccionado.getDireccion());
            txtTelefono.setText(String.valueOf(usuarioSeleccionado.getTelefono()));
        }
    }

}