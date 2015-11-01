package usersClasses;

import supporting.IOFileHandling;
import supporting.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherManager extends Validator {

    private Set<Teacher> teacherSet;
    private ArrayList<String> teachersNamesList;
    private Teacher currentTeacher;

    public TeacherManager() {
        teacherSet = IOFileHandling.loadUserSet(IOFileHandling.TEACHERS_SER);
        teachersNamesList = new ArrayList<>(
                this.teacherSet.stream().map(Teacher::getUserName).collect(Collectors.toList()));
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public ArrayList<String> getTeachersNamesList() {
        return teachersNamesList;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public void saveTeacherSet() {
        IOFileHandling.saveUserSet(teacherSet, IOFileHandling.TEACHERS_SER);
    }

    public void createTeacher(String surname, String name, String secondName, char[] password)
            throws IOException {
        validateName(surname, name, secondName);
        validatePassword(password);
        if (!teacherSet.add(new Teacher(surname, name, secondName, password))) {
            throw new IOException(Message.EXIST_USER);
        }
        IOFileHandling.saveUserSet(teacherSet, IOFileHandling.TEACHERS_SER);
    }

    public void updateCurrentTeacherInfo(String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(name, surname, secondName);

        String userName = surname + " " + name + " " + secondName;
        if (!currentTeacher.getUserName().equals(userName)) {
            checkUserName(userName);
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

    public void checkUserName(String userName) throws IOException {
        for (Teacher teacher : teacherSet) {
            if (teacher.getUserName().equals(userName)) {
                throw new IOException(Message.EXIST_USER);
            }
        }
    }

    public void deleteTeacher(Teacher teacher) {
        teacherSet.remove(teacher);
    }

    public void deleteCurrentTeacher() {
        teacherSet.remove(currentTeacher);
        currentTeacher = null;
    }

    public boolean authorizedTeacher(String userName, char[] password) {
        for (Teacher teacher : teacherSet) {
            if (teacher.isMatches(userName, password)) {
                currentTeacher = teacher;
                return true;
            }
        }
        return false;
    }

    public String[] getCurentTeacherFields() {
        return new String[]{currentTeacher.getSurname(), currentTeacher.getName(),
                currentTeacher.getSecondName(), currentTeacher.getTelephoneNum(), currentTeacher.getMailAddress()};
    }
}
