package Controllers;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Manejador de sonidos.
 *
 * @author krthr
 */
public class Sound {

    private Thread PLAYER;
    private final String MAIN_THEME;
    private String THEME;

    public Sound() {

        MAIN_THEME = "";

        initThread();
    }

    /**
     * Reproducir sonido.
     */
    public void play() {
        EventQueue.invokeLater(() -> {
            if (PLAYER == null) {
                initThread();
            }

            PLAYER.start();
        });
    }

    /**
     * Seleccionar tema musical.
     *
     * @param n
     */
    public void selTheme(int n) {
        switch (n) {
            case 1:
                THEME = MAIN_THEME;
                break;
        }
    }

    /**
     * Inicializar hilo del reproductor.
     */
    private void initThread() {
        this.PLAYER = new Thread(() -> {
            File audioFile = new File(THEME);

            try {

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            } catch (UnsupportedAudioFileException ex) {
                System.err.println("ERROR (Sound): El archivo de audio no es soportado.");
            } catch (IOException ex) {
                System.err.println("ERROR (Sound): No se ha podido reproducir el clip de audio.");
            }
        });
    }

}
