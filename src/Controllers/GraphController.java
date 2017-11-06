package Controllers;

import Models.Edge;
import Models.Node;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
     * Archivos del grafo.
     */
    private static final File GRAPH_FILE = new File(GraphController.class.getClass().getResource("/Models/graph.txt").getFile());
    private static final File NODES_FILE = new File(GraphController.class.getClass().getResource("/Models/nodes.txt").getFile());
    /**
     * Tamaño de los nodos.
     */
    public static final int TAM_NODOS = 30;
    /**
     * Color de los nodos del grafo.
     */
    public static final Color NODES_COLOR = Color.RED;

    private static int N_ID = 0;

    /**
     * Cargar datos del grafo desde un archivo de texto.
     */
    public static void loadGraph() {
        if (GRAPH_FILE == null) {
            return;
        }

        try {
            BufferedReader graph = new BufferedReader(new FileReader(GRAPH_FILE));
            BufferedReader nodes = new BufferedReader(new FileReader(NODES_FILE));

        } catch (FileNotFoundException ex) {
            System.err.println("ERROR (Graph): Archivo no encontrado. \n" + ex);
        } catch (IOException ex) {
            System.err.println("ERROR (Graph): Ocurrió un error al leer el archivo. \n" + ex);
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
     * Buscar nodo por coordenadas.
     *
     * @param x
     * @param y
     * @return
     */
    public static Node searchNode(int x, int y) {
        for (Node temp : NODES) {
            if (x == temp.X() && y == temp.Y()) {
                return temp;
            }
        }

        return null;
    }

    /**
     * Buscar nodo por cercano a cierta coordenada.
     *
     * @param x
     * @param y
     * @return
     */
    public static Node searchNearNode(int x, int y) {
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

        NODES.add(new Node(N_ID, x, y));
        N_ID++;
    }

    /**
     * Añadir arista a la lista de aristas.
     *
     * @param init
     * @param end
     */
    public static void addEdge(Node init, Node end) {
        if (EDGES == null) {
            EDGES = new ArrayList<>();
        }

        int dist = distance(init.X() + TAM_NODOS / 2, end.X() + TAM_NODOS / 2, init.Y() + TAM_NODOS / 2, end.Y() + TAM_NODOS / 2);
        EDGES.add(new Edge(init, end, dist));
    }

    /**
     * Generar matriz de adyacencia
     */
    public void generateMatriz() {
        if (MATRIZ == null) {
            MATRIZ = new int[NODES.size()][NODES.size()];
        } else {
            return;
        }

        EDGES.forEach((Edge edge) -> {
            MATRIZ[edge.init()][edge.end()] = MATRIZ[edge.end()][edge.init()] = (int) edge.dist();
        });
    }

    /**
     * Seleccionar un nodo en modo visual.
     *
     * @param node Nodo.
     * @param g
     * @param color
     */
    public void selNode(Node node, Graphics g, Color color) {
        g.setColor(color);
        g.drawOval(node.X(), node.Y(), TAM_NODOS - 1, TAM_NODOS - 1);
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

}
