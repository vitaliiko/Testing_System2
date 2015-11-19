package testingClasses;

import components.BoxPanel;
import components.QuestionPanel;

import javax.swing.*;
import java.awt.*;
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
    private JPanel resultPanel;
    private float points;
    private int rightAnswersCount;
    private int attemptsLeft;
    private int status = NOT_TOOK;

    public TestTaskWrapper(TestTask testTask) {
        this.testTask = testTask;
        attemptsLeft = testTask.getAttemptsLimit();
    }

    public TestTask getTestTask() {
        return testTask;
    }

    public JPanel getResultPanel() {
        return resultPanel;
    }

    public void setResultPanel(JPanel resultPanel) {
        this.resultPanel = resultPanel;
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

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
        if (points > 90) {
            status = GOOD;
        } else if (points > 75) {
            status = FINE;
        } else if (points > testTask.getMinPoint()) {
            status = BAD;
        }
        if (points < testTask.getMinPoint()) {
            status = FAIL;
        }
    }

    public int getRightAnswersCount() {
        return rightAnswersCount;
    }

    public void setRightAnswersCount(int rightAnswersCount) {
        this.rightAnswersCount = rightAnswersCount;
    }

    public JPanel createCard() {
        JPanel panel = new BoxPanel(BoxLayout.Y_AXIS);
        panel.setBackground(Color.WHITE);

        List<Question> questionList = new ArrayList<>();
        questionList.addAll(testTask.getQuestionsList());

        Random random = new Random();
        int questionCount = testTask.getQuestionsLimit();
        int i = 1;
        while (questionCount > 0) {
            Question question = questionList.get(random.nextInt(questionCount--));
            QuestionPanel questionPanel = new QuestionPanel(i++, question);
            questionList.remove(question);
            testTask.getQuestionGroupsList().stream()
                    .filter(list -> list.contains(question))
                    .forEach(questionList::removeAll);
            if (questionCount > questionList.size()) {
                questionCount = questionList.size();
            }
            panel.add(questionPanel);
            panel.add(new JSeparator());
        }
        return panel;
    }

    public String getStatusName() {
        switch (status) {
            case NOT_TOOK: return "Необхідно здати";
            case FAIL: return "Незадовільно";
            case BAD: return "Задовільно";
            case FINE: return "Добре";
            case GOOD: return "Відмінно";
            default: return "";
        }
    }

    @Override
    public String toString() {
        return testTask.getTaskName() + " " + status;
    }
}
