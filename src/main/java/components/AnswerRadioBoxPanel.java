package components;

import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AnswerRadioBoxPanel extends JPanel {

    private JCheckBox checkBox;
    private JRadioButton radioButton;
    private JTextArea textArea;

    public AnswerRadioBoxPanel(String text) {
        checkBox = new JCheckBox("");
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(checkBox);
        add(createTextArea(text));
    }

    public AnswerRadioBoxPanel(ButtonGroup buttonGroup, String text) {
        radioButton = new JRadioButton("");
        radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonGroup.add(radioButton);
        add(radioButton);
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
        if (checkBox != null) {
            return checkBox.isSelected();
        }
        return radioButton.isSelected();
    }
}
