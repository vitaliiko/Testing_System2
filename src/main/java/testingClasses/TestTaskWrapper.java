package testingClasses;

import components.BoxPanel;
import components.QuestionPanel;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTaskWrapper implements Serializable {

    public static final int FAIL = 0;
    public static final int NOT_TOOK = 1;
    public static final int BAD = 2;
    public static final int FINE = 3;
    public static final int GOOD = 4;

    private TestTask testTask;
    private int attemptsLeft;
    private int status;
    private int points;

    private List<QuestionPanel> questionPanelList = new ArrayList<>();

    public TestTaskWrapper(TestTask testTask) {
        this.testTask = testTask;
        attemptsLeft = testTask.getAttemptsLimit();
        status = NOT_TOOK;
    }

    public TestTask getTestTask() {
        return testTask;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void decAttemptsLeft() {
        attemptsLeft--;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<QuestionPanel> getQuestionPanelList() {
        return questionPanelList;
    }

    public JPanel createCard() {
        Random random = new Random();
        JPanel panel = new BoxPanel(BoxLayout.Y_AXIS);
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(testTask.getQuestionsList());
        int i = 1;
        while (questionList.size() > 0) {
            Question question = questionList.get(random.nextInt(questionList.size()));
            QuestionPanel questionPanel = new QuestionPanel(i, question);
            i++;
            questionList.remove(question);
            questionPanelList.add(questionPanel);
            panel.add(questionPanel);
        }
        attemptsLeft--;
        return panel;
    }

    @Override
    public String toString() {
        return testTask.getTaskName() + " " + status;
    }
}
