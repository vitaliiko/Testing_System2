package usersClasses;

import testingClasses.TestTaskWrapper;

import java.util.ArrayList;

public class Student extends User {

    private StudentsGroup studentsGroup;
    private ArrayList<TestTaskWrapper> testTaskWrapperList = new ArrayList<>();

    public Student(String surname, String name, String secondName, StudentsGroup studentsGroup) {
        super(surname, name, secondName, "");
        this.studentsGroup = studentsGroup;
        studentsGroup.addUser(this);
    }

    public StudentsGroup getStudentsGroup() {
        return studentsGroup;
    }

    public String getGroupName() {
        return studentsGroup.getName();
    }

    public ArrayList<TestTaskWrapper> getTestTaskWrapperList() {
        return testTaskWrapperList;
    }

    public void addTestTaskWrapper(TestTaskWrapper testTaskWrapper) {
        testTaskWrapperList.add(testTaskWrapper);
    }
}
