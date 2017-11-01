package Controllers;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Clase principal. 
 * @author krthr
 */
public class Main extends JFrame {
    
    /**
     * Juego.
     */
    static Game game;
    
    /**
     * Constructor.
     * @throws Exception 
     */
    public Main() throws Exception {
        init();
    }

    /**
     * Inicializar UI.
     * @throws Exception 
     */
    public void init() throws Exception {
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        game = new Game(900, 600);
        this.add(game);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main pc;
            try {
                pc = new Main();
                pc.setVisible(true);
                Game.startGame(game);
            } catch (Exception ex) {
                System.out.println("ERROR [CRITICO] (Main): No se pudo iniciar el juego. \n" + ex);
            }
        });
    }

}
