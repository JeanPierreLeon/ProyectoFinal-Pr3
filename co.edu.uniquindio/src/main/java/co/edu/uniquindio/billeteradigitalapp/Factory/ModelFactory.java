package co.edu.uniquindio.billeteradigitalapp.Factory;

import co.edu.uniquindio.billeteradigitalapp.Exceptions.*;
import co.edu.uniquindio.billeteradigitalapp.Model.*;
import co.edu.uniquindio.billeteradigitalapp.Utils.BilleteraUtils;
import co.edu.uniquindio.billeteradigitalapp.Utils.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelFactory {

    private static ModelFactory instance;
    BilleteraDigital billeteraDigital;

    public int validation(String user, String password) {
        return Persistencia.validation(user,password);
    }

    public Usuario getUsuarioPorCorreo(String correo) {
        return billeteraDigital.getUsuarioPorCorreo(correo);
    }

    public ArrayList<Transaccion> getTransacciones(Cuenta cuenta) {
        return billeteraDigital.getTransaccionesCuenta(cuenta);
    }

    public Cuenta obtenerCuenta(String s) {
        return billeteraDigital.obtenerCuenta(s);
    }


    private static class SingletonHolder {
        private final static ModelFactory eINSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    private ModelFactory() {
        billeteraDigital = new BilleteraDigital();
                cargarRecursosXML();
//              inicializarDatosBase();
//                salvarDatosBase();
//              guardarRecursosBinario();
//              guardarRecursosXML();

//              cargarDatosDesdeArchivos();
//              cargarRecursosBinario();



        Persistencia.guardaRegistroLog("Inicio de sesión ", 1, "inicio Sesión");
    }

    private BilleteraDigital getBilleteraDigital() {
        return billeteraDigital;
    }

    //----------------------------MANEJO DE PERSISTENCIA----------------------------//

    private void inicializarDatosBase() {
        billeteraDigital = BilleteraUtils.inicializarDatos();
    }

    private void cargarDatosDesdeArchivos() {
        try {
            Persistencia.cargarDatosArchivos(billeteraDigital);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosBase() {
        try {
            Persistencia.guardarUsuarios(billeteraDigital.getUsuarios());
            Persistencia.guardarCategorias(billeteraDigital.getCategorias());
            Persistencia.guardarCuentas(billeteraDigital.getCuentas());
            Persistencia.guardarPresupuestos(billeteraDigital.getPresupuestos());
            Persistencia.guardarTransacciones(billeteraDigital.getTransacciones());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void guardarRecursosBinario() {
        Persistencia.guardarRecursoBancoBinario(billeteraDigital);
    }

    private void cargarRecursosBinario() {
        billeteraDigital = Persistencia.cargarRecursoBancoBinario();
    }

    private void guardarRecursosXML() {
        Persistencia.guardarRecursoBancoXML(getBilleteraDigital());
    }

    private void cargarRecursosXML() {
        billeteraDigital = Persistencia.cargarRecursoBancoXML();
    }

    //----------------------------MANEJO DE PERSISTENCIA----------------------------//

    //----------------------------GESTION ACCESO----------------------------//

    public boolean iniciarSesion(String user, String password) {
        boolean respuesta = false;
        try {
            respuesta = Persistencia.iniciarSesion(user, password);
            Persistencia.guardaRegistroLog("Inicio Sesion: " + user, 1, "Inicio De Sesion");

            return respuesta;
        } catch (LoginException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Iniciar Sesion");
            return respuesta;
        }
    }

    public boolean registrarUsuario(String user, String password) {
        boolean respuesta = false;
        try {
            respuesta = Persistencia.registroUsuario(user, password);
            Persistencia.guardaRegistroLog("Registro De Usuario: " + user, 1, "Registro");
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            return respuesta;
        } catch (LoginException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Registro usuario");
            return respuesta;
        }

    }

    //----------------------------GESTION ACCESO----------------------------//

    //----------------------------GESTION USUARIO----------------------------//

    public List<Usuario> obtenerUsuarios() {
        return billeteraDigital.getUsuarios();
    }
    public List<Transaccion> obtenerTransacciones() {
        return billeteraDigital.getTransacciones();
    }

    public boolean crearUsuario(Usuario usuario) {
        boolean creacion = false;
        try {
            creacion = billeteraDigital.crearUsuario(usuario);
            Persistencia.guardaRegistroLog("Se creo el usuario: " + usuario.getIdUsuario(), 1, "Crear Usuario");
            Persistencia.guardarUsuarios(billeteraDigital.getUsuarios());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return creacion;
        } catch (UsuarioException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Crear Usuario");
            return creacion;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarUsuario(String cedula) {
        boolean eliminado;
        try {
            eliminado = billeteraDigital.eliminarUsuario(cedula);
            Persistencia.guardaRegistroLog("Se elimino el usuario: " + cedula, 1, "Eliminar Usuario");
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarUsuarios(getBilleteraDigital().getUsuarios());
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
        } catch (UsuarioException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Eliminar Usuario");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return eliminado;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        boolean actualizado;
        try {
            actualizado = billeteraDigital.actualizarUsuario(usuario);
            Persistencia.guardaRegistroLog("Se actualizo el usuario: " + usuario.getIdUsuario(), 1, "Actualiza Usuario");
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarUsuarios(billeteraDigital.getUsuarios());
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
        } catch (UsuarioException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Actualizar Usuario");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualizado;
    }

    //----------------------------GESTION USUARIO----------------------------//

    //----------------------------GESTION CUENTA----------------------------//

    public List<Cuenta> obtenerCuentas() {
        return billeteraDigital.getCuentas();
    }

    public boolean crearCuenta(Cuenta cuenta) {
        boolean crear = false;
        try {
            crear = billeteraDigital.crearCuenta(cuenta);
            Persistencia.guardaRegistroLog("Se creo la cuenta: " + cuenta.getIdCuenta(), 1, "Crear Cuenta");
            Persistencia.guardarCuentas(billeteraDigital.getCuentas());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return crear;
        } catch (CuentaException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Crear Cuenta");
            return crear;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarCuenta(Cuenta cuenta) {
        boolean actualizado = false;
        try {
            actualizado = billeteraDigital.actualizarCuenta(cuenta);
            Persistencia.guardaRegistroLog("Se actualizo la cuenta: " + cuenta.getIdCuenta(), 1, "Actualizar Cuenta");
            Persistencia.guardarCuentas(billeteraDigital.getCuentas());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return actualizado;
        } catch (CuentaException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Actualizar Cuenta");
            return actualizado;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarCuenta(String idCuenta) {
        boolean eliminado = false;
        try {
            eliminado = billeteraDigital.eliminarCuenta(idCuenta);
            Persistencia.guardaRegistroLog("Se Elimino la cuenta: " + idCuenta, 1, "Eliminar Cuenta");
            Persistencia.guardarCuentas(billeteraDigital.getCuentas());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
        } catch (CuentaException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Eliminar Cuenta");
            return eliminado;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return eliminado;
    }

    //----------------------------GESTION CUENTA----------------------------//

    //----------------------------GESTION PRESUPUESTO----------------------------//

    public List<Presupuesto> obtenerPresupuestos() {
        return billeteraDigital.getPresupuestos();
    }

    public boolean crearPresupuesto(Presupuesto presupuesto) {
        boolean crear = false;
        try {
            crear = billeteraDigital.crearPresupuesto(presupuesto);
            Persistencia.guardaRegistroLog("Se creo el presupuesto: " + presupuesto.getIdPresupuesto(), 1, "Crear presupuesto");
            Persistencia.guardarPresupuestos(billeteraDigital.getPresupuestos());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return crear;
        } catch (PresupuestoException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Crear Presupuesto");
            return crear;
        }
    }

    public boolean crearTransaccion(Transaccion transaccion) {
        boolean crear = false;
        try {
            crear = billeteraDigital.crearTransaccion(transaccion);
            Persistencia.guardaRegistroLog("Se creo el presupuesto: " + transaccion.getTipoTransaccion(), 1, "Crear presupuesto");
            Persistencia.guardarTransacciones(billeteraDigital.getTransacciones());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return crear;
        } catch (TransaccionException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Crear Presupuesto");
            return crear;
        }
    }

    public boolean actualizarPresupuesto(Presupuesto presupuesto) {
        boolean actualizar = false;
        try {
            actualizar = billeteraDigital.actualizarPresupuesto(presupuesto);
            Persistencia.guardaRegistroLog("Se actualizo el presupuesto" + presupuesto.getIdPresupuesto(), 1, "Actualizar Presupuesto");
            Persistencia.guardarPresupuestos(billeteraDigital.getPresupuestos());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return actualizar;
        } catch (PresupuestoException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Actualizar Presupuesto");
            return actualizar;
        }

    }

    public boolean eliminarPresupuesto(String idPresupuesto) {
        boolean eliminar = false;
        try {
            eliminar = billeteraDigital.eliminarPresupuesto(idPresupuesto);
            Persistencia.guardaRegistroLog("Se elimino el presupuesto: " + idPresupuesto, 1, "Eliminar Presupuesto");
            Persistencia.guardarPresupuestos(billeteraDigital.getPresupuestos());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return eliminar;
        } catch (PresupuestoException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Eliminar Presupuesto");
            return eliminar;
        }
    }

    public Categoria encontarCategoria(String nombreCategoria) {
        return billeteraDigital.obtenerCategoria(nombreCategoria);
    }


    //----------------------------GESTION PRESUPUESTO----------------------------//

    //----------------------------GESTION CATEGORIA----------------------------//

    public boolean crearCategoria(Categoria categoria) {
        boolean creacion = false;
        try {
            creacion = billeteraDigital.crearCategoria(categoria);
            Persistencia.guardaRegistroLog("Se creo la categoria: " + categoria.getIdCategoria(), 1, "Crear Categoria");
            Persistencia.guardarCategorias(billeteraDigital.getCategorias());
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
            return creacion;
        } catch (CategoriaException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Crear Categoria");
            return creacion;
        }
    }

    public List<Categoria> obtenerCategorias() {
        return billeteraDigital.getCategorias();
    }

    public boolean eliminarCategoria(String idCategoria) {
        boolean eliminado;
        try {
            eliminado = billeteraDigital.eliminarCategoria(idCategoria);
            Persistencia.guardaRegistroLog("Se elimino la categoria: " + idCategoria, 1, "Eliminar Categoria");
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarCategorias(getBilleteraDigital().getCategorias());
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
        } catch (CategoriaException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Eliminar Categoria");
            return false;
        }
        return eliminado;
    }

    public boolean actualizarCategoria(Categoria categoria) {

        boolean actualizado;
        try {
            actualizado = billeteraDigital.actualizarCategoria(categoria);
            Persistencia.guardaRegistroLog("Se actualizo la categoria: " + categoria.getIdCategoria(), 1, "Actualiza Categoria");
            Persistencia.guardarRecursoBancoXML(billeteraDigital);
            Persistencia.guardarCategorias(billeteraDigital.getCategorias());
            Persistencia.guardarRecursoBancoBinario(billeteraDigital);
        } catch (CategoriaException | IOException e) {
            Persistencia.guardaRegistroLog(e.getMessage(), 2, "Actualizar Categoria");
            return false;
        }
        return actualizado;
    }

    //----------------------------GESTION CATEGORIA----------------------------//
}