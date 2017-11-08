package Models;

import Controllers.animationController;
import static Controllers.gameController.LIFES;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 * Modelo de Pacman.
 *
 * @author krthr
 */
public class Pacman {

    /**
     * Código de las direcciones.
     */
    public static final int UP = 0,
            RIGTH = 1,
            DOWN = 2,
            LEFT = 3,
            NONE = -1;

    /**
     * Cantidad de vidas.
     */
    private int lifes;

    animationController[] animations;
    animationController[] death_animations;
    int x, y, vx, vy;
    String path;
    public int currentAnimation, currentDirection;

    /**
     * Pacman.
     *
     * @param x Posicion en x
     * @param y Posicion en y
     * @param vx Velocidad en x
     * @param vy Velocidad en y
     * @param path
     */
    public Pacman(int x, int y, int vx, int vy, String path) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.currentDirection = -1;
        this.lifes = LIFES;
        animations = new animationController[4];
    }

    /**
     * Cargar sprites.
     *
     * @param names
     * @throws Exception
     */
    public void loadPics(String[] names) throws Exception {
        System.out.println("INFO (Pacman): Cargando sprites...");
        for (int j = 0; j < 4; j++) {
            String name = names[j];
            animations[j] = new animationController();
            for (int i = 1; i <= 3; i++) {
                System.out.println("INFO (Pacman): Sprite - " + name + "" + i);
                animations[j].addScene(
                        new ImageIcon(getClass().getResource("/Assets/" + name + i + ".gif")).getImage(),
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
     * @param g Gráfico donde se dibujará.
     */
    public void draw(Graphics g) {
        g.drawImage(animations[currentAnimation].getImage(), x, y, null);
    }

    /**
     * Obtener posición en X
     *
     * @return La posición en X del pacman
     */
    public int X() {
        return this.x;
    }

    /**
     * Obtener posición en Y
     *
     * @return La posición en Y del Pacman
     */
    public int Y() {
        return this.y;
    }

    /**
     * Obtener la futura posición del pacman según movimiento.
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
     * ¿Está Pacman muerto?
     *
     * @return True si Pacman está muerto. False si no
     */
    public boolean isDead() {
        return this.lifes <= 0;
    }


}
