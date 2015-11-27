package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnswerForQuestionPanel extends JPanel {

    private JToggleButton toggleButton;
    private JTextArea textArea;

    public AnswerForQuestionPanel(String text) {
        toggleButton = new JCheckBox("");
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setOpaque(false);
        setOpaque(false);
        add(toggleButton);
        add(createTextArea(text));
    }

    public AnswerForQuestionPanel(ButtonGroup buttonGroup, String text) {
        toggleButton = new JRadioButton("");
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setOpaque(false);
        buttonGroup.add(toggleButton);
        setOpaque(false);
        add(toggleButton);
        add(createTextArea(text));
    }

    private JTextArea createTextArea(String text) {
        int height = (text.length() / 90 + (text.length() % 90 > 0 ? 1 : 0)) * 17;
        textArea = FrameUtils.createTextArea(text);
        textArea.setMinimumSize(new Dimension(200, 20));
        textArea.setPreferredSize(new Dimension(600, height));
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleButton.doClick();
            }
        });
        return textArea;
    }

    public String getText() {
        return textArea.getText();
    }

    public boolean isSelected() {
        return toggleButton.isSelected();
    }

    public void doDisabled() {
        toggleButton.setEnabled(false);
    }

    public void setTrue() {
        textArea.setFont(new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize()));
    }

    public void addListener(ActionListener actionListener) {
        toggleButton.addActionListener(actionListener);
    }
}
