package Controllers;

import static Controllers.gameController.MAP;
import static Controllers.gameController.MAX_POINTS;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import static Controllers.mapController.N_X;
import static Controllers.mapController.N_Y;
import Models.Edge;
import Models.Node;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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
     * Tamaño de los nodos.
     */
    public static final int TAM_NODOS = 30;
    /**
     * Color de los nodos del grafo.
     */
    public static final Color NODES_COLOR = Color.RED;
    /**
     * Objeto para la utilización de Dijkstra.
     */
    private static dijkstraController DIJKSTRA;

    private static int N_ID = 0;

    private static int[] COINS_NODES;

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
     * Cargar todos los datos relacionados al grafo.
     */
    public static void loadGraph() {
        generateCoinsNodes();
        loadNodes();
        loadEdges();
        generateMatriz();
        DIJKSTRA = new dijkstraController();
    }

    public static dijkstraController dijktra() {
        return DIJKSTRA;
    }

    /**
     * Cargar nodos del grafo.
     */
    private static void loadNodes() {

        if (NODES == null) {
            NODES = new ArrayList<>();
        }

        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (MAP[i][j] == 0) {
                    addNode(j * PRO_X, i * PRO_Y);
                }
            }
        }
    }

    /**
     * Cargar aristas del grafo.
     */
    private static void loadEdges() {
        if (EDGES == null) {
            EDGES = new ArrayList<>();
        }

        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (MAP[i][j] == 0) {
                    if (MAP[i + 1][j] == 0) {
                        addEdge(j * PRO_X, j * PRO_X, i * PRO_Y, (i + 1) * PRO_X);
                    }
                    if (MAP[i][j + 1] == 0) {
                        addEdge(j * PRO_X, (j + 1) * PRO_X, i * PRO_Y, i * PRO_X);
                    }
                    if (MAP[i - 1][j] == 0) {
                        addEdge(j * PRO_X, j * PRO_X, i * PRO_Y, (i - 1) * PRO_X);
                    }
                    if (MAP[i][j - 1] == 0) {
                        addEdge(j * PRO_X, (j - 1) * PRO_X, i * PRO_Y, i * PRO_X);
                    }
                }
            }
        }
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

        Node temp = new Node(N_ID, x, y, false, null);;

        for (int i = 0; i < 10; i++) {
            if (COINS_NODES[i] == N_ID) {
                temp = new Node(N_ID, x, y, true, null);
                break;
            }
        }

        NODES.add(temp);
        N_ID++;
    }

    private static void generateCoinsNodes() {
        COINS_NODES = new int[10];
        Random rn = new Random();
        for (int i = 0; i < 10; i++) {
            COINS_NODES[i] = rn.nextInt(185 - 1 + 1) + 1;
            MAX_POINTS += 10;
        }
    }

    /**
     * Añadir arista.
     *
     * @param x1 X del nodo inicial
     * @param x2 X del nodo final
     * @param y1 Y del nodo inicial
     * @param y2 Y del nodo final
     */
    private static void addEdge(int x1, int x2, int y1, int y2) {

        Node init = searchNode(x1, y1), end = searchNode(x2, y2);

        if (init == null || end == null) {
            return;
        }

        Edge temp = new Edge(init.id(), end.id(), x1, y1, x2, y2);
        EDGES.add(temp);
    }

    /**
     * Buscar nodo.
     *
     * @param x Posición en X
     * @param y Posición en Y
     * @return El nodo, si fue encontrado. NULL si no fue encontrado.
     */
    public static Node searchNode(int x, int y) {
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

    /**
     * Buscar nodo por ID.
     *
     * @param id ID del nodo a buscar.
     * @return NULL si no fue encontrado el nodo o si la lista de nodos es NULL.
     * Retorna el nodo si fue encontrado.
     */
    public static Node searchNode(int id) {
        if (NODES == null) {
            return null;
        }

        for (Node temp : NODES) {
            if (temp.id() == id) {
                return temp;
            }
        }

        return null;
    }

}
