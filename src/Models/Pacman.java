package Models;

import Controllers.animationController;
import static Controllers.gameController.GHOSTS;
import static Controllers.gameController.LIFES;
import javax.swing.ImageIcon;

/**
 * Modelo de Pacman.
 *
 * @author krthr
 */
public class Pacman extends Character {

    /**
     * Cantidad de vidas.
     */
    public int lifes;

    /**
     * Pacman.
     *
     * @param x Posicion en x
     * @param y Posicion en y
     * @param vx Velocidad en x
     * @param vy Velocidad en y
     * @param path (?)
     */
    public Pacman(int x, int y, int vx, int vy, String path) {
        super(x, y, vx, vy, path);
        this.lifes = LIFES;
    }

    /**
     * Cargar sprites.
     *
     * @param names Vector con los nombres de las imágenes.
     * @throws Exception Si no existe (o se encuentra) el archivo
     */
    public void loadPics(String[] names) throws Exception {
        System.out.println("INFO (Pacman): Cargando sprites...");
        for (int j = 0; j < 4; j++) {
            String name = names[j];
            animations[j] = new animationController();
            for (int i = 1; i <= 3; i++) {
                System.out.println("INFO (Pacman): Sprite - " + name + "" + i);
                animations[j].addScene(
                        new ImageIcon(getClass().getResource("/Assets/" + name + i + ".png")).getImage(),
                        100);
            }
        }

    }

    /**
     * ¿Está Pacman muerto?
     *
     * @return True si Pacman está muerto. False si no
     */
    public boolean isDead() {
        return this.lifes <= 0;
    }
    
    /**
     * 
     * @return 
     */
    public boolean touchedByGhost() {
        for (Ghost temp : GHOSTS) {
            if (temp.actualNode() == this.actualNode()) {
                return true;
            }
        }
        
        return false;
    }

}
