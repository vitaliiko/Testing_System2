package testingClasses;

import supporting.IOFileHandling;
import usersClasses.Student;
import usersClasses.StudentsGroup;

import java.util.ArrayList;

public class TestTaskManager {

    private ArrayList<TestTask> testTaskList;
    private int currentTestIndex;

    public TestTaskManager() {
        testTaskList = IOFileHandling.loadTestTasks();
    }

    public ArrayList<TestTask> getTestTaskList() {
        return testTaskList;
    }

    public TestTask getCurrentTest() {
        return currentTestIndex != -1 ? testTaskList.get(currentTestIndex) : null;
    }

    public void setCurrentTest(int currentTestIndex) {
        this.currentTestIndex = currentTestIndex;
    }

    public TestTask getTest(int index) {
        return testTaskList.get(index);
    }

    public void addTest(TestTask testTask) {
        testTaskList.add(testTask);
        currentTestIndex = testTaskList.indexOf(testTask);
    }

    public TestTask deleteTest(TestTask testTask) {
        testTaskList.remove(testTask);
        return testTask;
    }

    public void saveTests() {
        IOFileHandling.saveTestTasks(testTaskList);
    }

    public int getCurrentTestIndex() {
        return currentTestIndex;
    }

    public void setCurrentTestIndex(int currentTestIndex) {
        this.currentTestIndex = currentTestIndex;
    }

    private boolean haveWrapper(TestTask testTask, Student student) {
        for (TestTaskWrapper testTaskWrapper : student.getTestTaskWrapperList()) {
            if (testTaskWrapper.getTestTask().equals(testTask)) {
                return true;
            }
        }
        return false;
    }

    public void wrappingTests(Student student) {
        String groupName = student.getGroupName();
        testTaskList.stream()
                .filter(testTask -> testTask.getStudentGroupsList().contains(groupName) &&
                        !haveWrapper(testTask, student))
                .forEach(testTask -> student.addTestTaskWrapper(new TestTaskWrapper(testTask)));

        student.getTestTaskWrapperList().stream()
                .filter(testTaskWrapper -> testTaskWrapper.getStatus() <= TestTaskWrapper.BAD &&
                        !testTaskWrapper.getTestTask().getStudentGroupsList().contains(student.getGroupName()))
                .forEach(testTaskWrapper -> testTaskWrapper.setStatus(TestTaskWrapper.FAIL));
    }
}