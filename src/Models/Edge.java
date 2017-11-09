package Models;

import static Controllers.graphController.TAM_NODOS;
import static Controllers.graphController.searchNode;
import static java.lang.Math.pow;

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
     */
    public Edge(int init, int end, int x1, int y1, int x2, int y2) {
        this.init = init;
        this.end = end;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dist = (int) Math.sqrt(pow(x2 - x1, 2.0) + pow(y2 - y1, 2.0));
    }

    /**
     * Obtener ID del nodo inicial.
     *
     * @return El ID del nodo inicial.
     */
    public int init() {
        return this.init;
    }

    /**
     * Obtener ID del nodo final.
     *
     * @return El ID del nodo final.
     */
    public int end() {
        return this.end;
    }

    /**
     * X inicial
     *
     * @return Obtener la posición en X de la arista inicial
     */
    public int getX1() {
        return x1;
    }

    /**
     * Y inicial
     *
     * @return Obtener la posición en Y de la arista inicial
     */
    public int getY1() {
        return y1;
    }

    /**
     * X final
     *
     * @return Obtener la posición en X de la arista final
     */
    public int getX2() {
        return x2;
    }

    /**
     * Y final
     *
     * @return Obtener la posición en Y de la arista final
     */
    public int getY2() {
        return y2;
    }

    /**
     * Obtener peso de la arista.
     *
     * @return El peso de la arista.
     */
    public int getWeight() {
        return this.dist;
    }

    public Node getSource() {
        return searchNode(init);
    }

    public Node getDestination() {
        return searchNode(end);
    }

    @Override
    public String toString() {
        return this.init + "," + this.end;
    }

}
