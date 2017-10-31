package Controllers;

import Models.Ghost;
import Models.Pacman;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author examen
 */
public class Game extends Canvas {

    private Thread GHOSTS_THREAD;
    private Thread MAIN;

    private Pacman PACMAN;
    private Ghost[] GHOSTS;

    private final int FPS = 10;
    private BufferStrategy bs;

    private int[][] mundo = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };

    /**
     *
     * @param w Ancho
     * @param h Alto
     * @throws Exception
     */
    public Game(int w, int h) throws Exception {
        this.setSize(w, h);
        initKeyBoard();
        loadData();
        loadMainThread();
        loadGhostsThread();
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

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: {
                        PACMAN.currentDirection = Pacman.NONE;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        PACMAN.currentDirection = Pacman.NONE;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        PACMAN.currentDirection = Pacman.NONE;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        PACMAN.currentDirection = Pacman.NONE;
                        break;
                    }
                }
            }
        });
    }

    /**
     * Iniciar hilos.
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
        MAIN = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("INFO (Game): Cargando hilo principal...");

                createBufferStrategy(2);
                Graphics g = getBufferStrategy().getDrawGraphics();
                long startTime = System.currentTimeMillis();
                long currentTime = 0;

                while (true) {
                    try {
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        currentTime = System.currentTimeMillis() - startTime;

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
                                System.out.println("Arriba");
                                GHOSTS[0].moveUp(currentTime);
                                break;
                            }
                            case 2: {
                                System.out.println("Abajo");
                                GHOSTS[0].moveDown(currentTime);
                                break;
                            }
                            case 3: {
                                System.out.println("Derecha");
                                GHOSTS[0].moveRigth(currentTime);
                                break;
                            }
                            case 4: {
                                System.out.println("Izquierda");
                                GHOSTS[0].moveLeft(currentTime);
                                break;
                            }
                        }
                        
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }

            }

        });
    }

}
