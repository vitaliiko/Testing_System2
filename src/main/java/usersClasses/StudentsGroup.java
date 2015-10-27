package usersClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentsGroup implements UserDAO, Serializable {

    private String name;
    private String faculty;
    private String department;
    private Set<Student> studentsSet = new HashSet<>();

    public StudentsGroup(String name, String faculty, String department) {
        this.name = name;
        this.faculty = faculty;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(studentsSet);
    }

    @Override
    public User getUser(int index) {
        return new ArrayList<>(studentsSet).get(index);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {
        Student student = (Student) user;
        studentsSet.remove(student);
    }

    @Override
    public int addUser(User user) {
        studentsSet.add((Student) user);
        return new ArrayList<>(studentsSet).indexOf(user);
    }

    @Override
    public int getUserIndex(User user) {
        Student student = (Student) user;
        return new ArrayList<>(studentsSet).indexOf(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentsGroup that = (StudentsGroup) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
