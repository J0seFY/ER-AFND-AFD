import java.io.File;
import java.io.IOException;

public class ProcessBuilderDemo2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        // linea de comando para crear el diagrama
        String[] list = { "cmd.exe", "/c", "dot -Tjpg automata2.dot > dfa2.jpg" };
        var processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(
                "C:\\Users\\lofte\\OneDrive\\Escritorio\\Archivos\\UBB\\RAMOS\\FUNDAMENTOS\\Tarea 1\\ER-AFND-AFD-1\\src"));
        processBuilder.command(list);
        try {
            var process = processBuilder.start();
            var ret = process.waitFor();
            // codigo cero significa que no hay error
            System.out.printf("Codigo de retorno: %d", ret);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
