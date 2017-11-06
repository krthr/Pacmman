package Controllers;

import Models.Edge;
import Models.Node;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

/**
 * Constrolador del grafo.
 *
 * @author krthr
 */
public class GraphController {

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
     * Archivo del grafo.
     */
    private static final File GRAPH_FILE = new File("");
    /**
     * Tamaño de los nodos.
     */
    public static final int TAM_NODOS = 30;
    /**
     * Color de los nodos del grafo.
     */
    public static final Color NODES_COLOR = Color.RED;

    private static int temp_id = 0;

    /**
     * Cargar datos del grafo desde un archivo de texto.
     */
    public static void loadGraph() {
        if (GRAPH_FILE == null) {
            return;
        }
    }

    /**
     * Buscar nodo usando ID.
     *
     * @param id ID del nodo.
     * @return
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

    /**
     * Buscar nodo por coordenada.
     * @param x
     * @param y
     * @return 
     */
    public static Node searchNode(int x, int y) {
        for (Node temp : NODES) {
            if (x >= temp.X() && x <= temp.X() + TAM_NODOS
                    && y >= temp.Y() && y <= temp.Y() + TAM_NODOS) {
                return temp;
            }
        }

        return null;
    }

    /**
     * Verificar si existe un nodo con el ID.
     *
     * @param id
     * @return
     */
    public static boolean isNode(int id) {
        if (NODES == null) {
            return false;
        }

        return NODES.stream().anyMatch((temp) -> (temp.id() == id));
    }

    /**
     * Añadir nodo a la lista de nodos.
     *
     * @param x
     * @param y
     */
    public static void addNode(int x, int y) {
        if (NODES == null) {
            NODES = new ArrayList<>();
        }

        NODES.add(new Node(temp_id, x, y));
        temp_id++;
    }
    
    public static void addEdge(Node init, Node end) {
        if (EDGES == null) {
            EDGES = new ArrayList<>();
        }
        
        EDGES.add(new Edge(init, end));
    }
    
    /**
     * Generar matriz de adyacencia
     */
    public void generateMatriz() {
        if (MATRIZ == null) MATRIZ  = new int[NODES.size()][NODES.size()];
        else return;
        
        EDGES.forEach((Edge edge) -> {
            MATRIZ[edge.init()][edge.end()] = MATRIZ[edge.end()][edge.init()] = (int) edge.dist();
        });
    }
    
    public void selNode(Node node, Graphics g) {
        
    }

}
