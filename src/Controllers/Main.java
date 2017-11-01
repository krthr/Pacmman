package Controllers;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
        this.add(new initBoard(this, 900, 600));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main pc;
            try {
                pc = new Main();
                pc.setVisible(true);

                Sound s = new Sound();
                s.play();
            } catch (Exception ex) {
                System.out.println("ERROR [CRITICO] (Main): No se pudo iniciar el juego. \n" + ex);
            }
        });
    }

    /**
     *
     */
    class initBoard extends Canvas {

        Thread thread;
        int w, h;

        public initBoard(JFrame main, int w, int h) {
            EventQueue.invokeLater(() -> {
                this.w = w;
                this.h = h;
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
                                    game = new Game(main, 900, 600);
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
                int i = 0;
                while (true) {
                    g.setColor(Color.black);
                    g.fillRect(0, 0, w, 700);

                    g.setColor(Color.red);
                    g.drawRect(100, 100, w - 200, 200);

                    g.setFont(new Font("TimesRoman", Font.PLAIN, 55));
                    g.setColor(Color.YELLOW);
                    g.drawString("P A C M A N", w / 2 - 150, 220);

                    g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

                    if (i == 40) {
                        i = 0;
                        g.setColor(Color.black);
                    } else {
                        g.setColor(Color.white);
                        i++;
                    }

                    g.drawString("Press 's'", w / 2 - 80, 500);

                    getBufferStrategy().show();
                }
            });

        }

    }
}
