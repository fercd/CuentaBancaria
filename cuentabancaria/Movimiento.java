/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancaria;

/**
 *
 * @author Fernando
 */
public class Movimiento {

    private double saldoIni;
    private double saldoFin;
    private String tipo;
    private double cantidad;

    public Movimiento(double saldoIni, double saldoFin, String tipo, double cantidad) {
        this.saldoIni = saldoIni;
        this.saldoFin = saldoFin;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }
    
    
    
    public Movimiento() {
        this.saldoIni = 0;
        this.saldoFin = 0;
        this.tipo = "";
        this.cantidad = 0;
    }

    public void Registrar(String tipo, double cantidad, double saldoIni, double saldoFin) {
        this.saldoIni = saldoIni;
        this.saldoFin = saldoFin;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s %.2fe Saldo: %.2fe -> %.2fe", tipo, cantidad, saldoIni, saldoFin);
    }

}
