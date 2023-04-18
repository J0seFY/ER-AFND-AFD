import java.util.ArrayList;
import java.util.HashMap;

public class Automata {

    Estado inicio;
    ArrayList<Estado> estados;
    ArrayList<Estado> estadosFinales;

    public Automata() {
        estados = new ArrayList<>();
        estadosFinales = new ArrayList<>();
    }

    public void setInicio(Estado inicio) {
        this.inicio = inicio;
    }

    public void addEstado(Estado estado){
        estados.add(estado);
    }

    public void addEstadoFinal(Estado estado){
        estadosFinales.add(estado);
    }
}


class Estado{

    int identificador;
    boolean inicio;
    boolean fin;
    //en este arraylist almacenaremos los estados con los que se relaciona
    ArrayList<Estado> estados;
    HashMap<Character, ArrayList<Estado>> transiciones;

    public Estado(int identificador, boolean inicio, boolean fin) {
        this.identificador = identificador;
        this.inicio = inicio;
        this.fin = fin;
        transiciones = new HashMap<>();
        estados = new ArrayList<>();
    }


    public void addTransicion(char caracter, Estado estadoNuevo){

        if(transiciones.containsKey(caracter)){

            ArrayList<Estado> estados = new ArrayList<>();
            estados.add(estadoNuevo);
            transiciones.put(caracter,estados);
        }else{
            transiciones.get(caracter).add(estadoNuevo);
        }
    }

}
