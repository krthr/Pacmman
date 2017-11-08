package Controllers;

import static Controllers.drawController.drawEdges;
import static Controllers.drawController.drawMap;
import static Controllers.drawController.drawNodes;
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
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.Random;
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
     * Hilos del juego.
     */
    private Thread GHOSTS_THREAD;
    private Thread MAIN;

    /**
     * Caracteres del juego.
     */
    public Models.Pacman PACMAN;

    /**
     * Lista de fantasmas.
     */
    public static Ghost[] GHOSTS;

    private final int FPS = 10;
    private BufferStrategy bs;
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
    private boolean PAUSE;

    /**
     *
     * @param main
     * @param w Ancho del canvas
     * @param h Alto del canvas
     * @param dev ¿Iniciar en modo desarrollo?
     * @throws Exception
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
        PACMAN = new Pacman(468, 366, 2, 2, "Pacman");
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
                        if (PAUSE) {
                            PAUSE = false;
                        } else {
                            PAUSE = true;
                        }

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
     * @param game
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

            showHorWall();
            while (true) {

                while (PAUSE) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.err.println("ERROR (Game): Hubo un error al tratar de pausar el juego.");
                    }
                }

                try {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());

                    drawMap(g);
                    // drawNodes(g);

                    currentTime = System.currentTimeMillis() - startTime;

                    Point next = PACMAN.getNextPos();

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

                    // System.out.println("POS: (" + PACMAN.X() + "," + PACMAN.Y() + ")");
                    PACMAN.draw(g);
                    drawGhosts(g);

                    Thread.sleep(FPS);
                    getBufferStrategy().show();
                } catch (Exception e) {
                    System.out.println("ERROR (Game): Error en el hilo principal. \n" + e.getMessage());
                }

            }
        });
    }

    /**
     * Dibujar todos los fantasma en el tablero.
     *
     * @param g
     */
    private void drawGhosts(Graphics g) {
        for (Ghost temp : GHOSTS) {
            temp.draw(g);
        }
    }

    /**
     * Cargar hilo de los fantasmas
     */
    private void loadGhostsThread() {
        GHOSTS_THREAD = new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                long currentTime = 0;

                Random rn = new Random();

                while (true) {

                    while (PAUSE) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(gameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    try {
                        currentTime = System.currentTimeMillis() - startTime;

                        int temp = rn.nextInt(4 - 1 + 1) + 1;
                        switch (temp) {
                            case 1: {
                                GHOSTS[0].moveUp(currentTime + 1000);
                                break;
                            }
                            case 2: {
                                GHOSTS[0].moveDown(currentTime);
                                break;
                            }
                            case 3: {
                                GHOSTS[0].moveRigth(currentTime);
                                break;
                            }
                            case 4: {
                                GHOSTS[0].moveLeft(currentTime + 1000000);
                                break;
                            }
                        }

                        if (GHOSTS[0].isOut(GHOSTS[0].X(), GHOSTS[0].Y())) {
                            switch (temp) {
                                case 1:
                                    GHOSTS[0].moveDown(currentTime);
                                    break;
                                case 2:
                                    GHOSTS[0].moveUp(currentTime);
                                    break;
                                case 3:
                                    GHOSTS[0].moveLeft(currentTime);
                                    break;
                                case 4:
                                    GHOSTS[0].moveRigth(currentTime);
                                    break;
                            }
                        }

                        Thread.sleep(40);
                    } catch (InterruptedException e) {

                    }
                }

            }

        });
    }

    /**
     * Mostrar una ventana con un mensaje de error.
     */
    private void showMessage(String msg) {
        javax.swing.JOptionPane.showMessageDialog(father, msg, "P A C M M A N", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    void showHorWall() {
        for (int i = 0; i < N_X; i++) {
            System.out.print(i * PRO_X + " ");
        }

        System.out.println("");
        for (int i = 0; i < N_Y; i++) {
            System.out.print(i * PRO_Y + " ");
        }
    }

}
