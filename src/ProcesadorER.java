import java.util.HashMap;
import java.util.Stack;

public class ProcesadorER {

    //esta clase procesa la expresion y la convierte en una expresion postfija para que sea mas facil de operar

    String expresionProcesada;

    public ProcesadorER(String expresion) {
        expresionProcesada = convertirExpresion(expresion);

    }

    private String convertirExpresion(String expresion) {

        String postfijada = "";
        Stack<Character> pila = new Stack<>();

        for(char caracter: expresion.toCharArray()) {
            if (Character.isLetterOrDigit(caracter)) {
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
        while (!pila.isEmpty()){
            postfijada +=pila.pop();
        }

        return postfijada;
    }


    private int precedencia(char caracter){

        HashMap<Character,Integer> precedencia = new HashMap<>();
        precedencia.put('(',1);
        precedencia.put('|',2);
        precedencia.put('.',3);
        precedencia.put('*',4);

        return precedencia.get(caracter);
    }



    public String getExpresionProcesada() {
        return expresionProcesada;
    }
}
