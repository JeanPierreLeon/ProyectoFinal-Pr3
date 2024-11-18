package co.edu.uniquindio.billeteradigitalapp.Model;



import co.edu.uniquindio.billeteradigitalapp.Exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class BilleteraDigital implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Cuenta> cuentas = new ArrayList<>();
    private ArrayList<Transaccion> transacciones = new ArrayList<>();
    private ArrayList<Presupuesto> presupuestos = new ArrayList<>();
    private ArrayList<Categoria> categorias = new ArrayList<>();

    public BilleteraDigital() {
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public ArrayList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    //----------------------------GESTION USUARIO----------------------------//

    public boolean crearUsuario(Usuario usuario) throws UsuarioException {
        Usuario usuarioEncontrado = encontrarUsuario(usuario.getIdUsuario());
        if (usuarioEncontrado == null) {
            usuarios.add(usuario);
            return true;
        }else {
            throw new UsuarioException("Usuario con cedula: "+ usuario.getIdUsuario()+ " ya existe");
        }
    }

    private Usuario encontrarUsuario(String idUsuario) {
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario().equals(idUsuario)) {
                usuarioEncontrado = usuario;
                break;
            }else {
                usuarioEncontrado = null;
            }
        }
        return usuarioEncontrado;
    }

    public boolean eliminarUsuario(String cedula) throws UsuarioException {
        boolean eliminado = false;
        for(Usuario usuario:usuarios){
            if (usuario.getIdUsuario().equals(cedula)) {
                usuarios.remove(usuario);
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            throw new UsuarioException("Usuario con cedula: "+ cedula+ " no existe");
        }
        return eliminado;
    }

    public boolean actualizarUsuario(Usuario usuario) throws UsuarioException {
        boolean actualizado = false;
        for (Usuario usuario1 : usuarios) {
            if (usuario.getIdUsuario().equals(usuario1.getIdUsuario())) {
                usuarios.set(usuarios.indexOf(usuario1), usuario);
                actualizado = true;
                break;
            }
        }
        if(!actualizado){
                throw new UsuarioException("Usuario con cedula: "+ usuario.getIdUsuario()+ " no existe");
        }
        return actualizado;
    }

    //----------------------------GESTION USUARIO----------------------------//

    //----------------------------GESTION CUENTA----------------------------//

    public boolean crearCuenta(Cuenta cuenta) throws CuentaException {
        Cuenta cuentaEncontrado = encontrarCuenta(cuenta.getNumeroCuenta());
        if (cuentaEncontrado == null) {
            cuenta.setIdCuenta(encontrarIdCuenta()+1);
            cuentas.add(cuenta);
            return true;
        }else{
            throw new CuentaException("La cuenta con id: "+ cuenta.getIdCuenta()+ " ya existe");
        }
    }

    private int encontrarIdCuenta() {
        int idCuenta = 0;
        for (Cuenta cuenta : cuentas) {
            if(cuenta.getIdCuenta()>idCuenta){
                idCuenta = cuenta.getIdCuenta();
            }
        }
        return idCuenta;
    }

    private Cuenta encontrarCuenta(int numeroCuenta) {
        Cuenta cuentaEncontrada = null;
        for(Cuenta cuenta: cuentas){
            if (cuenta.getNumeroCuenta() == numeroCuenta) {
                cuentaEncontrada = cuenta;
                break;
            }else{
                cuentaEncontrada = null;
            }
        }return cuentaEncontrada;
    }

    public boolean actualizarCuenta(Cuenta cuenta) throws CuentaException{
        boolean actualizado = false;
        for (Cuenta cuenta1 : cuentas) {
            if(cuenta.getNumeroCuenta() == cuenta1.getNumeroCuenta()){
                cuentas.set(cuentas.indexOf(cuenta1), cuenta);
                actualizado = true;
                break;
            }
        }
        if (!actualizado){
            throw new CuentaException("Cuenta con id: "+ cuenta.getIdCuenta()+ " no existe");
        }
        return actualizado;
    }

    public boolean eliminarCuenta(String idCuenta) throws CuentaException {
        boolean eliminado = false;
        int idCuentaInt = Integer.parseInt(idCuenta);
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIdCuenta() == idCuentaInt) {
                cuentas.remove(cuenta);
                eliminado = true;
                break;
            }
        }
        if(!eliminado){
            throw new CuentaException("Cuenta con id: "+ idCuenta+ " no existe");
        }
        return eliminado;
    }

    //----------------------------GESTION CUENTA----------------------------//

    //----------------------------GESTION PRESUPUESTO----------------------------//

    private Transaccion encontrarTransaccion(String TipoTransaccion) {
        Transaccion transaccionEncontrado = null;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getTipoTransaccion().equals(TipoTransaccion)) {
                transaccionEncontrado = transaccion;
                break;
            }
        }
        return transaccionEncontrado;
    }

    private Presupuesto encontrarPresupuesto(String nombre) {
        Presupuesto presupuestoEncontrado = null;
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getNombre().equals(nombre)) {
                presupuestoEncontrado = presupuesto;
                break;
            }
        }
        return presupuestoEncontrado;
    }

    public boolean actualizarPresupuesto(Presupuesto presupuesto) throws PresupuestoException {
        boolean actualizado = false;
        for (Presupuesto presupuesto1 : presupuestos) {
            if (presupuesto.getIdPresupuesto()==(presupuesto1.getIdPresupuesto())) {
                presupuestos.set(presupuestos.indexOf(presupuesto1), presupuesto);
                actualizado = true;
                break;
            }
        }
        if(actualizado){
            return true;
        }else {
            throw new PresupuestoException("Categoria con Id:" + presupuesto.getIdPresupuesto() + "no encontrada.");
        }
    }

    public boolean eliminarPresupuesto(String idPresupuesto) throws PresupuestoException {
        boolean eliminado = false;
        int idPresupuestoInt = Integer.parseInt(idPresupuesto);
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getIdPresupuesto() == idPresupuestoInt) {
                presupuestos.remove(presupuesto);
                eliminado = true;
                break;
            }
        }
        if(eliminado){
            return true;
        }else {
            throw new PresupuestoException("Presupuesto con id: " + idPresupuesto + " inexistente");
        }
    }



    public int obtenerIdMaximoPresupuesto() {
        int idMaximoPresupuesto = 0;
        for (Presupuesto presupuesto: presupuestos){
            if(presupuesto.getIdPresupuesto() > idMaximoPresupuesto) {
                idMaximoPresupuesto = presupuesto.getIdPresupuesto();
            }
        }
        return idMaximoPresupuesto;
    }

    public int obtenerIdMaximoTransaccion() {
        int idMaximoTransaccion = 0;
        for (Transaccion transaccion: transacciones){
            if(transaccion.getIdTransaccion() > idMaximoTransaccion) {
                idMaximoTransaccion = transaccion.getIdTransaccion();
            }
        }
        return idMaximoTransaccion;
    }

    //----------------------------GESTION PRESUPUESTO----------------------------//

    //----------------------------GESTION CATEGORIA----------------------------//



    private Categoria encontrarCategoria(String idCategoria){
        Categoria categoriaEncontrada = null;
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria().equals(idCategoria)) {
                categoriaEncontrada = categoria;
                break;
            } else {
                categoriaEncontrada = null;
            }
        }
        return categoriaEncontrada;
    }

    public boolean eliminarCategoria(String idCategoria) throws CategoriaException {
        boolean eliminado = false;
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria().equals(idCategoria)) {
                categorias.remove(categoria);
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            throw new CategoriaException("Categoria con idCategoria: " + idCategoria + " no existe");
        }
        return eliminado;
    }

    public boolean actualizarCategoria(Categoria categoria) throws CategoriaException {
        boolean actualizado = false;
        for (Categoria categoria1 : categorias) {
            if (categoria.getIdCategoria().equals(categoria1.getIdCategoria())) {
                categorias.set(categorias.indexOf(categoria1), categoria);
                actualizado = true;
                break;
            }
        }
        if (!actualizado) {
            throw new CategoriaException("Categoria con idCategoria: " + categoria.getIdCategoria() + " no existe");
        }
        return actualizado;
    }

    public Usuario getUsuarioPorCorreo(String correo) {
        Usuario usuario = new Usuario();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getCorreo().equals(correo)) {
                usuario = usuario1;
                break;
            }
        }
        return usuario;
    }

    public ArrayList<Transaccion> getTransaccionesCuenta(Cuenta cuenta) {
        ArrayList<Transaccion> transaccionesCuenta = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getCuentaOrigen().equals(cuenta) || transaccion.getCuentaDestino().equals(cuenta)) {
                transaccionesCuenta.add(transaccion);
            }
        }
        return transaccionesCuenta;
    }


    public Categoria obtenerCategoria(String nombreCategoria)throws PresupuestoException {
        Categoria categoriaEncontrada = null;
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombreCategoria)) {
                categoriaEncontrada = categoria;
                break;
            }
        }
        if(categoriaEncontrada == null){
            throw new PresupuestoException("Categoria con el nombre: " + nombreCategoria + " no existe");
        }else {
            return categoriaEncontrada;
        }
    }



    public boolean crearCategoria(Categoria categoria) throws CategoriaException {
        Categoria categoriaEncontrada = encontrarCategoria(categoria.getIdCategoria());
        if (categoriaEncontrada == null) {
            categorias.add(categoria);
            return true;
        } else {
            throw new CategoriaException("Categoria con idCategoria: " + categoria.getIdCategoria() + " ya existe");
        }
    }

    public boolean crearPresupuesto(Presupuesto presupuesto) throws PresupuestoException {
        Presupuesto presupuestoEncontrado = encontrarPresupuesto(presupuesto.getNombre());
        if (presupuestoEncontrado == null) {
            presupuesto.setIdPresupuesto(obtenerIdMaximoPresupuesto()+1);
            presupuestos.add(presupuesto);
            return true;
        }else {
            throw new PresupuestoException("El presupuesto no se pudo crear");
        }
    }

    public boolean crearTransaccion(Transaccion transaccion) {
            transaccion.setIdTransaccion(obtenerIdMaximoTransaccion()+1);
            transacciones.add(transaccion);
            return true;

    }

    public Cuenta obtenerCuenta(String s) {
        Cuenta nequiCuenta = new Cuenta();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta() == Integer.parseInt(s)) {
                nequiCuenta = cuenta;
                break;
            }
        }
        return nequiCuenta;
    }

    //----------------------------GESTION CATEGORIA----------------------------//
}