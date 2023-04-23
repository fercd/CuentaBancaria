
package cuentabancaria;

/**
 *
 * @author Fernando
 */
public class Cuenta {

    private double saldo;
    private String titular;
    private String numCuenta;
    private Movimiento mov = new Movimiento();

    public Cuenta(double saldo, String titular, String numCuenta) {
        this.saldo = saldo;
        this.titular = titular;
        this.numCuenta = numCuenta;
    }

    

    public boolean ingresar(double cantidad) {
        if (cantidad > 0) {
            double saldotemp = saldo;
            saldo = saldo + cantidad;
            mov.Registrar("INGRESO", cantidad, saldotemp, saldo);
            return true;
        }
        System.out.println("La cantidad a ingresar debe ser mayor que cero");
        return false;
    }

    public boolean retirar(double cantidad) {
        if (cantidad > 0) {
            if (saldo >= cantidad) {
                double saldotemp = saldo;
                saldo = saldo - cantidad;
                mov.Registrar("RETIRADA", cantidad, saldotemp, saldo);
                return true;
            } else {
                System.out.print("No hay suficiente saldo en la cuenta para retirar: " + cantidad + " euros\n"
                        + "Saldo disponible en la cuenta: " + saldo);
            }
        } else {
            System.out.println("Error: La cantidad a retirar debe ser mayor que cero");
        }
        return false;
    }

    public boolean trasferir(Cuenta cuentaDestino, double cantidad) {
        if (saldo >= cantidad) {
            if (cuentaDestino.ingresar(cantidad) == true) {
                double saldotemp = saldo;
                saldo = saldo - cantidad;
                mov.Registrar("TRASFERENCIA A " + cuentaDestino.getTitular(), cantidad, saldotemp, saldo);
                return true;
            } else {
                System.out.println("Error en la trasferencia. No se ha podido realizar por problemas en la Cuenta de "
                        + cuentaDestino.getTitular());
            }
        } else {
            System.out.println("No hay suficiente saldo en la cuenta para trasferir: " + cantidad + " €\n"
                    + "Saldo disponible en la cuenta:" + saldo);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "saldo=" + saldo + ", titular=" + titular + ", numCuenta=" + numCuenta + ", mov=" + mov + '}';
    }

    

    public String getMovimiento() {
        return mov.toString();
    }

    /**
     * Get the value of numCuenta
     *
     * @return the value of numCuenta
     */
    public String getNumCuenta() {
        return numCuenta;
    }

    /**
     * Get the value of titular
     *
     * @return the value of titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Get the value of saldo
     *
     * @return the value of saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Set the value of saldo
     *
     * @param saldo new value of saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
