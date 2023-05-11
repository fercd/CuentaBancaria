/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancaria;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Query;

/**
 *
 * @author Usuario
 */
public class miclaseDBO4 {
    
    private ObjectSet rs=null;
    ObjectContainer bd=null;
    private String archivo;
    
    public miclaseDBO4(String archivo) {
        this.archivo = archivo;
    }
    
    public boolean abrirDB() {
        try {
            bd=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), archivo);
            return(true);
        } catch (Db4oIOException | DatabaseReadOnlyException | DatabaseClosedException e) {
            System.out.println("Error en la BD:"+ e);
        } 
        return(false);
    }
    public boolean cerrarDB(){
        try {  
            bd.close();
            return(true);
        } catch (Db4oIOException | DatabaseReadOnlyException | DatabaseClosedException e) {
            System.out.println("Error en la BD:"+ e);
            return(false);
        } 
    }
    
    
    
    public void borrarDB(){
        
    }
    
    /**
     * Get the value of rs
     *
     * @return the value of rs
     */
    public ObjectSet getRs() {
        return rs;
    }

    

    /**
     * Get the value of archivo
     *
     * @return the value of archivo
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Set the value of archivo
     *
     * @param archivo new value of archivo
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    
}
