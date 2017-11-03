package Models;

import Controllers.Animation;
import static Controllers.Board.GAME_HEIGHT;
import static Controllers.Board.GAME_WIDTH;
import Controllers.Game;
import static Controllers.Game.GHOSTS;
import static Controllers.Game.MAP;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;
import static Controllers.Game.PIXELS;
import static Controllers.Game.PRO_X;
import static Controllers.Game.PRO_Y;
import Controllers.Map;

/**
 * Modelo de Pacman.
 *
 * @author krthr
 */
public class Pacman {

    public static final int UP = 0,
            RIGTH = 1,
            DOWN = 2,
            LEFT = 3,
            NONE = -1;

    Animation[] animations;
    int x, y, vx, vy;
    String path;
    public int currentAnimation;
    public int currentDirection;

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
        animations = new Animation[4];
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
            animations[j] = new Animation();
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
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(animations[currentAnimation].getImage(), x, y, null);
    }

    /**
     * Obtener posición en X
     *
     * @return
     */
    public int X() {
        return this.x;
    }

    /**
     * Obtener posición en Y
     *
     * @return
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
     * Verificar si Pacman está fuera del tablero.
     *
     * @param p
     * @return
     */
    public boolean isOut(Point p) {
        return p.x < 0 || p.y < 0 || (p.x + 32) > GAME_WIDTH || (p.y + 32) > GAME_HEIGHT;
    }

    /**
     * Verificar si Pacman es tocado por algún fantasma.
     *
     * @return
     */
    public boolean isKilled() {
        for (Ghost temp : GHOSTS) {
            int a = temp.X(), b = temp.Y();
            if (x == a && y == b) {
                return true;
            } else if (x > a && x + PIXELS < a + PIXELS && x < a + PIXELS && y > b && y < b + PIXELS) {
                return true;
            } else if (x < a && x + PIXELS > a && y > b && y + PIXELS > b + PIXELS) {
                return true;
            } else if (x > a && x < a + PIXELS && x + PIXELS > a + PIXELS && y < b && y + PIXELS > b && y + PIXELS < b + PIXELS) {
                return true;
            } else if (x < a && x + PIXELS > a && y > b && y + PIXELS > b) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean touchsWall(Point p) {
        int x = p.x, y = p.y;
        for (int i = 0; i < Map.N_Y; i++) {
            for (int j = 0; j < Map.N_X; j++) {
                if (MAP[i][j] == 1) {
                    int h = j * PRO_X, v = i * PRO_Y;
                    if (x > h && x < (j + 1) * PRO_X && y > v && y < (i + 1) * PRO_Y) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
