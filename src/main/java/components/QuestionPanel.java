package components;

import supporting.ImageUtils;
import testingClasses.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class QuestionPanel extends JPanel {

    public static final Color RED_COLOR = new Color(237, 194, 189);
    public static final Color GREEN_COLOR = new Color(216, 255, 208);

    private int index;
    private Question question;
    private boolean isCheckBoxAlways;

    public QuestionPanel(int index, Question question, boolean isCheckBoxAlways) {
        this.index = index;
        this.question = question;
        this.isCheckBoxAlways = isCheckBoxAlways;
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
        if (!isCheckBoxAlways && question.getRightAnswersList().size() == 1) {
            ButtonGroup buttonGroup = new ButtonGroup();
            while (answerList.size() > 0) {
                String answer = answerList.get(random.nextInt(answerList.size()));
                add(new AnswerForQuestionPanel(buttonGroup, answer));
                answerList.remove(answer);
            }
        } else {
            while (answerList.size() > 0) {
                String answer = answerList.get(random.nextInt(answerList.size()));
                add(new AnswerForQuestionPanel(answer));
                answerList.remove(answer);
            }
        }
    }

    public boolean isChoiceMade() {
        for (Component component : getComponents()) {
            if (component instanceof AnswerForQuestionPanel && ((AnswerForQuestionPanel) component).isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void addListeners(ActionListener actionListener) {
        for (Component component : getComponents()) {
            if (component instanceof AnswerForQuestionPanel) {
                ((AnswerForQuestionPanel) component).addListener(actionListener);
            }
        }
    }

    public float saveState() {
        int studentAnswersCount = 0;
        int studentRightAnswersCount = 0;

        for (Component component : getComponents()) {
            if (component instanceof AnswerForQuestionPanel) {
                AnswerForQuestionPanel answerForQuestion = (AnswerForQuestionPanel) component;
                answerForQuestion.doDisabled();
                if (answerForQuestion.isSelected()) {
                    studentAnswersCount++;
                }
                if (question.getRightAnswersList().contains(answerForQuestion.getText())) {
                    answerForQuestion.setTrue();
                }
                if (question.getRightAnswersList().contains(answerForQuestion.getText())
                        && answerForQuestion.isSelected()) {
                    studentRightAnswersCount++;
                }
            }
        }

        if (question.getRightAnswersList().size() == 0) {
            return 0;
        }
        float result = (studentRightAnswersCount * 2 - studentAnswersCount) / question.getRightAnswersList().size();
        setBackground(result == 1 ? GREEN_COLOR : RED_COLOR);
        return result < 0 ? 0 : result;
    }
}
