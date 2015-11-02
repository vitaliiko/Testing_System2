package testingClasses;

import supporting.IOFileHandling;

import java.util.ArrayList;

public class TestTaskManager {

    private ArrayList<TestTask> testTaskList;
    private int currentTestIndex;

    public ArrayList<TestTask> getTestTaskList() {
        return testTaskList;
    }

    public TestTask getCurrentTest() {
        return testTaskList.get(currentTestIndex);
    }

    public void setCurrentTestIndex(int currentTestIndex) {
        this.currentTestIndex = currentTestIndex;
    }

    public TestTaskManager() {
        testTaskList = IOFileHandling.loadTestTasks();
    }

    public TestTask getTest(int index) {
        return testTaskList.get(index);
    }

    public void addTest(TestTask testTask) {
        testTaskList.add(testTask);
    }

    public TestTask deleteTest(TestTask testTask) {
        testTaskList.remove(testTask);
        return testTask;
    }

    public void saveTests() {
        IOFileHandling.saveTestTasks(testTaskList);
    }
}