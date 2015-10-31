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

    public TeacherManager(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
        teachersNamesList = new ArrayList<>();
        teachersNamesList.addAll(this.teacherSet.stream().map(Teacher::getUserName).collect(Collectors.toList()));
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public ArrayList<String> getTeachersNamesList() {
        return teachersNamesList;
    }

    public void createTeacher(String surname, String name, String secondName, char[] password)
            throws IOException {
        validateName(surname, name, secondName);
        validatePassword(password);
        if (!teacherSet.add(new Teacher(surname, name, secondName, password))) {
            throw new IOException(Message.EXIST_USER);
        }
        IOFileHandling.saveCollection(teacherSet);
    }

    public void updateTeacherInfo(Teacher teacher, String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(name, surname, secondName);

        String userName = surname + " " + name + " " + secondName;
        if (!teacher.getUserName().equals(userName)) {
            checkUserName(userName);
        }

        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setSecondName(secondName);
        teacher.setUserName(userName);

        if (!telephone.isEmpty()) {
            validateTelephone(telephone);
            teacher.setTelephoneNum(telephone);
        }
        if (!mail.isEmpty()) {
            validateMail(mail);
            teacher.setMailAddress(mail);
        }
    }

    public void checkUserName(String userName) throws IOException {
        for (Teacher teacher : teacherSet) {
            if (teacher.getUserName().equals(userName)) {
                throw new IOException(Message.EXIST_USER);
            }
        }
    }

    public void removeTeacher(Teacher teacher) {
        teacherSet.remove(teacher);
    }

    public Teacher authorizedTeacher(String userName, char[] password) {
        for (Teacher teacher : teacherSet) {
            if (teacher.isMatches(userName, password)) {
                return teacher;
            }
        }
        return null;
    }
}
