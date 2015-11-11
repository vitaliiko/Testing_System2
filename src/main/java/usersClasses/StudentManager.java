package usersClasses;

import supporting.IOFileHandling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentManager extends Validator implements UserManager<Student> {

    private Set<StudentsGroup> studentsGroupSet;
    private StudentsGroup studentsGroup;
    private Student currentStudent;

    public StudentManager() {
        studentsGroupSet = IOFileHandling.loadStudentsGroupSet();
    }

    @Override
    public Set<Student> getUserSet() {
        return null;
    }

    @Override
    public List<String> getUsersNameList() {
        return null;
    }

    @Override
    public Student getCurrentUser() {
        return currentStudent;
    }

    @Override
    public void saveUserSet() {

    }

    @Override
    public void createUser(String surname, String name, String secondName, char[] password) throws IOException {

    }

    @Override
    public void updateCurrentUserInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException {

    }

    @Override
    public boolean checkUsername(String username) throws IOException {
        return false;
    }

    @Override
    public void deleteUser(Student user) {

    }

    @Override
    public void deleteCurrentUser() {

    }

    @Override
    public boolean authorizeUser(String userName, char[] password) {
        for (Student student : studentsGroup.getAllUsers()) {
            if (student.isMatches(userName, password)) {
                currentStudent = student;
                return true;
            }
        }
        return false;
    }

    public Set<StudentsGroup> getStudentsGroupSet() {
        return studentsGroupSet;
    }

    public void setStudentsGroupSet(Set<StudentsGroup> studentsGroupSet) {
        this.studentsGroupSet = studentsGroupSet;
    }

    public ArrayList<String> getGroupNamesList() {
        ArrayList<String> groupNamesList = new ArrayList<>();
        for (StudentsGroup studentsGroup : studentsGroupSet) {
            groupNamesList.add(studentsGroup.getName());
        }
        return groupNamesList;
    }

    public StudentsGroup getStudentGroup(String name) {
        for (StudentsGroup sg : studentsGroupSet) {
            if (name.equals(sg.getName())) {
                return sg;
            }
        }
        return null;
    }

    public boolean authorizeUser(String userName, char[] password, StudentsGroup studentsGroup) {
        this.studentsGroup = studentsGroup;
        return studentsGroup != null && authorizeUser(userName, password);
    }
}
