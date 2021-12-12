package Compi;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;
import Config.Config;
import Config.Linea;

public class MaquinaPila {

    int cP; //contador de programa
    ArrayList mem;//memoria
    Stack pila;
    TablaSimb tabla;
    boolean stop = false, returning = false;
    Stack<Frame> stakFrame; //marcos de programa
    Config configAct;

    public MaquinaPila(TablaSimb tabla){
        configAct = new Config();
        cP = 0;
        mem = new ArrayList<Method>();
        pila = new Stack();
        this.tabla = tabla;
        stakFrame = new Stack();
    }
    public int numeroElementos(){ return mem.size()+1; }

    public int agregaOpera(String cad){
        int pos = mem.size();
        try {
            mem.add(this.getClass().getDeclaredMethod(cad, null));
            return pos;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int agregar(Object obj){
        int pos = mem.size();
        mem.add(obj);
        return pos;
    }
    public int agregarOperaen(String cad, int pos){
        try {
            mem.add(pos, this.getClass().getDeclaredMethod(cad, null));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return pos;
    }
    private void SUMA(){
        Object val2 = pila.pop();
        Object val1 = pila.pop();
        pila.push((double)val1 + (double)val2 );
    }
    private void RESTA(){
        Object val2 = pila.pop();
        Object val1 = pila.pop();
        pila.push((double)val1 - (double)val2 );
    }
    private void MULTIPLICACION(){
        Object val2 = pila.pop();
        Object val1 = pila.pop();
        pila.push((double)val1 * (double)val2 );
    }
    private void DIVISION(){
        Object val2 = pila.pop();
        Object val1 = pila.pop();
        pila.push((double)val1 / (double)val2 );
    }
    private void neg(){
        Object val1 = pila.pop();
        pila.push(-(double)val1 );
    }
    private void constPush(){ pila.push(mem.get(++cP));}
    private void varPush(){pila.push(mem.get(++cP));}
    private void varPevaluar(){pila.push(tabla.encontrar((String)mem.get(++cP)));}
    private void asign(){
        String var = (String)pila.pop();
        Object val1 = pila.pop();
        tabla.insert(var, val1);
    }
    private void EQ(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push((boolean)((double)val1==(double)val2));
    }
    private void NE(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push((boolean)((double)val1!=(double)val2));
    }
    private void LE(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push(((double)val1 <(double)val2));
    }
    private void LQ(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push(((double)val1 <= (double)val2));
    }
    private void GT(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push(((double)val1 > (double)val2));
    }
    private void GE(){
        Object val1 = pila.pop();
        Object val2 = pila.pop();
        pila.push(((double)val1 >= (double)val2));
    }
    private void AND(){
        pila.push((boolean)pila.pop() && (boolean)pila.pop());
    }
    private void OR(){
        pila.push((boolean)pila.pop() || (boolean)pila.pop());
    }
    private void NOT(){
        pila.push(!(boolean)pila.pop());
    }
    private void stop(){
        stop = true;
    }
    private void RETURN_ (){
        returning = true;
    }
    private void no(){

    }

    private void WHILE(){
        int cond = cP;
        boolean cont = true;
        while(cont && !returning){
            ejecuta(cond+3);
            if((boolean)pila.pop()){
                ejecuta((int)mem.get(cond+1));
            }else{
                cP = (int)mem.get(cond+2);
                cont = false;
            }
        }
    }

    private void IFelse(){
        int cond = cP;
        ejecuta(cond+4);
        boolean res = true;
        try{
            res = (boolean)pila.pop();
        }catch (Exception e){
        }
        if(res){
            ejecuta((int)mem.get(cond+1));
        }else{
            ejecuta((int)mem.get(cond+2));
        }
        cP = (int)mem.get(cond+3)-1;
    }
    private void FOR(){
        int cond = cP;
        ejecuta(cond+5);
        boolean cont = true;
        while(cont && !returning){
            ejecuta((int)mem.get(cond+1));
            if((boolean)pila.pop()){
                ejecuta((int)mem.get(cond+3));
                ejecuta((int)mem.get(cond+2));
            }else{
                cP = (int)mem.get(cond+4);
                cont = false;
            }
        }
    }

    private void dec(){
        //Primera instrucción de la función
        tabla.insert((String)mem.get(++cP), ++cP);
        int invoc = 0;
        while(mem.get(cP) != null || invoc != 0){
            if(mem.get(cP) instanceof Method)
                if(((Method)mem.get(cP)).getName().equals("INV"))
                    invoc++;
            if(mem.get(cP) instanceof Func)
                invoc++;
            if(mem.get(cP) == null)
                invoc--;
            cP++;
        }
    }

    private void INV(){
        Frame marc = new Frame();
        String cad = (String)mem.get(++cP);
        marc.setNombre(cad);
        cP++;
        while(mem.get(cP) != null){
            if(mem.get(cP) instanceof String){
                if(((String)(mem.get(cP))).equals("LIMIT")){
                    Object param = pila.pop();
                    marc.agregarParam(param);
                    cP++;
                }
            }else{
                ejecutaInstr(cP);
            }
        }
        marc.setReturn(cP);
        stakFrame.add(marc);
        ejecutaFunc((int)tabla.encontrar(cad));
    }

    private void pushParam(){
        pila.push(stakFrame.lastElement().getParam((int)mem.get(++cP)-1));
    }

    public void imprimirMem(){
        for(int i =0; i < mem.size(); i++)
            System.out.println(""+i+": "+mem.get(i));
    }

    public void ejecuta(){
        stop = false;
        while(cP < mem.size())
            ejecutaInstr(cP);
    }

    public boolean ejecutaSig(){
        if(cP < mem.size()){
            ejecutaInstr(cP);
            return true;
        }
        return false;
    }

    public void ejecuta(int indice){
        cP = indice;
        while(!stop && !returning){
            ejecutaInstr(cP);
        }
        stop = false;
    }

    public void ejecutaFunc(int indice){
        cP = indice;
        while(!returning && mem.get(cP)!=null){
            ejecutaInstr(cP);
        }
        returning = false;
        cP = stakFrame.lastElement().getReturn();
        stakFrame.removeElement(stakFrame.lastElement());
    }

    public void ejecutaInstr(int indice){
        try{
            Object objeto = mem.get(indice);
            if(objeto instanceof Method){
                Method metodo = (Method)objeto;
                metodo.invoke(this, null);
            }
            if(objeto instanceof Func){
                ArrayList params = new ArrayList();
                Func funcion = (Func)objeto;
                cP++;
                while(mem.get(cP) != null){
                    if(mem.get(cP) instanceof String){
                        if(((String)(mem.get(cP))).equals("LIMIT")){
                            Object param = pila.pop();
                            params.add(param);
                            cP++;
                        }
                    }else{
                        ejecutaInstr(cP);
                    }
                }
                funcion.ejecuta(configAct, params);
            }
            cP++;
        }catch(Exception e){}
    }
    public Config getConfig(){
        return configAct;
    }
    public static class Gira implements Func {
        public void ejecuta(Object a, ArrayList params){
            Config config = (Config)a;
            int ang = (config.getAng()+(int)(double)params.get(0))%360;
            config.setAng(ang);
        }
    }

    public static class Avanza implements Func {
        public void ejecuta(Object a, ArrayList params){
            Config config = (Config)a;
            int ang = config.getAng();
            double x0, y0, x1, y1;
            x0 = config.getX();
            y0 = config.getY();
            //REFERENTE AL EJE AL QUE CORRESPONDE X COS, Y SEN
            x1 = x0+Math.cos(Math.toRadians(ang))*(double)params.get(0);
            y1 = y0+Math.sin(Math.toRadians(ang))*(double)params.get(0);
            config.setPos(x1, y1);
            config.agregaLinea(new Linea((int)x0, (int)y0, (int)x1, (int)y1, config.getColor()));
        }
    }

    public static class CambiaColor implements Func {
        @Override
        public void ejecuta(Object a, ArrayList params){
            Config config = (Config)a;
            config.setColor(new Color((int)(double)params.get(0)%256, (int)(double)params.get(1)%256, (int)(double)params.get(2)%256));//VALORES PARA ASIGNAR EL RGB
        }
    }

    public static class PincelUP implements Func {
        @Override
        public void ejecuta(Object a, ArrayList params){
            Config config = (Config)a;
            config.setColor(Color.GRAY);
        }
    }

    public static class PincelDOWN implements Func {
        @Override
        public void ejecuta(Object a, ArrayList params){
            Config config = (Config)a;
            config.setColor(Color.BLACK);
        }
    }
}
