package testingClasses;

import supporting.IOFileHandling;

import java.util.ArrayList;

public class TestTaskManager {

    private ArrayList<TestTask> testTaskList;

    public TestTaskManager() {
        testTaskList = IOFileHandling.loadTestTasks();
    }

    public TestTask getTest(int index) {
        return testTaskList.get(index);
    }

    public void setTest(TestTask test, int index) {
        testTaskList.set(index, test);
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