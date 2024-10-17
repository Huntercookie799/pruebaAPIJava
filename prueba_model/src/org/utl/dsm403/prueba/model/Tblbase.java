
package org.utl.dsm403.prueba.model;

public class Tblbase {
    
    private int idBase;
    private int edad;
    private String nombre;
    
    public Tblbase(){
        
    }
    
    public Tblbase(int idBase, int edad, String nombre) {
        this.idBase = idBase;
        this.edad = edad;
        this.nombre = nombre;
    }
    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return idBase + "\t" + edad + "\t" + nombre;
    }

}
