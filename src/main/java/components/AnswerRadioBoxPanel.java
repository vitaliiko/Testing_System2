package components;

import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AnswerRadioBoxPanel extends JPanel {

    private JToggleButton toggleButton;
    private JTextArea textArea;

    public AnswerRadioBoxPanel(String text) {
        toggleButton = new JCheckBox("");
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setBackground(Color.WHITE);
        setOpaque(false);
        add(toggleButton);
        add(createTextArea(text));
    }

    public AnswerRadioBoxPanel(ButtonGroup buttonGroup, String text) {
        toggleButton = new JRadioButton("");
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setBackground(Color.WHITE);
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
        add(textArea);
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
        textArea.setForeground(Color.GREEN);
    }

    public void addListener(ActionListener actionListener) {
        toggleButton.addActionListener(actionListener);
    }
}
