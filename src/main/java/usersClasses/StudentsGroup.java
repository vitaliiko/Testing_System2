package usersClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentsGroup implements StudentDAO, Serializable {

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
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsSet);
    }

    @Override
    public Student getStudent(int index) {
        return new ArrayList<>(studentsSet).get(index);
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudent(Student student) {
        studentsSet.remove(student);
    }

    @Override
    public int addStudent(Student student) {
        studentsSet.add(student);
        return new ArrayList<>(studentsSet).indexOf(student);
    }

    @Override
    public int getStudentIndex(Student student) {
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
