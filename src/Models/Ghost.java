package Models;

import Controllers.Animation;
import static Controllers.Main.MAX_X;
import static Controllers.Main.MAX_Y;
import static Models.Pacman.DOWN;
import static Models.Pacman.LEFT;
import static Models.Pacman.NONE;
import static Models.Pacman.RIGTH;
import static Models.Pacman.UP;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 * Fantasma.
 *
 * @author krthr
 */
public class Ghost {

    Animation[] animations;
    int x;
    int y;
    int vx;
    int vy;
    String path;
    public int currentAnimation;
    public int currentDirection;

    /**
     *
     * @param x Posicion en x
     * @param y Posicion en y
     * @param vx Velocidad en x
     * @param vy Velocidad en y
     * @param path
     */
    public Ghost(int x, int y, int vx, int vy, String path) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.currentDirection = -1;
        animations = new Animation[4];
    }

    /**
     * Cargar sprites.
     *
     * @param ghost Numero de fantasma.
     * @throws Exception
     */
    public void loadPics(int ghost) throws Exception {
        System.out.println("INFO (Ghost): Cargando sprites...");
        for (int j = 0; j < 4; j++) {
            animations[j] = new Animation();
            for (int i = 1; i <= 3; i++) {
                System.out.println("INFO (Ghost): Sprite - " + ghost);
                animations[j].addScene(
                        new ImageIcon(getClass().getResource("/Assets/Ghost" + ghost + ".gif")).getImage(),
                        100);
            }
        }
    }

    /**
     * Mover a la derecha.
     *
     * @param time
     */
    public void moveRigth(long time) {
        x += vx;
        currentAnimation = RIGTH;
        animations[RIGTH].update(time);
    }

    /**
     * Mover a la izquierda.
     *
     * @param time
     */
    public void moveLeft(long time) {
        x -= vx;
        currentAnimation = LEFT;
        animations[LEFT].update(time);
    }

    /**
     * Mover a arriba.
     *
     * @param time
     */
    public void moveUp(long time) {
        y -= vy;
        currentAnimation = UP;
        animations[UP].update(time);
    }

    /**
     * Mover abajo.
     *
     * @param time
     */
    public void moveDown(long time) {
        y += vy;
        currentAnimation = DOWN;
        animations[DOWN].update(time);
    }

    /**
     * Dibujar.
     *
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(animations[currentAnimation].getImage(), x, y, null);
    }

    /**
     * Obtener la futura posición del fantasma.
     *
     * @return
     */
    public Point getNextPos() {
        Point temp = null;
        switch (currentDirection) {
            case NONE:
                temp = new Point(x, y);
                break;
            case UP:
                temp = new Point(x, y - vy);
                break;
            case RIGTH:
                temp = new Point(x + vx, y);
                break;
            case LEFT:
                temp = new Point(x - vx, y);
                break;
            case DOWN:
                temp = new Point(x, y + vy);
                break;
        }

        return temp;
    }

    /**
     * Obtener posición en x
     * @return 
     */
    public int X() {
        return x;
    }

    /**
     * Obtener posición en y
     * @return 
     */
    public int Y() {
        return y;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public boolean isOut(int x, int y) {
        return x < 0 || y < 0 || (x + 32) > MAX_X || (y + 32) > MAX_Y;
    }

}
