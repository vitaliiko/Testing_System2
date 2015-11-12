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

        add(FrameUtils.createTextArea(index + ". " + question.getTask()));
        if (question.getImageInByte() != null) {
            add(new ImagePanel(ImageUtils.imageFromByteArr(question.getImageInByte())));
        }


        for (String s : question.getAnswersList()) {

        }
    }
}
