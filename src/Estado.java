import java.util.ArrayList;
import java.util.HashMap;

public class Estado {

    int identificador;
    boolean inicio;
    boolean fin;
    // en este arraylist almacenaremos los estados con los que se relaciona
    ArrayList<Estado> estados;
    HashMap<Character, ArrayList<Estado>> transiciones;

    public Estado() {
    }

    public Estado(int identificador, boolean inicio, boolean fin) {
        this.identificador = identificador;
        this.inicio = inicio;
        this.fin = fin;
        transiciones = new HashMap<>();
        estados = new ArrayList<>();
    }

    public void addTransicion(char caracter, Estado estadoNuevo) {

        if (transiciones.containsKey(caracter) == false) {

            ArrayList<Estado> estados = new ArrayList<>();
            estados.add(estadoNuevo);
            transiciones.put(caracter, estados);
        } else {
            transiciones.get(caracter).add(estadoNuevo);
        }
    }

    void verificar_estado_final(){
        for(Estado estado : this.estados){
            if(estado.fin == true){
                this.fin = true;
            }
        }
    }

    void imprimir_transiciones(){
        for (Character i : transiciones.keySet()) {
            for(Estado estado: transiciones.get(i)){
                System.out.println("{"+" q"+ this.identificador+ ", " + i + "," + " q" +estado.identificador + " }");
            }
        }
    }

}