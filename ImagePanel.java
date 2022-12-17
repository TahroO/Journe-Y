import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;

/**
 * Write a description of class ImagePanel here.
 * 
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JLabel/Show_animated_GIF_without_using_a_JLabel.htm
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ImagePanel extends JPanel

{
    // instance variables - replace the example below with your own
    private Image image;

    /**
     * Constructor for objects of class ImagePanel
     */
    public ImagePanel()
    {
        // initialise instance variables
        image = Toolkit.getDefaultToolkit().createImage("resources/caveStart.png");
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    public void setImage(String path)
    {
        image = Toolkit.getDefaultToolkit().createImage(path);
        repaint();
    }
}
