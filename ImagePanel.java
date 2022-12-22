import javax.swing.*;
import java.awt.*;

/**
 * Used to load and display an image.
 */
public class ImagePanel extends JPanel {
    private Image image;

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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getWidth());
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        super.paintComponent(g);
    }

}
