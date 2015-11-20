package usersClasses;

import supporting.IOFileHandling;
import components.SingleMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherManager extends UserManager<Teacher> {

    private Set<Teacher> teacherSet;
    private ArrayList<String> teachersNamesList;

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
        return currentUser;
    }

    @Override
    public void saveUserSet() {
        IOFileHandling.saveUserSet(teacherSet, IOFileHandling.TEACHERS_SER);
    }

    public void createUser(String surname, String name, String secondName, char[] password) throws IOException {
        validateName(surname, name, secondName);
        validatePassword(password, this);
        if (!teacherSet.add(new Teacher(surname, name, secondName, password))) {
            throw new IOException(SingleMessage.EXIST_USER);
        }
        saveUserSet();
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
        teacherSet.remove(currentUser);
        currentUser = null;
    }

    @Override
    public boolean authorizeUser(String userName, char[] password) {
        for (Teacher teacher : teacherSet) {
            if (teacher.isMatches(userName, password)) {
                currentUser = teacher;
                return true;
            }
        }
        return false;
    }
}
