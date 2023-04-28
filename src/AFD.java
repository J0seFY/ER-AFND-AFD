import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Stack;

public class AFD {

    Estado estado;

    ArrayList<Estado> listaEstados;
    ArrayList<String> alfabeto;
    ArrayList<String> inicios;
    ArrayList<String> finales;
    Automata afnd;
    Stack<Estado> pila_estados;
    Estado estado_inicial;
    Estado sumidero;

    public AFD(Automata automata, ArrayList<String> alfabeto) {

        this.listaEstados = new ArrayList();
        this.alfabeto = alfabeto;
        this.inicios = new ArrayList();
        this.finales = new ArrayList();
        this.pila_estados = new Stack();

        this.afnd = automata;
        //Se obtienen todos los estados a los que se puede llegar desde el estado 0
        //usando solo transiciones epsilon
        obtener_eclosure();

        //se crea el estado inicial para el afd y un estado sumidero
        estado_inicial = new Estado(0, true, false);
        sumidero = new Estado(1, false, false);

        //se agregan los ciclos correspondientes al sumidero
        for(String string: this.alfabeto){
            sumidero.addTransicion(string.charAt(0), sumidero);
        }

        estado_inicial.estados.addAll(pila_estados);
        pila_estados.clear();

        //se agregan los estados creados prevamente a la lista de estados que representara nuestro AFD
        this.listaEstados.add(estado_inicial);
        this.listaEstados.add(sumidero);

        //se agrega el estado inicial a la pila de la cual obtendremos los estados
        //necesarios para calcular los nuevos estados del AFD
        this.pila_estados.push(estado_inicial);


        while(this.pila_estados.empty() != true){
            this.generar_estados_afd(this.pila_estados.pop());
        }

        //se modifican los id de cada estado creado, para que
        //la funcion delta que se imprimira sea mas entendible.
        for(int i = 0; i< listaEstados.size(); i++){
            listaEstados.get(i).identificador = i;
        }

        imprimir_afd();
        writeDot();
    }

    //imprime de manera estandar el AFD
    void imprimir_afd(){
        System.out.println("");
        System.out.println("AFD");
        System.out.printf("K = { ");
        for(int i=0;i<this.listaEstados.size();i++){
            if(this.listaEstados.size()-1 == i){
                System.out.printf("q"+listaEstados.get(i).identificador);
            }else{
                System.out.printf("q"+listaEstados.get(i).identificador+" ,");
            }
        }
        System.out.printf(" }");
        System.out.println(" ");
        System.out.printf("Sigma = [");
        for(int n=0;n<this.alfabeto.size();n++){
            if(this.alfabeto.size()-1 == n){
                System.out.println(this.alfabeto.get(n) + "]");
            }else{
                System.out.printf(this.alfabeto.get(n) + ", ");
            }
        }
        System.out.println("Delta :");

        for(Estado estado: this.listaEstados){
            estado.imprimir_transiciones();
        }
        System.out.println("s = { q" + this.estado_inicial.identificador + " }");
        System.out.printf("F = { ");
        for(int i=0;i<this.listaEstados.size();i++){
            if(this.listaEstados.get(i).fin){
                if(this.listaEstados.size()-1 == i){
                    System.out.printf("q" + this.listaEstados.get(i).identificador);
                }else{
                    System.out.printf("q" + this.listaEstados.get(i).identificador + ",");
                }
            }
        }
        System.out.printf(" }");
        System.out.println("");
    }

    //convierte las transiciones de un estado a los estados y transiciones que
    //se utilizaran para armar el AFD.
    void generar_estados_afd(Estado estado){
        for(String string : this.alfabeto){
            ArrayList<Estado> transiciones =  new ArrayList<Estado>();

            //se obtienen todos los estados a los que accede un
            //estado especifico usando una letra del alfabeto y
            //transiciones epsilon.
            for(Estado s : estado.estados ){
                if(s.transiciones.containsKey(string.charAt(0)) == true){
                    for(Estado aux: s.transiciones.get(string.charAt(0))){
                        obtener_transiciones_estado(aux, transiciones);
                    }
                }
            }

            //si el estado tiene transiciones con el caracter especificado
            //se agrega a la lista de estados del AFD y a la pila para que se generen
            // nuevos estados y trancisiones a traves de este nuevo estado.
            if(transiciones.isEmpty() == false){
                Estado nuevo_estado = new Estado(0, false, false);
                nuevo_estado.estados.addAll(transiciones);

                estado.addTransicion(string.charAt(0), nuevo_estado);

                nuevo_estado.verificar_estado_final();

                if(verificar_existencia_estado(nuevo_estado) == false){
                    this.listaEstados.add(nuevo_estado);
                    this.pila_estados.push(nuevo_estado);
                }

                transiciones.clear();
            }
            else{
                //si el estado no tiene transiciones con algun caracter del alfabeto
                // entonces se le agregan transiciones a un estado sumidero usando
                //el caracter especificado.
                estado.addTransicion(string.charAt(0), this.sumidero);
            }
        }
    }

    //obtiene todas las transiciones epsilon de un estado especifico y las almacena en
    //un arraylist que contiene todos los estados que componen un estado del AFD
    void obtener_transiciones_estado(Estado estado, ArrayList<Estado> transiciones){
        if(transiciones.contains(estado) == false){
            transiciones.add(estado);

            if(estado.transiciones.containsKey(' ') == true){
                for(Estado s : estado.transiciones.get(' ')){
                    obtener_transiciones_estado(s, transiciones);
                }
            }
        }
    }

    //verifica si es que ya se ha analizado un estado previamente y se agrega
    // a la lista de estados en caso de que no se haya analizado
    boolean verificar_existencia_estado(Estado estado){
        for(Estado s: this.listaEstados){
            if(s.estados.containsAll(estado.estados) == true){
                return true;
            }
        }

        return false;
    }

    void obtener_eclosure(){
        transicion_epsilon(afnd.inicio);
    }

    //funcion que obtiene los estados a los que se puede llegar desde el estado 0
    //usando solo transiciones epsilon
    void transicion_epsilon(Estado estado){

        if(pila_estados.contains(estado) == false){
            this.pila_estados.push(estado);

            if(estado.transiciones.get(' ') != null){
                for(Estado s: estado.transiciones.get(' ')){
                    transicion_epsilon(s);
                }
            }
        }
    }

    private void writeDot() {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("automata2.dot")))) {
            // Escribir las primeras líneas del archivo
            out.write("digraph finite_state_machine {");
            out.newLine();
            out.write("fontname=\"Helvetica,Arial,sans-serif\"");
            out.newLine();
            out.write("node [fontname=\"Helvetica,Arial,sans-serif\"]");
            out.newLine();
            out.write("edge [fontname=\"Helvetica,Arial,sans-serif\"]");
            out.newLine();
            out.write("rankdir=LR;");
            out.newLine();

            // Escribir las definiciones de los nodos
            out.write("node [shape = doublecircle];");
            for (int i=0;i<this.listaEstados.size();i++) {
                if(this.listaEstados.get(i).fin){
                    out.write(" \"" + this.listaEstados.get(i).identificador + "\" ");
                }
            }
            out.write(";");
            out.newLine();
            out.write("node [shape = circle]; ");

            for (int i=0;i<this.listaEstados.size();i++) {
                out.write(" \"" + this.listaEstados.get(i).identificador + "\" ");
            }

            out.write(";");
            out.newLine();

            // Escribir las definiciones de las aristas
            for (Estado estado1 : listaEstados) {
                for (Character clave : estado1.transiciones.keySet()) {
                    ArrayList<Estado> valores = estado1.transiciones.get(clave);
                    for (Estado estado2 : valores) {
                        out.write("\"" + estado1.identificador + "\" -> \"" + estado2.identificador + "\"");
                        out.write(" [label = \"" + clave + "\"];");

                        out.newLine();
                    }
                }
            }

            // Escribir la última línea del archivo
            out.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}