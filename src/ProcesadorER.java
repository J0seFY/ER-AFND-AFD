/*

Integrantes:
- Diego Arteaga Mendoza
- Christian Diaz Reyes
- José Fuentes Yáñez

Referencias obtenidas desde: https://github.com/maticou/ER-to-AFND-to-AFD

*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ProcesadorER {

    // esta clase procesa la expresion y la convierte en una expresion postfija para
    // que sea mas facil de operar

    String expresionProcesada;
    ArrayList<String> lenguaje;

    public ProcesadorER(String expresion) {
        lenguaje = new ArrayList<>();
        expresionProcesada = convertirExpresion(expresion);

    }

    private String convertirExpresion(String expresion) {

        String postfijada = "";
        Stack<Character> pila = new Stack<>();

        // recorre la cadena caracter por caractaer
        // en caso de que se trate de una leta o numero lo agrega a postfijada que sera
        // la cadena, en caso de ser un ( lo agrega a la pila y en en caso de ser un )
        // va sacando elementos de la pila hasta encontar otro )
        for (char caracter : expresion.toCharArray()) {
            if (Character.isLetterOrDigit(caracter)) {
                lenguaje.add(String.valueOf(caracter));
                postfijada += caracter;
            } else if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    postfijada += pila.pop();
                }
                pila.pop();
            } else {
                while (!pila.isEmpty() && precedencia(caracter) <= precedencia(pila.peek())) {
                    postfijada += pila.pop();
                }
                pila.push(caracter);
            }
        }
        while (!pila.isEmpty()) {
            postfijada += pila.pop();
        }

        return postfijada;
    }

    // se establece la precedencia de las operaciones a traves de un hashmap a fin
    // de hacer mas facil su acceso
    private int precedencia(char caracter) {

        HashMap<Character, Integer> precedencia = new HashMap<>();
        precedencia.put('(', 1);
        precedencia.put('|', 2);
        precedencia.put('.', 3);
        precedencia.put('*', 4);

        return precedencia.get(caracter);
    }

    public String getExpresionProcesada() {
        return expresionProcesada;
    }
}
