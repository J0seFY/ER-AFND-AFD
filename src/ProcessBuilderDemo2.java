/*

Integrantes:
- Diego Arteaga Mendoza
- Christian Diaz Reyes
- José Fuentes Yáñez

Referencias obtenidas desde: https://github.com/maticou/ER-to-AFND-to-AFD

*/

import java.io.File;
import java.io.IOException;

public class ProcessBuilderDemo2 {

    private static ProcessBuilderDemo2 instance;

    public static ProcessBuilderDemo2 getInstance() {
        if (instance == null) {
            instance = new ProcessBuilderDemo2();
        }
        return instance;
    }

    public void main(String[] args) throws IOException, InterruptedException {
        make();
    }

    public void make() {
        // linea de comando para crear el diagrama
        String[] list = { "cmd.exe", "/c", "dot -Tjpg automata2.dot > dfa2.jpg" };
        var processBuilder = new ProcessBuilder();

        processBuilder.command(list);
        try {
            var process = processBuilder.start();
            var ret = process.waitFor();
            // codigo cero significa que no hay error
            System.out.printf("Codigo de retorno: %d", ret);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
