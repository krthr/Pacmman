package Controllers;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Manejador de sonidos.
 *
 * @author krthr
 */
public class SoundController {

    private Thread PLAYER;
    private final String MAIN_THEME;
    private String THEME;

    private boolean playCompleted;

    public SoundController() {
        System.out.println(THEME);
        MAIN_THEME = THEME = "BeepBox-Song.wav";        
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
    public void play() {
        
        System.out.println(THEME);
        File audioFile = new File(getClass().getResource("/Assets/Sounds/" + THEME).getFile());

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

}
