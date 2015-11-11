package components;

import javafx.scene.control.ToggleButton;
import supporting.ImageUtils;
import testingClasses.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuestionPanel extends JPanel {

    private int index;
    private Question question;

    public QuestionPanel(int index, Question question) {
        this.index = index;
        this.question = question;
        create();
    }

    private void create() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        add(QuestionPanel.createTextArea(index + ". " + question.getTask()));
        if (question.getImageInByte() != null) {
            add(new ImagePanel(ImageUtils.imageFromByteArr(question.getImageInByte())));
        }


        for (String s : question.getAnswersList()) {

        }
    }

    public static JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
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
