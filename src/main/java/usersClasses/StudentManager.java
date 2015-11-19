package usersClasses;

import supporting.IOFileHandling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentManager extends UserManager<Student> {

    private Set<StudentsGroup> studentsGroupSet;
    private StudentsGroup studentsGroup;

    public StudentManager() {
        studentsGroupSet = IOFileHandling.loadStudentsGroupSet();
    }

    public void setCurrentUser(Student currentUser) {
        this.currentUser = currentUser;
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
        return currentUser;
    }

    @Override
    public void saveUserSet() {
        IOFileHandling.saveStudentsGroupSet(studentsGroupSet);
    }

    @Override
    public void createUser(String surname, String name, String secondName, char[] password) throws IOException {

    }

    public void updateCurrentUserInfo(String surname, String name, String secondName, StudentsGroup group,
                                      String telephone, String mail) throws IOException {
        if (!currentUser.getStudentsGroup().equals(group)) {
            currentUser.setStudentsGroup(group);
        }
        updateCurrentUserInfo(surname, name, secondName, telephone, mail);
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
    public boolean authorizeUser(String userName, char[] password) throws IOException {
        for (Student student : studentsGroup.getUsersSet()) {
            if (student.isPasswordEmpty()) {
                validatePassword(password, this);
                student.setPassword(password);
                saveUserSet();
                currentUser = student;
                return true;
            }
            if (student.isMatches(userName, password)) {
                currentUser = student;
                return true;
            }
        }
        return false;
    }

    public boolean authorizeUser(String userName, char[] password, StudentsGroup studentsGroup) throws IOException {
        this.studentsGroup = studentsGroup;
        return studentsGroup != null && authorizeUser(userName, password);
    }

    public Set<StudentsGroup> getStudentsGroupSet() {
        return studentsGroupSet;
    }

    public ArrayList<String> getGroupNamesList() {
        return studentsGroupSet.stream()
                .map(StudentsGroup::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public StudentsGroup getStudentGroup(String name) {
        for (StudentsGroup sg : studentsGroupSet) {
            if (name.equals(sg.getName())) {
                return sg;
            }
        }
        return null;
    }
}
