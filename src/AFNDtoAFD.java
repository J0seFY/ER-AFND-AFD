import java.util.*;
public class AFNDtoAFD {
    public static void main(String[] args) {
        // Definir el AFND
        int[][] afnd = {
            {1, 2},         // q0 transiciones a q1 y q2
            {0, 3},         // q1 transiciones a q0 y q3
            {0, 3},         // q2 transiciones a q0 y q3
            {3}             // q3 transición a q3
        };
        int[] finales = {3}; // Estados finales: q3
        Set<Integer> conjuntoInicial = new HashSet<>();
        conjuntoInicial.add(0); // Conjunto inicial: q0

        // Convertir el AFND a AFD
        Map<Set<Integer>, Integer> nuevoEstado = new HashMap<>();
        List<Set<Integer>> estadosAFD = new ArrayList<>();
        estadosAFD.add(conjuntoInicial);
        nuevoEstado.put(conjuntoInicial, 0);
        int[][] afd = new int[estadosAFD.size()][2];
        for (int i = 0; i < estadosAFD.size(); i++) {
            Set<Integer> conjunto = estadosAFD.get(i);
            for (int j = 0; j < 2; j++) {
                Set<Integer> conjuntoDestino = new HashSet<>();
                for (int estado : conjunto) {
                    int[] transiciones = afnd[estado];
                    conjuntoDestino.add(transiciones[j]);
                }
                if (!nuevoEstado.containsKey(conjuntoDestino)) {
                    estadosAFD.add(conjuntoDestino);
                    nuevoEstado.put(conjuntoDestino, estadosAFD.size() - 1);
                }
                afd[i][j] = nuevoEstado.get(conjuntoDestino);
            }
        }

        // Imprimir el AFD resultante
        System.out.println("Estados: " + estadosAFD);
        System.out.println("Estado inicial: " + 0);
        System.out.println("Estados finales: " + finales);
        System.out.println("Transiciones:");
        for (int i = 0; i < afd.length; i++) {
            System.out.println(i + " -> " + afd[i][0] + " (0)");
            System.out.println(i + " -> " + afd[i][1] + " (1)");
        }
    }

}
