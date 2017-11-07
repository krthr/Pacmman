package Controllers;

import static Controllers.gameController.MAP;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import java.awt.Graphics;
import static Controllers.graphController.NODES_COLOR;
import static Controllers.graphController.TAM_NODOS;
import static Controllers.graphController.getEdges;
import static Controllers.graphController.getNodes;
import static Controllers.mapController.N_X;
import static Controllers.mapController.N_Y;
import Models.Node;
import java.awt.Color;

/**
 *
 * @author krthr
 */
public class drawController {

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
     * Dibujar todos los nodos.
     *
     * @param g
     */
    public static void drawNodes(Graphics g) {
        if (getNodes() == null) {
            return;
        }

        getNodes().forEach((temp) -> {
            drawNode(g, temp.X(), temp.Y());
        });
    }

    /**
     * Dibujar nodo.
     *
     * @param g
     * @param x Posición en X
     * @param y Posición en Y
     */
    public static void drawNode(Graphics g, int x, int y) {
        g.setColor(NODES_COLOR);
        g.fillOval(x, y, TAM_NODOS, TAM_NODOS);
    }

    /**
     * Dibujar todas las aristas
     *
     * @param g
     */
    public static void drawEdges(Graphics g) {
        if (getEdges() == null) {
            return;
        }

        getEdges().forEach((temp) -> {
            drawEdge(g, temp.getX2(), temp.getX2(), temp.getY1(), temp.getY2());
        });
    }

    /**
     * Dibujar una arista
     *
     * @param g
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     */
    public static void drawEdge(Graphics g, int x1, int x2, int y1, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Dibujar mapa.
     *
     * @param g
     */
    public static void drawMap(Graphics g) {
        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (MAP[i][j] == 1) {
                    g.setColor(Color.black);
                    g.fillRect(j * PRO_X, i * PRO_Y, PRO_X, PRO_Y);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(j * PRO_X, i * PRO_Y, PRO_X, PRO_Y);
                }
            }
        }
    }

}
