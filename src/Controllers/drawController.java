package Controllers;

import static Controllers.gameController.MAP;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import java.awt.Graphics;
import static Controllers.graphController.NODES_COLOR;
import static Controllers.graphController.getEdges;
import static Controllers.graphController.getNodes;
import static Controllers.mapController.N_X;
import static Controllers.mapController.N_Y;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Controlador con las funciones básicas necesarias para dibujar elementos.
 *
 * @author krthr
 */
public class drawController {

    /**
     *
     */
    public final static Image WALL_IMG = (new ImageIcon(drawController.class.getClass()
            .getResource("/Assets/wall.jpg"))).getImage();
    public final static Image[] WAY_IMG = {
        (new ImageIcon(drawController.class.getClass()
            .getResource("/Assets/way.jpg"))).getImage(),
        (new ImageIcon(drawController.class.getClass()
            .getResource("/Assets/way2.jpg"))).getImage()
    };
    /**
     * Color de las paredes.
     */
    public final static Color WALL_COLOR = Color.BLUE;
    /**
     * Color del camino.
     */
    public final static Color WAY_COLOR = Color.BLACK;

    /**
     * Dibujar nodos del grafo.
     *
     * @param g Gráfico donde se dibujará.
     */
    public static void drawNodes(Graphics g) {
        if (getNodes() == null) {
            return;
        }

        getNodes().forEach((temp) -> {
            drawNode(g, temp.X(), temp.Y());
            // drawOvalNode(g, temp.X(), temp.Y());
        });
    }

    /**
     * Dibujar nodo de forma cuadrada.
     *
     * @param g Gráfico donde se dibujará.
     * @param x Posición en X
     * @param y Posición en Y
     */
    public static void drawNode(Graphics g, int x, int y) {
        g.setColor(NODES_COLOR);
        g.drawRect(x, y, PRO_X, PRO_Y);
    }

    /**
     * Dibujar nodo de forma esférica.
     *
     * @param g Gráfico donde se dibujará.
     * @param x Coordenada en X del óvalo
     * @param y Coordenada en Y del óvalo
     */
    public static void drawOvalNode(Graphics g, int x, int y) {
        g.setColor(NODES_COLOR);
        g.fillOval(x + PRO_X / 4, y + PRO_Y / 4, PRO_X / 2, PRO_Y / 2);
    }

    /**
     * Dibujar todas las aristas
     *
     * @param g Gráfico donde se dibujará.
     */
    public static void drawEdges(Graphics g) {
        if (getEdges() == null) {
            return;
        }

        // System.out.println("TAM: " + getEdges().size());
        getEdges().forEach((temp) -> {
            System.out.println(temp.toString());
            drawEdge(g, temp.getX2(), temp.getX2(), temp.getY1(), temp.getY2());
        });
    }

    /**
     * Dibujar una arista
     *
     * @param g Gráfico donde se dibujará.
     * @param x1 X de punto inicial
     * @param x2 X de punto final
     * @param y1 Y de punto inicial
     * @param y2 Y de punto final
     */
    public static void drawEdge(Graphics g, int x1, int x2, int y1, int y2) {
        g.setColor(NODES_COLOR);
        g.drawLine(x1 - PRO_X / 2, y1 - PRO_Y / 2, x2 - PRO_X / 2, y2 - PRO_Y / 2);
    }

    /**
     * Dibujar mapa.
     *
     * @param g Gráfico donde se dibujará.
     */
    public static void drawMap(Graphics g) {
        Random rn = new Random();
        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (MAP[i][j] == 1) {
                    g.drawImage(WALL_IMG, j * PRO_X, i * PRO_Y, PRO_X, PRO_Y, null);
                } else {
                    int a = rn.nextInt(1 - 0 + 1) + 0;
                    g.drawImage(WAY_IMG[0], j * PRO_X, i * PRO_Y, PRO_X, PRO_Y, null);
                }
            }
        }
    }

}
