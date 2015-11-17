package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public static JPanel createLabelGridPanel(int alignment, String... strings) {
        JPanel panel = createPanel(strings.length, 6);
        for (String s : strings) {
            JLabel label = new JLabel(s, alignment);
            panel.add(label);
        }
        return panel;
    }

    public static JPanel createLabelGridPanel(int alignment, Font font, int distance, String... strings) {
        JPanel panel = createPanel(strings.length, distance);
        for (String s : strings) {
            JLabel label = new JLabel(s, alignment);
            label.setFont(font);
            panel.add(label);
        }
        return panel;
    }

    public static JPanel createComponentsGridPanel(Component... components) {
        JPanel panel = createPanel(components.length, 6);
        for (Component component : components) {
            panel.add(component);
        }
        return panel;
    }

    private static JPanel createPanel(int rowCount, int distance) {
        JPanel panel = new JPanel(new GridLayout(rowCount, 1, 0, distance));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 5, 8, 0));
        return panel;
    }

    public static JButton createButtonAsLink(String title) {
        JButton button = new JButton("<html><u>" + title + "<html>");
        button.setForeground(new Color(0, 144, 255));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
