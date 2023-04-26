import java.util.ArrayList;
import java.util.HashMap;

public class Estado {

    int identificador;
    String idString = String.valueOf(identificador);
    boolean inicio;
    boolean fin;
    // en este arraylist almacenaremos los estados con los que se relaciona
    ArrayList<Estado> estados;
    HashMap<Character, ArrayList<Estado>> transiciones;

    // instancia
    private static Estado instance = null;

    public static Estado getInstance() {
        if (instance == null) {
            instance = new Estado();
        }
        return instance;
    }

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

}