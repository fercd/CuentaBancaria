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
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static void main(String[] args) throws Exception {
        try {
            ArrayList <Cuenta> lista = new ArrayList();
            ObjectContainer bdexample=null;
            
            //Crear nuevas cuentas
            lista.add(new Cuenta(1545.56+1000, "Carlos Jesus", "2345 3456 7845 3456" ));
            lista.add(new Cuenta(2000.45+250, "Mareta Andujar", "8888 6789 2345 3456"));
            lista.add(new Cuenta(153.6+564, "Tomas Perez", "3456 2345 3456 6789"));
            lista.add(new Cuenta(5678.3+457, "Javier Luis perez", "7788 9870 0002 5640"));
            lista.add(new Cuenta(4567.3+7000, "Fidel Salmeron", "0002 5640 7788 9870"));
            //Modificar algunas cosas de las cuentas
            if (lista.get(0).retirar(1500)) {
                System.out.println(lista.get(0).toString() + lista.get(0).getMovimiento());
            }
            
            if (lista.get(1).ingresar(-10)) {
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
            /*
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
            */
            querySODA();
        } catch (MenorDeCeroException ex) {
            Logger.getLogger(CuentaBancaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Realizar consultas SODA con DBO4
     * @return 
     */
    private static void querySODA(){
        ObjectContainer bd=null;
        try {
            bd=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "BDExample.db4o");
            Query consulta = bd.query();
            consulta.constrain(Cuenta.class);  //Definimos la clase a pintar
            //Select * from Cuentas where titular LIKE "Pla"
            //Constraint const1 = consulta.descend("titular").constrain("Maria").like();
            //Select * from Cuentas where saldo > 1000 and titular LIKE "Maria"
            
            ObjectSet rs = consulta.execute();
            //borrarObjectSet(bd, rs);
            pintarObjectSet(rs);
        } catch (Db4oIOException | DatabaseReadOnlyException | DatabaseClosedException e) {
            System.out.println("Error en la BD:"+ e);
        } finally{
            bd.close();
        }
    }
    
    
    
    private static void pintarObjectSet(ObjectSet res){
        System.out.println("El resultado del query es:"); 
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
    /** 
     * Borra todos los objetos contenidos en el ObjectSet recibido
     * @param res 
     */
    private static void borrarObjectSet(ObjectContainer bdexample, ObjectSet res){
        int n=0;
        while(res.hasNext()){
            bdexample.delete(res.next());
            n=n+1;
        }
        System.out.println("Nº de registros eliminados:" + n); 
        
    }
}
