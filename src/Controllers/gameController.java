package Controllers;

import static Controllers.drawController.drawGhosts;
import static Controllers.drawController.drawMap;
import static Controllers.drawController.drawPath;
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
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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
    private final int FPS = 10;
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
    public static final int LIFES = 3;
    /**
     * Tamaño en pixeles de los sprites.
     */
    public static int PIXELS;
    private static boolean PAUSE;
    /**
     * Camino con los nodos del camino más corto entre el fantasma y pacman.
     */
    private static LinkedList<Node> SORTESTPATH;

    /**
     *
     * @param main JFrame donde el Canvas será dibujado.
     * @param w Ancho del canvas
     * @param h Alto del canvas
     * @param dev ¿Iniciar en modo desarrollo?
     * @throws Exception Error
     */
    public gameController(javax.swing.JFrame main, int w, int h, boolean dev) throws Exception {
        this.setSize(w, h);
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

                MAIN.start();
                GHOSTS_THREAD.start();
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
        PIXELS = 22;
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

        GHOSTS[0] = new Ghost(468, 280, 5, 5, "Ghost0");
        GHOSTS[0].loadPics(0);

        String[] names = {"arriba", "der", "abajo", "izq"};
        PACMAN = new Pacman(468, 366, 3, 3, "Pacman");
        PACMAN.loadPics(names);
        PACMAN.currentDirection = RIGTH;
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
     * Iniciar hilos.
     *
     * @param game Juego que será iniciado.
     */
    public static void startGame(Controllers.gameController game) {
        game.MAIN.start();
        game.GHOSTS_THREAD.start();
    }

    /**
     * Cargar hilo principal.
     */
    private void loadMainThread() {
        MAIN = new Thread(() -> {
            System.out.println("INFO (Game): Cargando hilo principal...");

            createBufferStrategy(2);
            Graphics g = getBufferStrategy().getDrawGraphics();

            long startTime = System.currentTimeMillis();
            long currentTime = 0;

            while (true) {

                try {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());

                    drawMap(g);
                    // drawNodes(g);
                    // drawEdges(g);
                    drawPath(g);

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

                    PACMAN.draw(g);
                    drawGhosts(g);

                    Thread.sleep(FPS);
                    getBufferStrategy().show();
                } catch (Exception e) {
                    System.out.println("ERROR (Game): Error en el hilo principal. \n" + Arrays.toString(e.getStackTrace()));
                }

            }
        });
    }

    /**
     * Cargar hilo de los fantasmas
     */
    private void loadGhostsThread() {
        GHOSTS_THREAD = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long currentTime = 0;
            
            Random rn = new Random();
            
            while (true) {
                try {
                    currentTime = System.currentTimeMillis() - startTime;
                    SORTESTPATH = GHOSTS[0].getSortestPathToPacman();
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    
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

    public static LinkedList<Node> getPath() {
        return SORTESTPATH;
    }
}
