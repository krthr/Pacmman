package Models;

import Controllers.animationController;
import static Controllers.gameController.MAP;
import static Controllers.gameController.PACMAN;
import static Controllers.gameController.PIXELS;
import static Controllers.gameController.PRO_X;
import static Controllers.gameController.PRO_Y;
import static Controllers.gameController.isPaused;
import static Controllers.graphController.dijktra;
import static Models.Character.DOWN;
import static Models.Character.LEFT;
import static Models.Character.RIGTH;
import static Models.Character.UP;
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
     * @param path (?)
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

    /**
     * Generar el camino más corto entre el fantasma y Pacman.
     *
     * @return Lista de nodos que componen el camino.
     */
    public LinkedList getSortestPathToPacman() {
        dijktra().execute(this.actualNode());
        return dijktra().getPath(PACMAN.actualNode());
    }

    /**
     * Mover a la derecha.
     *
     * @param time (?)
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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

}
