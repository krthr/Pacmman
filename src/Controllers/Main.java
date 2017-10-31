/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author krthr
 */
public class Main extends JFrame {

    static Game game;

    public Main() throws Exception {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        game = new Game(800, 600);
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
