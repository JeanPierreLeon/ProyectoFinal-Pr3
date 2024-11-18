package co.edu.uniquindio.billeteradigitalapp.Utils;

import co.edu.uniquindio.billeteradigitalapp.Model.*;

import java.time.LocalDate;

public class BilleteraUtils {

    public static BilleteraDigital inicializarDatos() {
        BilleteraDigital billeteraDigital = new BilleteraDigital();

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(14131313);
        cuenta.setSaldo(0);
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setNombreBanco("Bancolombia");

        billeteraDigital.getCuentas().add(cuenta);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta(111);
        cuenta2.setTipoCuenta("Ahorro");
        cuenta2.setNombreBanco("Nequi");
        cuenta2.setSaldo(0);

        billeteraDigital.getCuentas().add(cuenta2);

        Cuenta cuenta3 = new Cuenta();
        cuenta3.setNumeroCuenta(123456);
        cuenta3.setTipoCuenta("Ahorro");
        cuenta3.setNombreBanco("WalletLink");
        cuenta3.setSaldo(100000.00);

        billeteraDigital.getCuentas().add(cuenta3);


        Usuario usuario = new Usuario();
        usuario.setNombre("Jhon");
        usuario.setIdUsuario("111");
        usuario.setCorreo("jhon@gmail.com");
        usuario.setTelefono("300671376");
        usuario.setDireccion("Calle 65 #89-09");
        usuario.getCuentasAsociadas().add(cuenta);
        usuario.getCuentasAsociadas().add(cuenta3);

        billeteraDigital.getUsuarios().add(usuario);

        Categoria categoria = new Categoria();
        categoria.setNombre("Transporte");
        categoria.setDescripcion("Viajes y mas.");
        categoria.setIdCategoria("1");
        billeteraDigital.getCategorias().add(categoria);

        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Comida");
        categoria1.setDescripcion("");
        categoria1.setIdCategoria("2");
        billeteraDigital.getCategorias().add(categoria1);


        Transaccion transaccion = new Transaccion();
        transaccion.setCategoria(categoria);
        transaccion.setTipoTransaccion("Deposito");
        transaccion.setFecha("23/07/2024");
        transaccion.setMonto(15000.00);
        transaccion.setDescripcionTransaccion("Viajes y mas.");
        transaccion.setCuentaOrigen(cuenta);
        transaccion.setCuentaDestino(cuenta2);

        billeteraDigital.getTransacciones().add(transaccion);
        transaccion = new Transaccion();
        transaccion.setCategoria(categoria);
        transaccion.setTipoTransaccion("Retiro");
        transaccion.setFecha("17/08/2024");
        transaccion.setMonto(27000);
        transaccion.setDescripcionTransaccion("Viajes y mas.");
        transaccion.setCuentaOrigen(cuenta);
        transaccion.setCuentaDestino(cuenta2);
        billeteraDigital.getTransacciones().add(transaccion);

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombre("Transporte Mes");
        presupuesto.setCategoria(categoria);
        presupuesto.setIdPresupuesto(1);
        presupuesto.setMontoTotalAsignado(15000.00);
        billeteraDigital.getPresupuestos().add(presupuesto);


        return billeteraDigital;
    }
}
