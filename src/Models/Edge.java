package Models;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
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
    private final double dist;

    /**
     * Constructor de las aristas del grafo.
     *
     * @param init ID del nodo inicial.
     * @param end ID del nodo final.
     * @param x1 X del nodo inicial
     * @param y1 Y del nodo inicial
     * @param x2 X del nodo final
     * @param y2 Y del nodo final
     */
    public Edge(int init, int end, int x1, int y1, int x2, int y2) {
        this.init = init;
        this.end = end;
        this.x1 = x1 + (TAM_NODOS / 2);
        this.y1 = y1 + (TAM_NODOS / 2);
        this.x2 = x2 + (TAM_NODOS / 2);
        this.y2 = y2 + (TAM_NODOS / 2);
        this.dist = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }

    /**
     * Constructor de aristas.
     *
     * @param init Nodo inicial
     * @param end Nodo final
     */
    public Edge(Node init, Node end) {
        this.init = init.id();
        this.end = end.id();
        this.x1 = init.X() + (TAM_NODOS / 2);
        this.y1 = init.Y() + (TAM_NODOS / 2);
        this.x2 = end.X() + (TAM_NODOS / 2);
        this.y2 = end.Y() + (TAM_NODOS / 2);
        this.dist = sqrt(pow(end.X() - init.X(), 2) + pow(end.Y() - init.Y(), 2));
    }
    
    
    /**
     * Obtener ID del nodo inicial.
     * @return 
     */
    public int init() {
        return this.init;
    }
    
    /**
     * Obtener ID del nodo final.
     * @return 
     */
    public int end() {
        return this.end;
    }
    
    /**
     * Obtener peso de la arista.
     * @return 
     */
    public double dist() {
        return this.dist;
    }
    
}
