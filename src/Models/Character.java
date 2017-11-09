package Models;

import Controllers.animationController;
import static Controllers.gameController.MAP;
import static Controllers.gameController.PIXELS;
import static Controllers.gameController.PRO_X;
import static Models.Pacman.DOWN;
import static Models.Pacman.LEFT;
import static Models.Pacman.RIGTH;
import static Models.Pacman.UP;
import java.awt.Graphics;
import static Controllers.gameController.isPaused;

/**
 * Clase padre de todos los caracteres del juego.
 *
 * @author krthr
 */
public class Character {

    /**
     * Código de las direcciones.
     */
    public static final int UP = 0,
            RIGTH = 1,
            DOWN = 2,
            LEFT = 3,
            NONE = -1;

    animationController[] animations;
    animationController[] death_animations;
    int x, y, vx, vy;
    String path;
    public int currentAnimation, currentDirection;

    /**
     * Constructor.
     * @param x Posición en X
     * @param y Posición en Y
     * @param vx Velocidad en X
     * @param vy Velocidad en Y
     * @param path (?)
     */
    public Character(int x, int y, int vx, int vy, String path) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.currentDirection = -1;
        animations = new animationController[4];
    }

    /**
     * Obtener posición en x
     *
     * @return
     */
    public int X() {
        return x;
    }

    /**
     * Obtener posición en y
     *
     * @return
     */
    public int Y() {
        return y;
    }

    /**
     * Mover a la derecha.
     *
     * @param time (?)
     */
    public void moveRigth(long time) {
        if (isPaused()) {
            return;
        }
        int x = this.x + PIXELS - vx + 3;
        if (MAP[toI(y + (PIXELS / 2))][toI(x)] == 0
                && MAP[toI(y)][toI(x)] == 0
                && MAP[toI(y + PIXELS)][toI(x)] == 0) {
            this.x += vx;
        }

        currentAnimation = RIGTH;
        animations[RIGTH].update(time);
    }

    /**
     * Mover a la izquierda.
     *
     * @param time (?)
     */
    public void moveLeft(long time) {
        if (isPaused()) {
            return;
        }
        int x = this.x - vx + 1;
        if (MAP[toI(y + (PIXELS / 2))][toI(x)] == 0
                && MAP[toI(y)][toI(x)] == 0
                && MAP[toI(y + PIXELS)][toI(x)] == 0) {
            this.x -= vx;
        }

        currentAnimation = LEFT;
        animations[LEFT].update(time);
    }

    /**
     * Mover a arriba.
     *
     * @param time (?)
     */
    public void moveUp(long time) {
        if (isPaused()) {
            return;
        }
        int y = this.y - vy;
        if (MAP[toI(y)][toI(x + (PIXELS / 2))] == 0
                && MAP[toI(y)][toI(x)] == 0
                && MAP[toI(y)][toI(x + PIXELS - 2)] == 0) {
            this.y -= vy;
        }

        currentAnimation = UP;
        animations[UP].update(time);
    }

    /**
     * Mover abajo.
     *
     * @param time (?)
     */
    public void moveDown(long time) {
        if (isPaused()) {
            return;
        }
        int y = this.y + PIXELS + vy;
        if (MAP[toI(y)][toI(x + (PIXELS / 2))] == 0
                && MAP[toI(y)][toI(x)] == 0
                && MAP[toI(y)][toI(x + PIXELS - 3)] == 0) {
            this.y += vy;
        }

        currentAnimation = DOWN;
        animations[DOWN].update(time);
    }

    private int toI(int n) {
        return n / PRO_X;
    }

    /**
     * Dibujar.
     *
     * @param g Gráfico donde se dibujará.
     */
    public void draw(Graphics g) {
        g.drawImage(animations[currentAnimation].getImage(), x, y, null);
    }

}
