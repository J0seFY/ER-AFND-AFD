import java.util.Stack;

public class ExpresionRegular {
    public static String convertirPostfija(String expresion) {
        String postfija = "";
        Stack<Character> pila = new Stack<>();
        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);
            if (Character.isLetterOrDigit(caracter)) {
                postfija += caracter;
            } else if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    postfija += pila.pop();
                }
                if (!pila.isEmpty() && pila.peek() != '(') {
                    return "Expresión inválida";
                } else {
                    pila.pop();
                }
            } else {
                while (!pila.isEmpty() && precedencia(caracter) <= precedencia(pila.peek())) {
                    if (pila.peek() == '(') {
                        return "Expresión inválida";
                    }
                    postfija += pila.pop();
                }
                pila.push(caracter);
            }
        }
        while (!pila.isEmpty()) {
            if (pila.peek() == '(') {
                return "Expresión inválida";
            }
            postfija += pila.pop();
        }
        return postfija;
    }

    public static int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    public static void main(String[] args) {
        String expresion = "a+b*(c^d-e)^(f+g*h)-i";
        System.out.println(convertirPostfija(expresion));
    }
}