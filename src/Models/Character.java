package Models;

import Controllers.animationController;
import static Controllers.gameController.MAP;
import static Controllers.gameController.PIXELS;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import static Controllers.graphController.getNodes;
import static Controllers.graphController.searchNode;
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
     *
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
     * @return La posición en X
     */
    public int X() {
        return x;
    }

    /**
     * Obtener posición en y
     *
     * @return La posición en Y
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
        int x = this.x + PIXELS + vx;
        if (MAP[xToI(y + (PIXELS / 2))][xToI(x)] == 0
                && MAP[xToI(y)][xToI(x)] == 0
                && MAP[xToI(y + PIXELS)][xToI(x)] == 0) {
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
        int x = this.x - vx;
        if (MAP[xToI(y + (PIXELS / 2))][xToI(x)] == 0
                && MAP[xToI(y)][xToI(x)] == 0
                && MAP[xToI(y + PIXELS)][xToI(x)] == 0) {
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
        if (MAP[xToI(y)][xToI(x + (PIXELS / 2))] == 0
                && MAP[xToI(y)][xToI(x)] == 0
                && MAP[xToI(y)][xToI(x + PIXELS - 2)] == 0) {
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
        if (MAP[xToI(y)][xToI(x + (PIXELS / 2))] == 0
                && MAP[xToI(y)][xToI(x)] == 0
                && MAP[xToI(y)][xToI(x + PIXELS - 3)] == 0) {
            this.y += vy;
        }

        currentAnimation = DOWN;
        animations[DOWN].update(time);
    }

    private int xToI(int n) {
        return n / PRO_X;
    }
    
    private int yToI(int n) {
        return n / PRO_Y;
    }

    /**
     * Dibujar.
     *
     * @param g Gráfico donde se dibujará.
     */
    public void draw(Graphics g) {
        g.drawImage(animations[currentAnimation].getImage(), x, y, null);
    }

    /**
     * Obtener el nodo actual en el que se encuentra.
     *
     * @return El nodo de la posición actual.
     */
    public Node actualNode() {
        Node temp = getNode(x + PIXELS / 2, y + PIXELS / 2);
        return temp;
    }

    /**
     * Obtener nodo según coordenadas.
     *
     * @param x Coordenada en X
     * @param y Coordenada en Y
     * @return El nodo donde las coordenadas se encuentran, si existe.
     */
    private Node getNode(int x, int y) {
        Node se = null;
        for (Node temp : getNodes()) {
            if (x > temp.X() && y > temp.Y() && x < temp.X() + PRO_X && y < temp.Y() + PRO_Y) {
                se = temp;
                break;
            }
        }

        return se;
    }

    /**
     * Asignar valor de la posición en X
     * @param x Valor de la posición en X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Asignar valor de la posición en Y
     * @param y Valor de la posición en Y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    

}
