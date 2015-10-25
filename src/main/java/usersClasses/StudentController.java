package usersClasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StudentController {

    private Set<Student> studentSet;
    private Set<StudentsGroup> studentsGroupSet;

    public StudentController() {
        studentsGroupSet = new HashSet<>();
        studentsGroupSet.add(new StudentsGroup("CGC-1466", "", ""));
        studentsGroupSet.add(new StudentsGroup("CGC-1566", "", ""));
        studentsGroupSet.add(new StudentsGroup("CGC-1366", "", ""));
        studentsGroupSet.add(new StudentsGroup("CG-126", "", ""));
        studentsGroupSet.add(new StudentsGroup("RV-125", "", ""));

        ArrayList<StudentsGroup> studentsGroupsList = new ArrayList<>(studentsGroupSet);
        studentSet = new HashSet<>();
        studentSet.add(new Student("Іванов", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Іваненко", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Петренко", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Петров", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Іванов", "Петро", "Іванович", studentsGroupsList.get(1)));
        studentSet.add(new Student("Іванов", "Іван", "Петрович", studentsGroupsList.get(1)));
        studentSet.add(new Student("Іванов", "Федір", "Петрович", studentsGroupsList.get(1)));
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public Set<StudentsGroup> getStudentsGroupSet() {
        return studentsGroupSet;
    }

    public void setStudentsGroupSet(Set<StudentsGroup> studentsGroupSet) {
        this.studentsGroupSet = studentsGroupSet;
    }

    public ArrayList<String> getGroupNamesList() {
        ArrayList<String> groupNamesList = new ArrayList<>();
        for (StudentsGroup studentsGroup : studentsGroupSet) {
            groupNamesList.add(studentsGroup.getName());
        }
        return groupNamesList;
    }
}
