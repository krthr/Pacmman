package Models;

import static Controllers.GraphController.TAM_NODOS;

/**
 * Modelo de las aristas del grafo.
 *
 * @author admin
 */
public class Edge {

    private final int init;
    private final int end;
    private final int x1, y1, x2, y2;
    private final int dist;

    /**
     * Constructor de las aristas del grafo.
     *
     * @param init ID del nodo inicial.
     * @param end ID del nodo final.
     * @param x1 X del nodo inicial
     * @param y1 Y del nodo inicial
     * @param x2 X del nodo final
     * @param y2 Y del nodo final
     * @param dist
     */
    public Edge(int init, int end, int x1, int y1, int x2, int y2, int dist) {
        this.init = init;
        this.end = end;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dist = dist;
    }

    /**
     * Constructor de aristas.
     *
     * @param init Nodo inicial
     * @param end Nodo final
     * @param dist
     */
    public Edge(Node init, Node end, int dist) {
        this.init = init.id();
        this.end = end.id();
        this.x1 = init.X() + (TAM_NODOS / 2);
        this.y1 = init.Y() + (TAM_NODOS / 2);
        this.x2 = end.X() + (TAM_NODOS / 2);
        this.y2 = end.Y() + (TAM_NODOS / 2);
        this.dist = dist;
    }

    /**
     * Obtener ID del nodo inicial.
     *
     * @return
     */
    public int init() {
        return this.init;
    }

    /**
     * Obtener ID del nodo final.
     *
     * @return
     */
    public int end() {
        return this.end;
    }

    /**
     * Obtener peso de la arista.
     *
     * @return
     */
    public double dist() {
        return this.dist;
    }

}
