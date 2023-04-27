/* PASOS PARA CONVERTIR AFND -> AFD
 *                              |           Lenguaje
 * 1. identificar sus elementos | Estado    0         1 
 *                              |    a     {a}      {a,b}
 *                              |  {a,b}   {a,c,d}  {a,b,c}
 * 2. dibujar el priumer estaod de la primera columna
 * 3. todos los estados de su transicion con el lenguaje
 * 4. dibujar el estado siguiente de la primera columna asi como sus estados de transicion
 * 5. si el estado de la trasicion ya existe no se dibuja, solo se hace una transicion
 * 6. todos los finales se unen, asi mismo si se une algun estado no final a uno final ahora sera final.
 */

import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Stack;

public class AFD {

    // Estados
    Estado estado, Inicial, Final;
    // Arrays
    ArrayList<Estado> Lista;
    ArrayList<String> Lenguaje, Iniciales, Finales;
    // Automatas
    Automata afd;
    // Pilas
    Stack<Estado> estadoPila;


    public AFD(Automata automata, ArrayList<String> leng) {

        this.Lista = new ArrayList<>();
        this.Iniciales = new ArrayList<>();
        this.Finales = new ArrayList<>();
        this.estadoPila = new Stack<>();
        this.Lenguaje = leng;
        this.afd = automata;

        // obtenemos los estados desde 0 con transiciones eps.
        estadosEps(afd.inicio);

        Inicial = new Estado(0, true, false); // define el estado inicial
        Final = new Estado(-1, false, false); // define el estado final

        // agregar ciclos
        for (String i : this.Lenguaje) {
            Final.addTransicion(i.charAt(0), Final);
        }

        Inicial.estados.addAll(estadoPila);

        String nombre = "";

        ArrayList<String> EstadosIniciales = new ArrayList<>();

        for (int i = 0; i < EstadosIniciales.size(); i++) {
            EstadosIniciales.add(Inicial.estados.get(i).idString);
        }

        Collections.sort(EstadosIniciales);

        for (int i = 0; i < EstadosIniciales.size(); i++) {
            if (i == EstadosIniciales.size() - 1) {
                nombre = nombre + EstadosIniciales.get(i);
            } else {
                nombre = nombre + EstadosIniciales.get(i) + ",";
            }
        }

        Inicial.idString = nombre;
        estadoPila.clear();

        // agregar estados a la lista de estados para AFD
        this.Lista.add(Inicial);
        this.Lista.add(Final);

        // agregar estado inicial a al pula para calcular nuevos estados
        this.estadoPila.push(Inicial);

        while (this.estadoPila.empty() != true) {
            this.ConvertirAutomata(this.estadoPila.pop());
        }

        writeDot(this.afd);
    }
    // Funciones a utilizar

    // Permite obtener los estados de 0 con transiciones eps
    public void estadosEps(Estado estado) {
        if (estadoPila.contains(estado) == false) {
            this.estadoPila.push(estado);
            if (estado.transiciones.get("eps") != null) {
                for (Estado i : estado.transiciones.get("eps")) {
                    estadosEps(i);
                }
            }
        }
    }

    // Convertir AFD a AFD por creacion de estados
    public void ConvertirAutomata(Estado estado) {
        for (String i : this.Lenguaje) {
            ArrayList<Estado> conexion = new ArrayList<Estado>();
            ArrayList<String> identificadores = new ArrayList<>();

            // obtener estados a los que accede el elemento del string y las transiciones
            // eps

            for (Estado j : estado.estados) {
                if (j.transiciones.containsKey(i) == true) {
                    for (Estado extra : j.transiciones.get(i)) {
                        getConexiones(extra, conexion, identificadores);
                    }
                }
            }

            Collections.sort(identificadores);
            String nombre = "";

            if (identificadores.isEmpty() == false) {
                for (int k = 0; k < identificadores.size(); k++) {
                    if (k == identificadores.size() - 1) {
                        nombre = nombre + identificadores.get(k);
                    } else {
                        nombre = nombre + identificadores.get(k) + ",";
                    }
                }
            }

            // se organizan las conexinoes entre estados
            if (conexion.isEmpty() == false) {
                Estado nuevo = new Estado(Integer.parseInt(nombre), false, false);
                nuevo.estados.addAll(conexion);

                estado.addTransicion(i.charAt(0), nuevo);

                if (existeEstado(nuevo) == false) {
                    this.Lista.add(nuevo);
                    this.estadoPila.push(nuevo);
                }

                conexion.clear();
                identificadores.clear();
            } else {
                estado.addTransicion(i.charAt(0), this.Final);
            }

        }
    }

    public void getConexiones(Estado extra, ArrayList<Estado> conexion, ArrayList<String> identificadores) {
        if (conexion.contains(extra) == false) {
            conexion.add(extra);
            identificadores.add(extra.idString);

            if (extra.transiciones.containsKey("eps") == true) {
                for (Estado i : extra.transiciones.get("eps")) {
                    getConexiones(i, conexion, identificadores);
                }
            }
        }
    }

    public boolean existeEstado(Estado estado) {
        for (Estado est : this.Lista) {
            if (est.idString.equals(estado.idString)) {
                return true;
            }
        }
        return false;
    }

    public void makePic() {
        String[] list = { "cmd.exe", "/c", "dot -Tjpg automata2.dot > dfa.jpg" };

        try {
            ProcessBuilder proceso = new ProcessBuilder();
            proceso.command(list);
            proceso.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Automata getAutomata() {
        return afd;
    }

    private void writeDot(Automata automata) {
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("src\\automata2.dot")))) {
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


