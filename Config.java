/*
*   Carrillo Rodríguez Antonio
*   Hernández Clemente Samantha
*   Leal Avilés Carlos David
*   Tostado Navarro Jesús Maximiliano
*   Proyecto - LOGOS
*   3CM17
*   COMPILADORES
*   Archivo que servira para realizar la impresión / dibujo en el panel del programa
*/

import java.awt.*;
import java.util.ArrayList;

public class Config {
    private final ArrayList<Linea> lineas;
    private double x, y;
    private int ang;
    private Color color;

    public Config(){
        x = 0.0;
        y = 0.0;
        lineas = new ArrayList<Linea>();
    }

    public ArrayList<Linea> getLineas(){
        return lineas;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public int getAng(){
        return ang;
    }

    public Color getColor(){
        return color;
    }

    public void setAng(int angulo){
        this.ang = angulo;
    }

    public void setColor(Color color){
        this.color = color;
    }
    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void agregaLinea(Linea linea){
        lineas.add(linea);
    }
    @Override
    public String toString(){
        StringBuilder val = new StringBuilder();

        for(Linea linea: lineas) val.append(linea).append(", ");
        val.append("x: ").append(x).append(" y: ").append(y).append(" angulo: ").append(ang);
        return val.toString();
    }


}
