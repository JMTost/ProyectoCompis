package Compi;

public class Par {
    private String cadena;
    private Object obj;

    public Par(String nombre, Object object){
        this.cadena = nombre;
        this.obj = object;
    }

    public String getCadena(){
        return cadena;
    }
    public void setCadena(String nombre){
        this.cadena = nombre;
    }
    public Object getObj(){
        return obj;
    }
    public void setObj(Object object){
        this.obj = object;
    }
}
