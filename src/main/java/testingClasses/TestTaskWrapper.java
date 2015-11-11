package testingClasses;

import java.io.Serializable;

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

    public void createCard() {

    }

    @Override
    public String toString() {
        return testTask.getTaskName() + " " + status;
    }
}
