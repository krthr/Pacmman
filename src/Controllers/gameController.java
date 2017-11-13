package Controllers;

import static Controllers.drawController.drawGameOver;
import static Controllers.drawController.drawGhosts;
import static Controllers.drawController.drawBoard;
import static Controllers.drawController.drawWin;
import static Controllers.graphController.loadGraph;
import static Controllers.mapController.*;
import static Models.Pacman.DOWN;
import static Models.Pacman.LEFT;
import static Models.Pacman.NONE;
import static Models.Pacman.RIGTH;
import static Models.Pacman.UP;
import Models.Ghost;
import Models.Node;
import Models.Pacman;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de manejar la lógica y casi todo lo correspondiente al juego.
 *
 * @author krthr
 */
public class gameController extends java.awt.Canvas {

    /**
     * Frame padre del juego.
     */
    private final javax.swing.JFrame father;
    /**
     * Hilo de los fantasma.
     */
    private Thread GHOSTS_THREAD;
    /**
     * Hilo principal.
     */
    private Thread MAIN;
    /**
     * Hilo para mover el fantasma.
     */
    private Thread MOVE_GOST;
    /**
     * Caracteres del juego.
     */
    public static Pacman PACMAN;
    /**
     * Lista de fantasmas.
     */
    public static Ghost[] GHOSTS;
    /**
     * Velocidad para los Hilos.
     */
    public static final int FPS = 10;
    /**
     * Mapa actual
     */
    public static int[][] MAP;
    /**
     * Proporción pixeles-matrix en X.
     */
    public static int PRO_X;
    /**
     * Proporción pixeles-matrix en Y.
     */
    public static int PRO_Y;
    /**
     * Número de vidas en el juego.
     */
    public static int LIFES = 3;
    /**
     * Tamaño en pixeles de los sprites.
     */
    public static int PIXELS;
    private static boolean PAUSE;
    /**
     * Camino con los nodos del camino más corto entre el fantasma y pacman.
     */
    private static LinkedList<Node> SHORTESTPATH;
    /**
     * ¿El juego acabó?
     */
    public static boolean PLAYING = true;
    /**
     * Puntos que tiene el jugados.
     */
    private static int POINTS = 0;
    /**
     * Máxima cantidad de puntos.
     */
    public static int MAX_POINTS = 0;

    public static long startTime;

    /**
     *
     * @param main JFrame donde el Canvas será dibujado.
     * @param w Ancho del canvas
     * @param h Alto del canvas
     * @param dev ¿Iniciar en modo desarrollo?
     * @throws Exception Error
     */
    public gameController(javax.swing.JFrame main, int w, int h, boolean dev) throws Exception {
        this.setSize(w, h + 70);
        this.father = main;
        this.requestFocus();

        initKeyBoard();

        EventQueue.invokeLater(() -> {
            try {
                loadData(w, h);
                loadGraph();
                loadCharacters(w, h);
                loadMainThread();
                loadGhostsThread();
                loadMoveGhostThread();

                MAIN.start();
                GHOSTS_THREAD.start();
                MOVE_GOST.start();
            } catch (Exception ex) {
                System.err.println("ERROR (Game): Error al cargar funciones principales. \n" + Arrays.toString(ex.getStackTrace()));
            }
        });
    }

    int temp = 0;
    Node init = null, end = null;

    /**
     * Cargar datos.
     *
     * @throws Exception
     */
    private void loadData(int w, int h) throws Exception {
        System.out.println("INFO (Game): Cargando datos...");
        MAP = LEVEL1;
        PRO_X = w / 25;
        PRO_Y = h / 15;
        PIXELS = 27;
        PAUSE = false;
    }

    /**
     * Crear caracteres del juego.
     *
     * @param w Ancho
     * @param h Alto
     * @throws Exception
     */
    private void loadCharacters(int w, int h) throws Exception {
        GHOSTS = new Ghost[1];

        GHOSTS[0] = new Ghost(440 + PIXELS / 4, 200 + PIXELS / 4, 2, 2, "Ghost0");
        GHOSTS[0].loadPics(0);

        String[] names = {"arriba", "der", "abajo", "izq"};
        PACMAN = new Pacman(468, 366, 3, 3, "Pacman");
        PACMAN.loadPics(names);
//        PACMAN.currentDirection = RIGTH;
    }

    /**
     * Agregar evento para la lectura del teclado.
     */
    private void initKeyBoard() {
        System.out.println("INFO (Game): Iniciando teclado...");
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: {
                        PACMAN.currentDirection = UP;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        PACMAN.currentDirection = DOWN;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        PACMAN.currentDirection = LEFT;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        PACMAN.currentDirection = RIGTH;
                        break;
                    }
                    case KeyEvent.VK_P: {
                        PAUSE = !PAUSE;
                        break;
                    }
                    case KeyEvent.VK_ENTER: {
                        if (!PLAYING) {
                            System.exit(0);
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: {
                        PACMAN.currentDirection = NONE;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        PACMAN.currentDirection = NONE;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        PACMAN.currentDirection = NONE;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        PACMAN.currentDirection = NONE;
                        break;
                    }
                }
            }
        }
        );
    }

    /**
     * Cargar hilo principal.
     */
    private void loadMainThread() {
        MAIN = new Thread(() -> {
            System.out.println("INFO (Game): Cargando hilo principal...");

            createBufferStrategy(2);
            Graphics g = getBufferStrategy().getDrawGraphics();

            startTime = System.currentTimeMillis();
            long currentTime = 0;

            while (PLAYING) {
                try {
                    if (PACMAN.isDead()) {
                        drawGameOver((Graphics2D) g, POINTS);
                        PLAYING = false;
                    } else if (POINTS == MAX_POINTS) {
                        drawWin((Graphics2D) g, POINTS);
                        PLAYING = false;
                    } else {
                        drawBoard(g, POINTS);
                        // drawController.drawNodes(g);
                        // drawController.drawEdges(g);
                        // drawController.drawPath(g);

                        currentTime = System.currentTimeMillis() - startTime;

                        switch (PACMAN.currentDirection) {
                            case RIGTH: {
                                PACMAN.moveRigth(currentTime);
                                break;
                            }
                            case DOWN: {
                                PACMAN.moveDown(currentTime);
                                break;
                            }
                            case LEFT: {
                                PACMAN.moveLeft(currentTime);
                                break;
                            }
                            case UP: {
                                PACMAN.moveUp(currentTime);
                                break;
                            }
                        }

                        switch (GHOSTS[0].currentDirection) {
                            case RIGTH: {
                                GHOSTS[0].moveRigth(currentTime);

                                break;
                            }
                            case DOWN: {
                                GHOSTS[0].moveDown(currentTime);
                                break;
                            }
                            case LEFT: {
                                GHOSTS[0].moveLeft(currentTime);
                                break;
                            }
                            case UP: {
                                GHOSTS[0].moveUp(currentTime);
                                break;
                            }
                        }

                        if (PACMAN.actualNode() != null && PACMAN.actualNode().isCoin()) {
                            PACMAN.actualNode().setCoint();
                            POINTS += 10;
                        }

                        PACMAN.draw(g);
                        drawGhosts(g);

                        /**
                         * Si Pacman fue tocado por el fantasma, volver a la
                         * posición inicial y disminuir en 1 la vida.
                         */
                        if (PACMAN.touchedByGhost()) {
                            PACMAN.lifes--;

                            PACMAN.setX(468);
                            PACMAN.setY(366);

                            GHOSTS[0].setX(440);
                            GHOSTS[0].setY(200);
                        }
                    }

                    Thread.sleep(FPS);
                    getBufferStrategy().show();
                } catch (InterruptedException e) {
                    System.out.println("ERROR (Game): Error en el hilo principal. \n" + Arrays.toString(e.getStackTrace()));
                }

            }
        });
    }

    /**
     * Cargar hilo de los fantasmas. Calcular el camino mínimo entre cada
     * fantasma y Pacman.
     */
    private void loadGhostsThread() {
        GHOSTS_THREAD = new Thread(() -> {
            while (PLAYING) {
                try {
                    SHORTESTPATH = GHOSTS[0].getSortestPathToPacman();
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {

                }
            }
        });
    }

    /**
     * Cargar hilo encargado del movimiento del fantasma. Movel en fantasma
     * hasta la posición del Pacman.
     */
    private void loadMoveGhostThread() {
        MOVE_GOST = new Thread(() -> {
            long startTime = System.currentTimeMillis();

            while (true) {
                try {
                    LinkedList<Node> temp = getPath();
                    Node pos = GHOSTS[0].actualNode();
                    int x = GHOSTS[0].X(), y = GHOSTS[0].Y();

                    if (GHOSTS[0].actualNode() == PACMAN.actualNode()) {
                        continue;
                    }

                    if (temp == null || temp.size() == 1 || pos == null) {
                        continue;
                    }

//                    System.out.println("---");
//                    System.out.println(pos);
//                    System.out.println(temp.get(1));
//                    System.out.println(x);
                    if (pos.X() == temp.get(1).X()) {
//                        System.out.println("En Y");

                        if (pos.Y() > temp.get(1).Y()) {

                            if (x + PIXELS > temp.get(1).X() + PIXELS) {
                                GHOSTS[0].moveLeft(System.currentTimeMillis() - startTime);
                            } else if (x < temp.get(1).X()) {
                                GHOSTS[0].moveRigth(System.currentTimeMillis() - startTime);
                            }

                            GHOSTS[0].currentDirection = UP;
                        } else {

                            if (x < pos.X()) {
                                GHOSTS[0].moveRigth(System.currentTimeMillis() - startTime);
                            } else if (x + PIXELS > temp.get(1).X() + PIXELS) {
                                GHOSTS[0].moveLeft(System.currentTimeMillis() - startTime);
                            }

                            GHOSTS[0].currentDirection = DOWN;
                        }
                    }

                    if (pos.Y() == temp.get(1).Y()) {
//                        System.out.println("En X");

                        if (pos.X() > temp.get(1).X() - PIXELS) {

                            if (y < pos.Y()) {
                                GHOSTS[0].moveDown(System.currentTimeMillis() - startTime);
                            } else if (y + PIXELS > temp.get(1).Y()) {
                                GHOSTS[0].moveUp(System.currentTimeMillis() - startTime);
                            }

                            GHOSTS[0].currentDirection = LEFT;
                        } else {

                            if (y > pos.Y()) {
                                GHOSTS[0].moveUp(System.currentTimeMillis() - startTime);
                            } else if (y < temp.get(1).Y()) {
                                GHOSTS[0].moveDown(System.currentTimeMillis() - startTime);
                            }

                            GHOSTS[0].currentDirection = RIGTH;
                        }
                    }

                    Thread.sleep(FPS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(gameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Ver si el juego está pausado.
     *
     * @return True si está pausado. False si no está pausado.
     */
    public static boolean isPaused() {
        return PAUSE;
    }

    /**
     * Obtener el camino del fantasma al Pacman
     *
     * @return Un array de nodos
     */
    public static LinkedList<Node> getPath() {
        return SHORTESTPATH;
    }

}
