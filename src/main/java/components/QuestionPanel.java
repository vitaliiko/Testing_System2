package components;

import javafx.scene.control.ToggleButton;
import supporting.ImageUtils;
import testingClasses.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class QuestionPanel extends JPanel {

    public static final Color RED_COLOR = new Color(237, 0, 4);
    public static final Color GREEN_COLOR = new Color(0, 255, 4);

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
        setBackground(Color.WHITE);

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

    public boolean isChoiceMade() {
        for (Component component : getComponents()) {
            if (component instanceof AnswerRadioBoxPanel && ((AnswerRadioBoxPanel) component).isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void addListeners(ActionListener actionListener) {
        for (Component component : getComponents()) {
            if (component instanceof AnswerRadioBoxPanel) {
                ((AnswerRadioBoxPanel) component).addListener(actionListener);
            }
        }
    }

    public void saveState() {
        List<String> studentAnswerList = new ArrayList<>();
        for (Component component : getComponents()) {
            if (component instanceof AnswerRadioBoxPanel) {
                AnswerRadioBoxPanel answerRadioBoxPanel = (AnswerRadioBoxPanel) component;
                answerRadioBoxPanel.doDisabled();
                if (question.getRightAnswersList().contains(answerRadioBoxPanel.getText())) {
                    answerRadioBoxPanel.setTrue();
                }
                if (answerRadioBoxPanel.isSelected()) {
                    studentAnswerList.add(answerRadioBoxPanel.getText());
                }
            }
        }
        if (studentAnswerList.containsAll(question.getRightAnswersList())) {
            setBackground(GREEN_COLOR);
        } else {
            setBackground(RED_COLOR);
        }
    }
}
