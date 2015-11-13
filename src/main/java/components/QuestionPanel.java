package components;

import javafx.scene.control.ToggleButton;
import supporting.ImageUtils;
import testingClasses.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

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

        Random random = new Random();
        List<String> answerList = new ArrayList<>();
        answerList.addAll(question.getAnswersList());
        if (question.getRightAnswersList().size() == 1) {
            ButtonGroup buttonGroup = new ButtonGroup();
            while (answerList.size() > 0) {
                String answer = answerList.get(random.nextInt(answerList.size()));
                add(new AnswerRadioBoxPanel(buttonGroup, answer));
                answerList.remove(answer);
            }
        } else {
            while (answerList.size() > 0) {
                String answer = answerList.get(random.nextInt(answerList.size()));
                add(new AnswerRadioBoxPanel(answer));
                answerList.remove(answer);
            }
        }

        add(new JSeparator());
    }
}
