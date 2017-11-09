package Models;

import Controllers.animationController;
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
    
}
