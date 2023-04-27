import java.io.File;
import java.io.IOException;
public class ProcessBuilderDemo {

    private static ProcessBuilderDemo instance ;

    public static ProcessBuilderDemo getInstance(){
        if(instance == null){
            instance = new ProcessBuilderDemo();
        }
        return instance;
    }

    public void main(String[] args) throws IOException, InterruptedException {
// linea de comando para crear el diagrama
        make();
    }

    public void make() {
        String[] list = {"cmd.exe", "/c", "dot -Tjpg automata1.dot > dfa.jpg"};
        var processBuilder = new ProcessBuilder();

        processBuilder.command(list);
        try {
            var process = processBuilder.start();
            var ret = process.waitFor();
//codigo cero significa que no hay error
            System.out.printf("Codigo de retorno: %d", ret);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
