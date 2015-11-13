package components;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {

    public static final Color MAIN_COLOR = new Color(255, 250, 205);
    public static final Color SIDE_COLOR = new Color(255, 219, 0);

    public static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 12);

    public static void setLookAndFill() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(FrameUtils.MAIN_FONT);
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setAutoscrolls(false);
        return textArea;
    }
}
