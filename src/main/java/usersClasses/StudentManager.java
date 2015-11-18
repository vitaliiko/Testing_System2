package usersClasses;

import supporting.IOFileHandling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        IOFileHandling.saveStudentsGroupSet(studentsGroupSet);
    }

    @Override
    public void createUser(String surname, String name, String secondName, char[] password) throws IOException {

    }

    @Override
    public void updateCurrentUserInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(name, surname, secondName);

        String userName = surname + " " + name + " " + secondName;
        if (!currentStudent.getUserName().equals(userName)) {
            checkUsername(userName);
        }

        currentStudent.setName(name);
        currentStudent.setSurname(surname);
        currentStudent.setSecondName(secondName);
        currentStudent.setUserName(userName);

        if (!telephone.isEmpty()) {
            validateTelephone(telephone);
        }
        currentStudent.setTelephoneNum(telephone);

        if (!mail.isEmpty()) {
            validateMail(mail);
        }
        currentStudent.setMailAddress(mail);
    }

    public void updateCurrentUserInfo(String surname, String name, String secondName, StudentsGroup group,
                                      String telephone, String mail) throws IOException {
        if (!currentStudent.getStudentsGroup().equals(group)) {
            currentStudent.setStudentsGroup(group);
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
                currentStudent = student;
                return true;
            }
            if (student.isMatches(userName, password)) {
                currentStudent = student;
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
        return studentsGroupSet.stream().map(StudentsGroup::getName).collect(Collectors.toCollection(ArrayList::new));
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
