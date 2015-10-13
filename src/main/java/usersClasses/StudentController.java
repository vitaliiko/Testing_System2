package usersClasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StudentController {

    private Set<Student> studentSet;
    private ArrayList<StudentsGroup> studentsGroupList;

    public StudentController() {
        studentsGroupList = new ArrayList<>();
        studentsGroupList.add(new StudentsGroup("CGC-1466", "", ""));
        studentsGroupList.add(new StudentsGroup("CGC-1566", "", ""));
        studentsGroupList.add(new StudentsGroup("CGC-1366", "", ""));
        studentsGroupList.add(new StudentsGroup("CG-126", "", ""));
        studentsGroupList.add(new StudentsGroup("RV-125", "", ""));

        studentSet = new HashSet<>();
        studentSet.add(new Student("Іванов", "Іван", "Іванович", studentsGroupList.get(0)));
        studentSet.add(new Student("Іваненко", "Іван", "Іванович", studentsGroupList.get(0)));
        studentSet.add(new Student("Петренко", "Іван", "Іванович", studentsGroupList.get(0)));
        studentSet.add(new Student("Петров", "Іван", "Іванович", studentsGroupList.get(0)));
        studentSet.add(new Student("Іванов", "Петро", "Іванович", studentsGroupList.get(1)));
        studentSet.add(new Student("Іванов", "Іван", "Петрович", studentsGroupList.get(1)));
        studentSet.add(new Student("Іванов", "Федір", "Петрович", studentsGroupList.get(1)));
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public ArrayList<StudentsGroup> getStudentsGroupList() {
        return studentsGroupList;
    }

    public void setStudentsGroupList(ArrayList<StudentsGroup> studentsGroupList) {
        this.studentsGroupList = studentsGroupList;
    }
}
