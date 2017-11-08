package Models;

import static Controllers.graphController.NODES_COLOR;
import java.awt.Color;
import java.awt.Image;

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
    private final boolean isCoin;
    private final Image image;

    /**
     * Constructor de nodo.
     *
     * @param id ID del nodo.
     * @param x Posición en X
     * @param y Posición en Y
     * @param isCoin ¿El nodo tiene un coin?
     * @param image Imágen del camino
     */
    public Node(int id, int x, int y, boolean isCoin, Image image) {
        this.id = id;
        this.posx = x;
        this.posy = y;
        this.color = NODES_COLOR;
        this.isCoin = isCoin;
        this.image = image;
    }

    /**
     * ID del nodo.
     *
     * @return El ID del nodo.
     */
    public int id() {
        return this.id;
    }

    /**
     * Color del nodo.
     *
     * @return El color del nodo.
     */
    public Color color() {
        return this.color;
    }

    /**
     * Posición en Y.
     *
     * @return La posición en Y del nodo
     */
    public int Y() {
        return this.posy;
    }

    /**
     * Posición en X.
     *
     * @return La posición en X del nodo
     */
    public int X() {
        return this.posx;
    }

    @Override
    public String toString() {
        return this.posx + "," + this.posy;
    }

}
