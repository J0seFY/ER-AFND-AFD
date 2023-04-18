import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String expresionRegular = sc.next();
        ProcesadorER letra = new ProcesadorER(expresionRegular);

        System.out.print(letra.getExpresionProcesada());
    }
}
