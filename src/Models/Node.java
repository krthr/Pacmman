package Models;

import static Controllers.GraphController.NODES_COLOR;
import java.awt.Color;

/**
 * Modelo de los nodos del grafo.
 *
 * @author admin
 */
public class Node {

    private final int id;
    private final int posx;
    private final int posy;
    private final Color color;

    /**
     * Constructor de nodo.
     *
     * @param id
     * @param x
     * @param y
     */
    public Node(int id, int x, int y) {
        this.id = id;
        this.posx = x;
        this.posy = y;
        this.color = NODES_COLOR;
    }

    /**
     * ID del nodo.
     *
     * @return
     */
    public int id() {
        return this.id;
    }

    /**
     * Color del nodo.
     *
     * @return
     */
    public Color color() {
        return this.color;
    }

    /**
     * Posición en Y.
     *
     * @return
     */
    public int Y() {
        return this.posy;
    }

    /**
     * Posición en X.
     *
     * @return
     */
    public int X() {
        return this.posx;
    }

    @Override
    public String toString() {
        return this.posx + "," + this.posy;
    }

}
