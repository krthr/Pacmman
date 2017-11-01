package Controllers;

import static Controllers.Map.LEVEL1;
import Models.Ghost;
import Models.Pacman;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.Console;
import static java.lang.System.console;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Clase encargada de manejar la lógica y casi todo lo correspondiente al juego.
 *
 * @author krthr
 */
public class Game extends Canvas {

    private JFrame father;

    private Thread GHOSTS_THREAD;
    private Thread MAIN;

    private Pacman PACMAN;
    private Ghost[] GHOSTS;

    private final int FPS = 10;
    private BufferStrategy bs;
    private int[][] MAP;
    private int PRO_X;
    private int PRO_Y;
    private int LIFES;

    /**
     *
     * @param main
     * @param w Ancho del canvas
     * @param h Alto del canvas
     * @throws Exception
     */
    public Game(JFrame main, int w, int h) throws Exception {
        this.setSize(w, h);
        this.father = main;

        EventQueue.invokeLater(() -> {
            try {
                initKeyBoard();
                loadData();
                loadMainThread();
                loadGhostsThread();

                MAIN.start();
                GHOSTS_THREAD.start();
            } catch (Exception ex) {
            }
        });
    }

    /**
     * Cargar datos.
     *
     * @throws Exception
     */
    private void loadData() throws Exception {
        System.out.println("INFO (Game): Cargando datos...");

        GHOSTS = new Ghost[2];
        GHOSTS[0] = new Ghost(50, 50, 2, 2, "Ghost1");
        GHOSTS[1] = new Ghost(getWidth() - 50, getHeight() - 50, 2, 2, "Ghost2");
        for (int i = 0; i < 2; i++) {
            GHOSTS[i].loadPics(i);
        }

        String[] names = {"arriba", "der", "abajo", "izq"};
        PACMAN = new Pacman(getWidth() / 2, getHeight() / 2, 2, 2, "Pacman");
        PACMAN.loadPics(names);

        MAP = LEVEL1;
        PRO_X = 900 / 18;
        PRO_Y = 600 / 10;
        LIFES = 3;
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
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: {
                        PACMAN.currentDirection = Pacman.UP;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        PACMAN.currentDirection = Pacman.DOWN;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        PACMAN.currentDirection = Pacman.LEFT;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        PACMAN.currentDirection = Pacman.RIGTH;
                        break;
                    }
                }
            }
        });
    }

    /**
     * Iniciar hilos.
     *
     * @param game
     */
    public static void startGame(Game game) {
        game.MAIN.start();
        game.GHOSTS_THREAD.start();
    }

    /**
     * Cargar hilo principal
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
                    currentTime = System.currentTimeMillis() - startTime;

                    Point temp = PACMAN.getNextPos();
                    verChoquePared(temp.x, temp.y);

                    switch (PACMAN.currentDirection) {
                        case Pacman.RIGTH: {
                            PACMAN.moveRigth(currentTime);
                            break;
                        }
                        case Pacman.DOWN: {
                            PACMAN.moveDown(currentTime);
                            break;
                        }
                        case Pacman.LEFT: {
                            PACMAN.moveLeft(currentTime);
                            break;
                        }
                        case Pacman.UP: {
                            PACMAN.moveUp(currentTime);
                            break;
                        }
                    }

                    PACMAN.draw(g);
                    GHOSTS[0].draw(g);
                    GHOSTS[1].draw(g);

                    Thread.sleep(FPS);
                    getBufferStrategy().show();
                } catch (Exception e) {
                    System.out.println("ERROR (Game): Error en el hilo principal. \n" + e);
                }
            }
        });
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
                    try {
                        currentTime = System.currentTimeMillis() - startTime;

                        switch (rn.nextInt(3 - 2 + 1) + 2) {
                            case 1: {
                                GHOSTS[0].moveUp(currentTime);
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
                                GHOSTS[0].moveLeft(currentTime);
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
     * Dibujar mapa.
     */
    private void drawMap(Graphics g) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                if (MAP[i][j] == 1) {
                    g.setColor(Color.black);
                    g.fillRect(j * PRO_X, i * PRO_Y, PRO_X, PRO_Y);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(j * PRO_X, i * PRO_Y, PRO_X, PRO_Y);
                }
            }
        }
    }

    /**
     * Comprobar si hay un choque con la pared.
     *
     * @param x
     * @param y
     */
    private void verChoquePared(int x, int y) {
        // TODO
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private void verChoqueGhost(int x, int y) {
        // TODO
    }
}
