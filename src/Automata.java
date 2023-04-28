/*

Integrantes:
- Diego Arteaga Mendoza
- Christian Diaz Reyes
- José Fuentes Yáñez

Referencias obtenidas desde: https://github.com/maticou/ER-to-AFND-to-AFD

*/

import java.util.ArrayList;
//import java.util.HashMap;

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

    public void addEstado(Estado estado) {
        estados.add(estado);
    }

    public void addEstadoFinal(Estado estado) {
        estadosFinales.add(estado);
    }
}
