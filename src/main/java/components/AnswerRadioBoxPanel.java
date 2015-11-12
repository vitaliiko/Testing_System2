package components;

import javafx.scene.control.RadioButton;

import javax.swing.*;
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

        add(FrameUtils.createTextArea(text));
    }

    public AnswerRadioBoxPanel(ButtonGroup buttonGroup, String text) {
        radioButton = new JRadioButton("");
        radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonGroup.add(radioButton);
        add(radioButton);

        add(FrameUtils.createTextArea(text));
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
