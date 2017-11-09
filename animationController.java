package Controllers;

import java.awt.Image;
import java.util.ArrayList;

/**
 * Controlador encargado de la animación de cada caracter.
 *
 * @author krthr
 */
public class animationController {

    private final ArrayList<OneScene> scenes;
    private int sceneIndex;
    private long movieTime;
    private long totalTime;

    public synchronized void start() {
        movieTime = 0;
        sceneIndex = 0;
    }

    public animationController() {
        scenes = new ArrayList<>();
        totalTime = 0;
        start();
    }

    public synchronized void update(long timePassed) {
        if (scenes.size() > 1) {
            if (timePassed > totalTime) {
                movieTime = timePassed - ((int) (timePassed / totalTime)) * totalTime;
                sceneIndex = 0;
            } else {
                movieTime = timePassed;
            }

            while (movieTime > getScene(sceneIndex).endTime) {
                sceneIndex++;
            }
        }
    }

    public synchronized void addScene(Image i, long t) {
        totalTime += t;
        scenes.add(new OneScene(i, totalTime));
    }

    /**
     * Obtener la escena actual.
     * @param x ID de la escena
     * @return La escena
     */
    private OneScene getScene(int x) {
        return scenes.get(x);
    }

    public synchronized Image getImage() {
        if (scenes.isEmpty()) {
            return null;
        } else {
            return getScene(sceneIndex).pic;
        }
    }

    private class OneScene {

        Image pic;
        long endTime;

        public OneScene(Image pic, long endTime) {
            this.pic = pic;
            this.endTime = endTime;
        }
    }
}
