import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String expresionRegular = sc.next();
        ProcesadorER expresionProcesada = new ProcesadorER(expresionRegular);
        AFND transformarER_AFND = new AFND(expresionProcesada.getExpresionProcesada());
        Automata afnd = transformarER_AFND.automata;
        writeDotAFND(afnd);
    }


    public static void writeDotAFND(Automata afnd) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("automata.dot")))) {
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
            for (Estado estado : afnd.estadosFinales) {
                out.write("\""+estado.identificador+"\"");
            }
            out.write(";");
            out.newLine();
            out.write("node [shape = circle]; ");
            for (Estado estado : afnd.estados) {
                out.write("\"" + estado.identificador + "\"");
            }
            out.write(";");
            out.newLine();

            // Escribir las definiciones de las aristas
            for (Estado estado1 : afnd.estados) {
                for (Character clave : estado1.transiciones.keySet()){
                    ArrayList<Estado> valores = estado1.transiciones.get(clave);
                    for (Estado estado2 : valores){
                        out.write("\"" + estado1.identificador + "\" -> \"" + estado2.identificador+ "\"");
                        if (clave == ' '){
                            out.write(" [label = \"" + "eps" + "\"]");
                        }else {
                            out.write(" [label = \"" + clave + "\"]");
                        }
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
