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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final File EDGES_FILE = new File(GraphController.class.getClass().getResource("/Models/edges.txt").getFile());
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
     * Obtener la lista de nodos.
     *
     * @return
     */
    public static ArrayList<Node> getNodes() {
        return NODES;
    }

    /**
     * Obtener la lista de aristas.
     *
     * @return
     */
    public static ArrayList<Edge> getEdges() {
        return EDGES;
    }

    /**
     * Cargar datos del grafo desde un archivo de texto.
     *
     * @throws java.io.IOException
     */
    public static void loadGraph() throws IOException {
        loadNodes();
        loadEdges();
        generateMatriz();
    }

    /**
     * Cargar aristas del grafo.
     *
     * @throws FileNotFoundException
     */
    public static void loadEdges() throws FileNotFoundException, IOException {
        BufferedReader edges = new BufferedReader(new FileReader(EDGES_FILE));

        String temp;
        while (true) {
            temp = edges.readLine();

            if (temp == null || temp.equals("")) {
                break;
            }

            String[] nodes = temp.split(",");
            int init = toInt(nodes[0]), end = toInt(nodes[1]);

            Node a = searchNode(init), b = searchNode(end);

            addEdge(a, b);
        }
    }

    /**
     * Cargar nodos del grafo.
     *
     * @throws IOException
     */
    public static void loadNodes() throws IOException {

        BufferedReader nodes = new BufferedReader(new FileReader(NODES_FILE));

        String temp;

        while (true) {
            temp = nodes.readLine();

            if (temp == null || temp.equals("")) {
                break;
            }

            String[] point = temp.split(",");
            int x = toInt(point[0]), y = toInt(point[1]);

            addNode(x, y);
        }

    }

    private static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {

            System.err.println("ERROR (Graph): Error al convertir un string a entero. \n" + ex);
            return -1;
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
     * @param x Posición en x
     * @param y Posición en Y
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
    public static void generateMatriz() {
        if (MATRIZ == null) {
            MATRIZ = new int[NODES.size()][NODES.size()];
        } else {
            return;
        }

        EDGES.forEach((Edge edge) -> {
            MATRIZ[edge.init()][edge.end()] = MATRIZ[edge.end()][edge.init()] = (int) edge.getWeight();
        });
    }

    /**
     * Seleccionar un nodo en modo visual.
     *
     * @param node Nodo.
     * @param g
     * @param color
     */
    public static void selNode(Node node, Graphics g, Color color) {
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
