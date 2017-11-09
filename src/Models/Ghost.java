package Models;

import Controllers.animationController;
import Controllers.drawController;
import static Controllers.gameController.PACMAN;
import static Controllers.graphController.dijktra;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 * Fantasma.
 *
 * @author krthr
 */
public class Ghost extends Character {

    /**
     * Constructor de Fantasmas.
     *
     * @param x Posicion en x
     * @param y Posicion en y
     * @param vx Velocidad en x
     * @param vy Velocidad en y
     * @param path
     */
    public Ghost(int x, int y, int vx, int vy, String path) {
        super(x, y, vx, vy, path);
    }

    /**
     * Cargar sprites.
     *
     * @param ghost Numero de fantasma.
     * @throws Exception Archivo no encontrado.
     */
    public void loadPics(int ghost) throws Exception {
        System.out.println("INFO (Ghost): Cargando sprites...");
        for (int j = 0; j < 4; j++) {
            animations[j] = new animationController();
            for (int i = 1; i <= 3; i++) {
                System.out.println("INFO (Ghost): Sprite - " + ghost);
                animations[j].addScene(
                        new ImageIcon(getClass().getResource("/Assets/Ghost" + ghost + ".png")).getImage(),
                        100);
            }
        }
    }

    public LinkedList getSortestPathToPacman(Graphics g) {
        Node position = actualNode();
        // System.out.println("X: " + x + " - Y: " + y);
        dijktra().execute(position);

        LinkedList<Node> way = dijktra().getPath(PACMAN.actualNode());

//        if (way != null) {
//            // System.out.println(way);
//            way.forEach((temp) -> {
//                drawController.drawNode(g, temp.X(), temp.Y());
//            });
//        }
        return way;
    }

}
