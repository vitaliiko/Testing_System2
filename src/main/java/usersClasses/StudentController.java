package usersClasses;

import java.util.ArrayList;
import java.util.Set;

public class StudentController extends Validator {

    private Set<Student> studentSet;
    private Set<StudentsGroup> studentsGroupSet;

    public StudentController(Set<StudentsGroup> studentsGroupSet) {
        this.studentsGroupSet = studentsGroupSet;
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
