package usersClasses;

import supporting.IOFileHandling;
import supporting.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherController {

    private static final String NAME_REG = "^[A-Z][a-zA-Z-]{2,}$";
    private static final String PASSWORD_REG = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*-_]).{8,24})";
    private static final String TEL_NUM_REG = "^[\\d]{10}$";
    private static final String MAIL_REG = "^[a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$";

    private Set<Teacher> teacherSet;
    private ArrayList<String> teachersNamesList;

    public TeacherController(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
        teachersNamesList = new ArrayList<>();
        for (Teacher teacher : this.teacherSet) {
            String teacherName = teacher.getSurname() + " " + teacher.getName() + " " + teacher.getSecondName();
            teachersNamesList.add(teacherName);
        }
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
        IOFileHandling.saveTeachersSet(teacherSet);
    }

    public void updateTeacherInfo(Teacher teacher, String surname, String name, String secondName, String telephone, String mail)
            throws IOException {
        validateName(name, surname, secondName);

        String userName = surname + " " + name + " " + secondName;
        checkUserName(userName);

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

    public boolean validateName(String surname, String name, String secondName) throws IOException {
        if (!validate(name, NAME_REG) || !validate(surname, NAME_REG) || !validate(secondName, NAME_REG)) {
            throw new IOException(Message.WRONG_NAME);
        }
        return true;
    }

    public boolean validatePassword(char[] password) throws IOException {
        if (password.length < 8) {
            throw new IOException(Message.SHORT_PASSWORD);
        }
        if (password.length > 24) {
            throw new IOException(Message.LONG_PASSWORD);
        }
        if (!validate(String.valueOf(password), PASSWORD_REG)) {
            throw new IOException(Message.WRONG_PASSWORD);
        }
        return true;
    }

    public boolean validateTelephone(String telephone) throws IOException {
        if (!validate(telephone, TEL_NUM_REG)) {
            throw new IOException(Message.WRONG_TEL);
        }
        return true;
    }

    public boolean validateMail(String mail) throws IOException {
        if (!validate(mail, MAIL_REG)) {
            throw new IOException(Message.WRONG_MAIL);
        }
        return true;
    }

    private boolean validate(String text, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
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
