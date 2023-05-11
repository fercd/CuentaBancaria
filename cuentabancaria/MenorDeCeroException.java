/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancaria;

/**
 *
 * @author Usuario
 */
public class MenorDeCeroException extends Exception{
    MenorDeCeroException(){
        super("El valor de la operacion debe ser mayor que cero");
    }
    
}
