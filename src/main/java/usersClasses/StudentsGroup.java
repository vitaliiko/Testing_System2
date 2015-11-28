package usersClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StudentsGroup implements UserDAO<Student>, Serializable, Comparable<StudentsGroup>, Data {

    private String name;
    private String faculty;
    private String department;
    private Teacher curator;
    private Set<Student> studentsSet = new HashSet<>();

    public StudentsGroup(String name, String faculty, String department, Teacher curator) {
        this.name = name;
        this.faculty = faculty;
        this.department = department;
        this.curator = curator;
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

    public Teacher getCurator() {
        return curator;
    }

    public void setCurator(Teacher curator) {
        this.curator = curator;
    }

    @Override
    public Student getUser(int index) {
        return new ArrayList<>(studentsSet).get(index);
    }

    @Override
    public Set<Student> getUsersSet() {
        return studentsSet;
    }

    @Override
    public void updateUser(Student user) {

    }

    @Override
    public void deleteUser(Student user) {
        studentsSet.remove(user);
    }

    @Override
    public void addUser(Student user) {
        studentsSet.add(user);
    }

    @Override
    public int compareTo(StudentsGroup o) {
        return name.compareTo(o.getName());
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
