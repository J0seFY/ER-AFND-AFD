/*

Integrantes:
- Diego Arteaga Mendoza
- Christian Diaz Reyes
- José Fuentes Yáñez

Referencias obtenidas desde: https://github.com/maticou/ER-to-AFND-to-AFD

*/

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

        AFD transformarAFND_AFD = new AFD(afnd, expresionProcesada.lenguaje);

        ProcessBuilderDemo.getInstance().make();
        System.out.println("");
        ProcessBuilderDemo2.getInstance().make();

    }

}
