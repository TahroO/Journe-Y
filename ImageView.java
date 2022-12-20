import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ImageView {
    public static final int IMAGE_WIDTH = 832;
    public static final int IMAGE_HEIGHT = 704;
    public static final int FONT_SIZE = 14;

    private final Color TEXT_BACKGROUND_COLOR = new Color(0, 0, 0, 180);
    private final Color TEXT_BORDER_COLOR = Color.BLACK;

    private MusicPlayer player = new MusicPlayer();


    JFrame frame;
    ImagePanel imagePanel;
    JEditorPane textPane;

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
        if (cmd.isUnknown()) {
            return "> " + cmd.getInputLine() + "\n\n";
        }
        return "> " + cmd.getCommandWord().label + (cmd.hasSecondWord() ? " " + cmd.getSecondWord() : "") + "\n\n";
    }

    public void setText(Command cmd, String... text) {
        String command = getCmdPrompt(cmd);
        try {
            Document doc = textPane.getDocument();
            doc.remove(0, doc.getLength());
            doc.insertString(0, command + String.join("\n", text).trim(), null);
            Dimension d = textPane.getPreferredSize();
            Rectangle r = textPane.modelToView(textPane.getDocument().getLength());
            d.height = r.y + r.height + 3;
            textPane.setPreferredSize(d);
        } catch (Exception e2) {
        }

    }

    public void setText(String... text) {
        setText(null, text);
    }

    public void changeRoom(Command cmd, Room room) {
        // Set image and audio.
        imagePanel.setImage(room.getImagePath());
        String audio = room.getAudioPath();
        if (audio != null) {
            player.stop();
            player.startPlaying(audio);
        }
        // Show description.
        setText(getCmdPrompt(cmd) + room.getLocationInfo());
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

    public class BlockCaret extends DefaultCaret {
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
