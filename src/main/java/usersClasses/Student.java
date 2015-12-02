package usersClasses;

import components.SingleMessage;
import testingClasses.TestTaskWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private StudentsGroup studentsGroup;
    private List<TestTaskWrapper> testTaskWrapperList = new ArrayList<>();

    public Student(String surname, String name, String secondName, StudentsGroup studentsGroup) throws IOException {
        super(surname, name, secondName, "");
        this.studentsGroup = studentsGroup;
        if (!studentsGroup.addUser(this)) {
            throw new IOException(SingleMessage.EXIST_USER);
        }
    }

    public StudentsGroup getStudentsGroup() {
        return studentsGroup;
    }

    public void setStudentsGroup(StudentsGroup studentsGroup) {
        this.studentsGroup.deleteUser(this);
        this.studentsGroup = studentsGroup;
        this.studentsGroup.addUser(this);
    }

    public String getGroupName() {
        return studentsGroup.getName();
    }

    public List<TestTaskWrapper> getTestTaskWrapperList() {
        return testTaskWrapperList;
    }

    public void addTestTaskWrapper(TestTaskWrapper testTaskWrapper) {
        testTaskWrapperList.add(testTaskWrapper);
    }
}
