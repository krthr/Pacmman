package Controllers;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Clase principal.
 *
 * @author krthr
 */
public class Main extends JFrame {

    /**
     * Juego.
     */
    static Game game;

    /**
     * Constructor.
     *
     * @throws Exception
     */
    public Main() throws Exception {
        init();
    }

    /**
     * Inicializar UI.
     *
     * @throws Exception
     */
    public void init() throws Exception {
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new initBoard(this));
        // game = new Game(900, 600);
        // this.add(game);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main pc;
            try {
                pc = new Main();
                pc.setVisible(true);
            } catch (Exception ex) {
                System.out.println("ERROR [CRITICO] (Main): No se pudo iniciar el juego. \n" + ex);
            }
        });
    }

    class initBoard extends Canvas {

        Thread thread;

        public initBoard(JFrame main) {
            EventQueue.invokeLater(() -> {
                this.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_S: {
                                System.out.println("Iniciar");
                                try {
                                    game = new Game(900, 600);
                                } catch (Exception ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                main.add(game);
                                setVisible(false);
                                break;
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }

                });
                run();
                thread.start();
            });
        }

        public void run() {
            thread = new Thread(() -> {
                createBufferStrategy(2);
                Graphics g = getBufferStrategy().getDrawGraphics();
                while (true) {
                    g.setColor(Color.black);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    
                    g.setColor(Color.red);
                    g.drawRect(120, 120, 100, 100);
                    getBufferStrategy().show();
                }
            });

        }

    }
}
