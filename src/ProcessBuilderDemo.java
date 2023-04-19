import java.io.IOException;
public class ProcessBuilderDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
// linea de comando para crear el diagrama
        String[] list = {"cmd.exe", "/c", "dot -Tjpg automata.dot > dfa.jpg"};
        var processBuilder = new ProcessBuilder();
        processBuilder.command(list);
        try {
            var process = processBuilder.start();
            var ret = process.waitFor();
//codigo cero significa que no hay error
            System.out.printf("Codigo de retorno: %d", ret);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
