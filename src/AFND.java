import java.util.Stack;

public class AFND {
    Automata automata;
    Stack<Automata> stack = new Stack<>();
    String expresionRegular;

    public AFND(String expresionRegular){
        this.expresionRegular = expresionRegular;
        this.generarAutomata();
    }

    private void generarAutomata() {
        for (char caracter : expresionRegular.toCharArray()){
            switch (caracter){
                case '.':
                    generarAutomataConcatenacion(caracter);
                    break;
                case '|':
                    generarAutomataUnion();
                    break;
                case '*':
                    generarAutomataKleene();
                    break;
                default:
                    generarAutomataSimple(caracter);
                    break;
            }
        }

        automata = stack.pop();

        //se reinicia el atributo final e inicial de todos los estados, para que sean asignados manualmente y evitar errores
        for (Estado estado :
                automata.estados) {
            estado.inicio = false;
            estado.fin = false;
        }

        automata.inicio.inicio = true;

        for (Estado estado :
                automata.estadosFinales) {
            estado.fin = true;
        }

    }

    private void generarAutomataKleene() {

        Automata automataKleene = new Automata();

        //se definen los estados iniciales y finales
        Estado estadoInicial = new Estado(0,true,false);
        Estado estadoFinal = new Estado(0,false,true);

        Automata automata = stack.pop();

        //se agrega las transiciones vacias desde el inicio al inicio del antiguo automata y al estado final
        estadoInicial.addTransicion(' ',automata.inicio);
        estadoInicial.addTransicion(' ',estadoFinal);

        //desde el final del antiguo automata se agrega la transicion vacia al estado final y al inicio del antiguo automata
        automata.estadosFinales.get(0).addTransicion(' ',automata.inicio);
        automata.estadosFinales.get(0).addTransicion(' ',estadoFinal);


        //se agregan los estados inicial, los estados del automata antiguo y el estado final
        automataKleene.addEstado(estadoInicial);

        for (Estado estado : automata.estados) {
            automataKleene.addEstado(estado);
        }

        automataKleene.addEstado(estadoFinal);

        //se designan los estados inicial y final del automata nuevo
        automataKleene.setInicio(estadoInicial);
        automataKleene.addEstadoFinal(estadoFinal);

        //se reasignan los numeros de identificacion de los estados
        for (int i = 0; i < automataKleene.estados.size();i++){
            automataKleene.estados.get(i).identificador = i;
        }

        stack.push(automataKleene);

    }

    private void generarAutomataUnion() {
        Automata automataUnion = new Automata();
        Automata automata2 = stack.pop();
        Automata automata1 = stack.pop();

        //se crean el estado inicial y final para unir los dos automatas
        Estado estadoInicial = new Estado(0,true,false);
        Estado estadoFinal = new Estado(0,false,true);

        //se agrega la transicion vacia desde el estado inicial a el inicio de los dos automatas
        estadoInicial.addTransicion(' ',automata1.inicio);
        estadoInicial.addTransicion(' ',automata2.inicio);

        //se agrega la transicion vacia desde el estado final de ambos automatas hasta el estado final
        automata1.estadosFinales.get(0).addTransicion(' ',estadoFinal);
        automata2.estadosFinales.get(0).addTransicion(' ',estadoFinal);

        //se comienza a llenar el automata union con los estados, partiendo por el incial, continuando con los estados del automata 1,automata 2 y estado final
        automataUnion.addEstado(estadoInicial);

        for (Estado estado: automata1.estados) {
            automataUnion.addEstado(estado);
        }

        for (Estado estado: automata2.estados) {
            automataUnion.addEstado(estado);
        }

        automataUnion.addEstado(estadoFinal);

        //se definen los estados de inicio y final del automata union
        automataUnion.setInicio(estadoInicial);
        automataUnion.addEstadoFinal(estadoFinal);

        //se reasignan los numeros de identificacion de los estados
        for (int i = 0; i < automataUnion.estados.size();i++){
            automataUnion.estados.get(i).identificador = i;
        }

        stack.push(automataUnion);
    }

    private void generarAutomataConcatenacion(char caracter) {
        Automata automataConcatenacion = new Automata();
        Automata automata2 = stack.pop();
        Automata automata1 = stack.pop();

        //se obtiene el estado final del automata1 y se agrega una concatenacion vacia al inicio del automata 2
        automata1.estadosFinales.get(0).addTransicion(' ',automata2.inicio);

        //se agregan los estados de ambos automatas al automata final
        for (Estado estado : automata1.estados) {
            automataConcatenacion.addEstado(estado);
        }

        for (Estado estado : automata2.estados) {
            automataConcatenacion.addEstado(estado);
        }

        /*se definen los estados iniciales y finales, para inicial se toma el inicial
         del automata 1 y para el final se toman los estados finales del automata 2*/
        automataConcatenacion.setInicio(automata1.inicio);
        automataConcatenacion.estadosFinales.addAll(automata2.estadosFinales);

        //el siguiente ciclo actualiza los id de los estados para que vayan en orden

        for (int i =0 ; i < automataConcatenacion.estados.size();i++){
            automataConcatenacion.estados.get(i).identificador = i;
        }

        this.stack.push(automataConcatenacion);
    }

    private void generarAutomataSimple(char caracter) {

        Automata automataBasico = new Automata();
        Estado estado1 = new Estado(0,true,false);
        Estado estado2 = new Estado(1,false,true);

        estado1.addTransicion(caracter,estado2);

        automataBasico.addEstado(estado1);
        automataBasico.addEstado(estado2);

        automataBasico.setInicio(estado1);
        automataBasico.addEstadoFinal(estado2);

        stack.push(automataBasico);

    }
}
