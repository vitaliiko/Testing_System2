package usersClasses;

import supporting.IOFileHandling;
import supporting.SingleMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherManager extends Validator implements Manager<Teacher> {

    private Set<Teacher> teacherSet;
    private ArrayList<String> teachersNamesList;
    private Teacher currentTeacher;

    public TeacherManager() {
        teacherSet = IOFileHandling.loadUserSet(IOFileHandling.TEACHERS_SER);
        teachersNamesList = new ArrayList<>(
                this.teacherSet.stream().map(Teacher::getUserName).collect(Collectors.toList()));
    }

    @Override
    public Set<Teacher> getUserSet() {
        return teacherSet;
    }

    @Override
    public List<String> getUsersNameList() {
        return teachersNamesList;
    }

    @Override
    public Teacher getCurrentUser() {
        return currentTeacher;
    }

    @Override
    public void saveUserSet() {
        IOFileHandling.saveUserSet(teacherSet, IOFileHandling.TEACHERS_SER);
    }

    @Override
    public void createUser(String surname, String name, String secondName, char[] password) throws IOException {
        validateName(surname, name, secondName);
        validatePassword(password);
        if (!teacherSet.add(new Teacher(surname, name, secondName, password))) {
            throw new IOException(SingleMessage.EXIST_USER);
        }
        saveUserSet();
    }

    @Override
    public void updateCurrentUserInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(name, surname, secondName);

        String userName = surname + " " + name + " " + secondName;
        if (!currentTeacher.getUserName().equals(userName)) {
            checkUsername(userName);
        }

        currentTeacher.setName(name);
        currentTeacher.setSurname(surname);
        currentTeacher.setSecondName(secondName);
        currentTeacher.setUserName(userName);

        if (!telephone.isEmpty()) {
            validateTelephone(telephone);
            currentTeacher.setTelephoneNum(telephone);
        }
        if (!mail.isEmpty()) {
            validateMail(mail);
            currentTeacher.setMailAddress(mail);
        }
    }

    @Override
    public boolean checkUsername(String username) throws IOException {
        for (Teacher teacher : teacherSet) {
            if (teacher.getUserName().equals(username)) {
                throw new IOException(SingleMessage.EXIST_USER);
            }
        }
        return true;
    }

    @Override
    public void deleteUser(Teacher user) {
        teacherSet.remove(user);
    }

    @Override
    public void deleteCurrentUser() {
        teacherSet.remove(currentTeacher);
        currentTeacher = null;
    }

    @Override
    public boolean authorizeUser(String userName, char[] password) {
        for (Teacher teacher : teacherSet) {
            if (teacher.isMatches(userName, password)) {
                currentTeacher = teacher;
                return true;
            }
        }
        return false;
    }
}
