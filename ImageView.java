import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Game image view.
 */
public class ImageView {
    public static final int IMAGE_WIDTH = 832;
    public static final int IMAGE_HEIGHT = 704;
    public static final int FONT_SIZE = 14;
    private final Color TEXT_BACKGROUND_COLOR = new Color(0, 0, 0, 180);
    private final Color TEXT_BORDER_COLOR = Color.BLACK;
    private MusicPlayer player = new MusicPlayer();
    private String currentAudioPath;

    JFrame frame;
    ImagePanel imagePanel;
    JEditorPane textPane;

    /**
     * Creates a new ImageView instance.
     *
     * @param commandListener
     */
    public ImageView(ActionListener commandListener) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imagePanel = createImagePanel();
        frame.add(imagePanel, BorderLayout.NORTH);
        JTextField textField = createTextField();
        textField.addActionListener(commandListener);
        frame.add(textField, BorderLayout.SOUTH);
        frame.setLocation(100, 100);
        frame.setResizable(false);
        frame.pack();
        textField.requestFocus();
    }

    private String getCmdPrompt(Command cmd) {
        if (cmd == null) {
            return "";
        }

        StringBuilder output = new StringBuilder("> ");
        if (cmd.getCommandWord() == CommandWord.UNKNOWN) {
            output.append(cmd.getInputLine());
        }
        else {
            output.append(cmd.getCommandWord().label)
                    .append(cmd.hasSecondWord() ? " " + cmd.getSecondWord() : "");
        }
        return  output.append("\n\n").toString();
    }

    /**
     * Sets textPane's text and adjusts its height to fit content heigt.
     *
     * @param cmd A command object.
     * @param text Text to display.
     */
    public void setText(Command cmd, String... text) {
        String command = getCmdPrompt(cmd);
        try {
            Document doc = textPane.getDocument();
            doc.remove(0, doc.getLength());
            doc.insertString(0, command + String.join("\n", text).trim(), null);
            Dimension d = textPane.getPreferredSize();
            Rectangle2D r = textPane.modelToView2D(textPane.getDocument().getLength());
            d.height = (int) Math.round(r.getY() + r.getHeight() + 3);
            textPane.setPreferredSize(d);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setText(String... text) {
        setText(null, text);
    }

    /**
     * Starts playing a new audio file.
     *
     * @param audioPath Path to audio file.
     */
    private void setAudio(String audioPath) {
        if (audioPath == null) {
            return;
        }
        String normalizedAudioPath = audioPath.replaceAll("^/+", "");
        if (!normalizedAudioPath.equals(currentAudioPath)) {
            player.stop();
            player.startPlaying(normalizedAudioPath);
            currentAudioPath = normalizedAudioPath;
        }
    }

    /**
     * Shows the new room - image, description and audio.
     *
     * @param cmd The "go" command object.
     * @param room The new room.
     */
    public void changeRoom(Command cmd, Room room) {
        // Set image.
        imagePanel.setImage(room.getImagePath());
        // Set audio.
        setAudio(room.getAudioPath());
        // Show description.
        setText(getCmdPrompt(cmd) + room.getLocationInfo());
        Logger.getLogger("Journey:Y").log(Level.INFO, "room #%s (%s)".formatted(room.getId(), room.getSourceFile()));
    }

    /**
     * Sets this JFrame visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Closes this JFrame.
     */
    public void quit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Creates a JTextField component for user input.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField(0);
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setCaret(new BlockCaret());
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setMargin(new Insets(5, 5, 5, 5));
        return textField;
    }

    /**
     * Creates a JTextPane for text output.
     */
    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(TEXT_BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(TEXT_BORDER_COLOR);
                g.drawRect(1, 1, getWidth() - 3, getHeight() + 2);
                super.paintComponent(g);
            }
        };
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setOpaque(false);
        textPane.setForeground(Color.WHITE);
        textPane.setMargin(new Insets(10, 10, 10, 10));
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        textPane.setPreferredSize(new Dimension((int) (IMAGE_WIDTH * 0.75), FONT_SIZE + 5));
        return textPane;
    }

    /**
     * Creates an ImagePanel component to show images.
     */
    private ImagePanel createImagePanel() {
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imagePanel.setOpaque(false);
        textPane = createTextPane();
        textPane.setText("test text");
        imagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 0;
        imagePanel.add(textPane, gbc);
        return imagePanel;
    }

    /**
     * Custom block caret for JTextInput.
     */
    private static class BlockCaret extends DefaultCaret {
        private String mark = "█";

        public BlockCaret() {
            setBlinkRate(500);
        }

        @Override
        protected synchronized void damage(Rectangle r) {
            if (r == null) {
                return;
            }

            JTextComponent comp = getComponent();
            FontMetrics fm = comp.getFontMetrics(comp.getFont());
            int textWidth = fm.stringWidth(mark);
            int textHeight = fm.getHeight();
            x = r.x;
            y = r.y;
            width = textWidth;
            height = textHeight;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            JTextComponent comp = getComponent();
            if (comp == null) {
                return;
            }

            int dot = getDot();
            Rectangle r;
            try {
                r = comp.modelToView(dot);
            } catch (BadLocationException e) {
                return;
            }
            if (r == null) {
                return;
            }

            if ((x != r.x) || (y != r.y)) {
                // Erase previous location of caret.
                repaint();
                damage(r);
            }

            if (isVisible()) {
                FontMetrics fm = comp.getFontMetrics(comp.getFont());
                g.setColor(comp.getCaretColor());
                g.drawString(mark, x, y + fm.getAscent());
            }
        }
    }
}
