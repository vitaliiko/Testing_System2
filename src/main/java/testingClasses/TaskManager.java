package testingClasses;

import supporting.IOFileHandling;

import java.util.ArrayList;

public class TaskManager {

    private ArrayList<TestTask> testTaskList;
    private ArrayList<TestTask> testTasksArchive = new ArrayList<>();

    public TaskManager() {
        testTaskList = IOFileHandling.loadTestTasks();
    }

    public void addTest(TestTask testTask) {
        testTaskList.add(testTask);
    }

    public TestTask deleteTest(TestTask testTask) {
        testTaskList.remove(testTask);
        return testTask;
    }

    public void archiveTest(TestTask testTask) {
        testTasksArchive.add(deleteTest(testTask));
    }

    public void saveTests() {
        IOFileHandling.saveTestTasks(testTaskList, IOFileHandling.TEST_TASK_SER);
    }

    public void saveArchive() {
        IOFileHandling.saveTestTasks(testTasksArchive, IOFileHandling.TEST_TASK_ARCHIVE);
    }
}
