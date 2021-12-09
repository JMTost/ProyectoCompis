//Archivo del marco de funciones

package Compi;

import java.util.ArrayList;

public class Frame {
    private ArrayList params;
    private int returning;
    private String nombre;

    public Frame(){
        params = new ArrayList();
        returning = 0;
        nombre = null;
    }

    public void agregarParam(Object param){
        params.add(param);
    }

    public Object getParam(int i){
        return params.get(i);
    }

    public void setParams(ArrayList params){
        this.params = params;
    }

    public int getReturn(){
        return returning;
    }

    public void setReturn(int returning){
        this.returning = returning;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
