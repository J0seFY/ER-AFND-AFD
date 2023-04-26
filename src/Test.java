import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        System.out.println("Ingrese la expresion regular: ");
        Scanner sc = new Scanner(System.in);
        String expresionRegular = sc.next();
        // Crea la variable expresionPrcesada que es la expresion regular procesada es
        // enviada a ProcvesadorER
        ProcesadorER expresionProcesada = new ProcesadorER(expresionRegular);
        AFND transformarER_AFND = new AFND(expresionProcesada.getExpresionProcesada());
        Automata afnd = transformarER_AFND.automata;
        writeDot(afnd);

        Automata afd = AFD.getInstance().getAutomata();
        AFD.writeDot(afd);

    }

    public static void writeDot(Automata automata) {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("src\\automata1.dot")))) {
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
            for (Estado estado : automata.estadosFinales) {
                out.write(" \"" + estado.identificador + "\" ");
            }
            out.write(";");
            out.newLine();
            out.write("node [shape = circle]; ");
            for (Estado estado : automata.estados) {
                out.write("\"" + estado.identificador + "\"");
            }
            out.write(";");
            out.newLine();

            // Escribir las definiciones de las aristas
            for (Estado estado1 : automata.estados) {
                for (Character clave : estado1.transiciones.keySet()) {
                    ArrayList<Estado> valores = estado1.transiciones.get(clave);
                    for (Estado estado2 : valores) {
                        out.write("\"" + estado1.identificador + "\" -> \"" + estado2.identificador + "\"");
                        if (clave == ' ') {
                            out.write(" [label = \"" + "eps" + "\"];");
                        } else {
                            out.write(" [label = \"" + clave + "\"];");
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
