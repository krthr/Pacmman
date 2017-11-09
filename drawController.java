package Controllers;

import static Controllers.gameController.GHOSTS;
import static Controllers.gameController.MAP;
import static Controllers.gameController.PIXELS;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import static Controllers.gameController.getPath;
import java.awt.Graphics;
import static Controllers.graphController.NODES_COLOR;
import static Controllers.graphController.getEdges;
import static Controllers.graphController.getNodes;
import static Controllers.graphController.searchNode;
import static Controllers.mapController.N_X;
import static Controllers.mapController.N_Y;
import Models.Ghost;
import Models.Node;
import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
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

        getEdges().forEach((temp) -> {
            drawEdge(g, temp.getX1() + PRO_X/2, temp.getX2() + PRO_X /2, temp.getY1() + PRO_Y / 2, temp.getY2() + PRO_Y / 2);
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
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Dibujar mapa.
     *
     * @param g Gráfico donde se dibujará.
     */
    public static void drawMap(Graphics g) {
        for (int i = 0; i < N_Y; i++) {
            for (int j = 0; j < N_X; j++) {
                if (MAP[i][j] == 1) {
                    g.drawImage(WALL_IMG, j * PRO_X, i * PRO_Y, PRO_X, PRO_Y, null);
                } else {
                    g.drawImage(WAY_IMG[0], j * PRO_X, i * PRO_Y, PRO_X, PRO_Y, null);
                    
                    if (searchNode(j * PRO_X, i * PRO_Y).isCoin()) {
                        g.setColor(Color.white);
                        g.fillOval(j * PRO_X + PIXELS / 2, i * PRO_Y + PIXELS / 2, PIXELS / 2, PIXELS / 2);
                    }
                    
                    
                }
            }
        }
    }
    
    /**
     * Dibujar todos los fantasma en el tablero.
     *
     * @param g
     */
    public static void drawGhosts(Graphics g) {
        for (Ghost temp : GHOSTS) {
            temp.draw(g);
        }
    }
    
    /**
     * Dibujar el camino más corto entre el fantasma y Pacman.
     * @param g 
     */
    public static void drawPath(Graphics g) {
        if (getPath() == null) return;
        
        for (LinkedList<Node> path : getPath()) {
            
            if (path == null) return;
            
            path.forEach((temp) -> {
                drawController.drawNode(g, temp.X(), temp.Y());
            });
        }
    }

}
