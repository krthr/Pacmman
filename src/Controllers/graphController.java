package Controllers;

import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import static Controllers.mapController.LEVEL1;
import static Controllers.mapController.N_X;
import static Controllers.mapController.N_Y;
import Models.Edge;
import Models.Node;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

/**
 * Constrolador del grafo.
 *
 * @author krthr
 */
public class graphController {

    /**
     * Matriz de adyacencia.
     */
    private static int[][] MATRIZ;
    /**
     * Lista de los nodos del grafo.
     */
    private static ArrayList<Node> NODES;
    /**
     * Lista de las aristas del grafo.
     */
    private static ArrayList<Edge> EDGES;
    /**
     * Archivos del grafo.
     */
    private static final File EDGES_FILE = new File(graphController.class.getClass().getResource("/Models/edges.txt").getFile());
    private static final File NODES_FILE = new File(graphController.class.getClass().getResource("/Models/nodes.txt").getFile());
    /**
     * Tamaño de los nodos.
     */
    public static final int TAM_NODOS = 30;
    /**
     * Color de los nodos del grafo.
     */
    public static final Color NODES_COLOR = Color.RED;

    private static int N_ID = 0;

    public static int N() {
        return N_ID;
    }

    /**
     * Obtener la lista de nodos.
     *
     * @return Lista de nodos.
     */
    public static ArrayList<Node> getNodes() {
        return NODES;
    }

    /**
     * Obtener la lista de aristas.
     *
     * @return Lista de aristas.
     */
    public static ArrayList<Edge> getEdges() {
        return EDGES;
    }

    /**
     * Cargar datos del grafo desde un archivo de texto.
     */
    public static void loadGraph() {

        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (LEVEL1[i][j] == 0) {
                    addNode(j * PRO_X, i * PRO_Y);
                }
            }
        }

        loadEdges();
        generateMatriz();

//        System.out.println(NODES.size());
//        System.out.println("PROX: " + PRO_X);
//        System.out.println("PROY: " + PRO_Y);
//        System.out.println("FILE: " + NODES_FILE.getAbsolutePath());
//
//        try (BufferedWriter wr = new BufferedWriter(new FileWriter(NODES_FILE))) {
//            for (Node temp : NODES) {
//                System.out.println("id: " + temp.id() + " " + temp.X() + "," + temp.Y());
//                wr.write(temp.X() + "," + temp.Y() + "\n");
//            }
//        } catch (IOException ex) {
//        }
    }

    public static void loadEdges() {
        // TODO
    }

    /**
     * Generar matriz de adyacencia.
     */
    public static void generateMatriz() {
        if (MATRIZ == null) {
            MATRIZ = new int[NODES.size()][NODES.size()];
        }

        if (EDGES == null) {
            return;
        }

        EDGES.forEach((Edge edge) -> {
            MATRIZ[edge.init()][edge.end()] = MATRIZ[edge.end()][edge.init()] = (int) edge.getWeight();
        });
    }

    /**
     * Calcular distancia entre dos puntos.
     *
     * @param x1 X del punto inicial.
     * @param x2 X del punto final.
     * @param y1 Y del punto inicial.
     * @param y2 Y del punto final.
     * @return Distancia entre los dos puntos.
     */
    private static int distance(int x1, int x2, int y1, int y2) {
        double dist = Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
        return (int) dist;
    }

    /**
     * Añadir nuevo nodo al grafo.
     *
     * @param x Posición en X
     * @param y Posición en Y
     */
    private static void addNode(int x, int y) {
        Node temp = new Node(N_ID, x, y, false, null);

        if (NODES == null) {
            NODES = new ArrayList<>();
        }

        NODES.add(temp);
        N_ID++;
    }

    private static void addEdge(int x1, int x2, int y1, int y2) {
        if (EDGES == null) {
            EDGES = new ArrayList<>();
        }

        Node init = searchNode(x1, y1), end = searchNode(x2, y2);

        if (init == null || end == null) {
            return;
        }

        Edge temp = new Edge(init.id(), end.id(), x1, x2, y1, y2);
        EDGES.add(temp);
    }

    /**
     * Buscar nodo.
     *
     * @param x Posición en X
     * @param y Posición en Y
     * @return El nodo, si fue encontrado. NULL si no fue encontrado.
     */
    private static Node searchNode(int x, int y) {
        if (NODES == null) {
            return null;
        }

        for (Node temp : NODES) {
            if (temp.X() == x && temp.Y() == y) {
                return temp;
            }
        }

        return null;
    }

}
