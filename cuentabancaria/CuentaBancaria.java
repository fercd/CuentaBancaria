/*
 * Cuenta Bancaria
 * @Author: Fernando Carrasco
 */
package cuentabancaria;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class CuentaBancaria {
    /**
     * Crea un ArrayList de Cuenta y realiza varias operaciones entre ellas, trasferencias, ingresos
     * Guarda la informacion en una bd4o y despues la recupera con queryByExample
     * 
     * @param args 
     */
    public static void main(String[] args) {
        ArrayList <Cuenta> lista = new ArrayList();
        ObjectContainer bdexample=null;
        lista.add(new Cuenta(1545.56, "Francisco Garcia", "2345 3456 7845 3456" ));
        lista.add(new Cuenta(2000.45, "Maria Salmeron", "8888 6789 4567 3454"));
        lista.add(new Cuenta(153.6, "Carlos Perez", "3456 5454 3345 6789"));
        lista.add(new Cuenta(5678.3, "Jose Ruiz", "9876 0987 0002 5640"));
        lista.add(new Cuenta(4567.3, "Roman Pla", "4343 5566 7788 9870"));
           
        if (lista.get(0).retirar(1500) == true) {
            System.out.println(lista.get(0).toString() + lista.get(0).getMovimiento());
        }
        
        if (lista.get(1).ingresar(333.67) == true) {
            System.out.println(lista.get(1).toString() + lista.get(1).getMovimiento());
        }

        //Realizar una trasferencia
        if (lista.get(0).trasferir(lista.get(1), 45) == true) {
            System.out.println(lista.get(0).toString() + lista.get(0).getMovimiento());
            System.out.println(lista.get(1).toString() + lista.get(1).getMovimiento());
        }
        
        //Guardar en una BD4o las clases cuentas de los clientes
        try {
            bdexample=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "BDExample.db4o");
            for(Cuenta cta: lista){
                bdexample.store(cta);
            }
            System.out.println("Guardados los datos en la BDo");
        } catch (Db4oIOException e) {
            System.out.println("Error en la BD:"+ e);
        } finally{
            bdexample.close();
        }
               
        //editar un campo de la BD
        try {
            bdexample=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "BDExample.db4o");
            pintarBD(bdexample);
            Cuenta ex = new Cuenta(0,null,"8888 6789 4567 3454"); //Muestra toda la BD
            ObjectSet res = bdexample.queryByExample(ex);
            
            while(res.hasNext()){
                Cuenta c = (Cuenta) res.next();
                c.setSaldo(c.getSaldo()+1500); //Suma 1500 al saldo
                bdexample.store(c);
            }
            pintarBD(bdexample);
        } catch (Db4oIOException | DatabaseReadOnlyException | DatabaseClosedException e) {
            System.out.println("Error en la BD:"+ e);
        } finally{
            bdexample.close();
        }
        
    }
    private static void pintarObjectSet(ObjectSet res){
        System.out.println("El ObjectSet es:"); 
        while(res.hasNext()){
            System.out.println(res.next().toString());    
        }
    }
    
    private static void pintarBD(ObjectContainer bdexample){
        System.out.println("La base de datos es:"); 
        Cuenta ex = new Cuenta(0,null,null); //Muestra toda la BD
        ObjectSet res = bdexample.queryByExample(ex);
        pintarObjectSet(res);
    }
}
