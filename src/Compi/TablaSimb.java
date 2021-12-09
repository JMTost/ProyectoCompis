package Compi;

import java.util.ArrayList;

public class TablaSimb {
    ArrayList<Par> simbols;

    public TablaSimb(){
        simbols = new ArrayList<Par>();
    }
    public Object encontrar(String cad){
        for(int i = 0; i < simbols.size(); i++)
            if(cad.equals(simbols.get(i).getCadena()))
                return simbols.get(i).getObj();

        return null;
    }

    public boolean insert(String cad, Object objec){
        Par par = new Par(cad, objec);
        for(int i = 0; i < simbols.size(); i++)
            if(cad.equals(simbols.get(i).getCadena())){
                simbols.get(i).setObj(objec);
                return true;
            }
        simbols.add(par);
        return false;
    }
}
