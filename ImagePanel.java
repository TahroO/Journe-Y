import javax.swing.*;
import java.awt.*;

/**
 * Used to load and display an image.
 */
public class ImagePanel extends JPanel {
    private Image image;

    /**
     * Constructor for objects of class ImagePanel
     */
    public ImagePanel() {
        image = Toolkit.getDefaultToolkit().createImage("resources/images/rooms/4167399110_waterfall__cyberpunk__HQ__unreal_engine__jungle.png");
    }

    /**
     * Changes displayed image.
     * @param path Image path.
     */
    public void setImage(String path) {
        image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(path));
        ImageUtilities.preload(image);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        super.paintComponent(g);
    }

}
