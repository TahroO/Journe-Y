import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;

/**
 * Write a description of class ImagePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
     * An example of a method - replace this comment with your own
     *
     * @param y a sample parameter for a method
     * @return the sum of x and y
     */
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        super.paintComponent(g);
    }

    public void setImage(String path) {
        image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(path));
        repaint();
    }
}
