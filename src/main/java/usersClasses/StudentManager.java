package usersClasses;

import components.SingleMessage;
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

    public void createUser(String surname, String name, String secondName, StudentsGroup studentsGroup)
            throws IOException {
        this.studentsGroup = studentsGroup;
        System.out.println(surname + " " + name + " " + secondName);
        validateName(surname, name, secondName);
        if (!this.studentsGroup.getUsersSet().add(new Student(surname, name, secondName, studentsGroup))) {
            throw new IOException(SingleMessage.EXIST_USER);
        }
        saveUserSet();
    }

    public void updateCurrentUserInfo(String surname, String name, String secondName, StudentsGroup group)
            throws IOException {
        if (!currentUser.getStudentsGroup().equals(group)) {
            currentUser.setStudentsGroup(group);
        }
        updateCurrentUserInfo(surname, name, secondName);
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

    public void addStudentsGroup(StudentsGroup studentsGroup) throws IOException {
        if (!studentsGroupSet.add(studentsGroup)) {
            throw new IOException("Група з такою назвою вже існує");
        }
    }

    public void deleteStudentsGroup(StudentsGroup studentsGroup) {
        studentsGroupSet.remove(studentsGroup);
    }

    public void checkStudentsGroupName(String name) throws IOException {
        for (StudentsGroup studentsGroup : studentsGroupSet) {
            if (studentsGroup.getName().equals(name)) {
                throw new IOException("Група з такою назвою вже існує");
            }
        }
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
