package usersClasses;

import supporting.IOFileHandling;

import java.util.ArrayList;
import java.util.Set;

public class StudentManager extends Validator {

    private Set<StudentsGroup> studentsGroupSet;
    private Student currentStudent;

    public StudentManager() {
        studentsGroupSet = IOFileHandling.loadStudentsGroupSet();
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

    public StudentsGroup getStudentGroup(String name) {
        for (StudentsGroup sg : studentsGroupSet) {
            if (name.equals(sg.getName())) {
                return sg;
            }
        }
        return null;
    }

    public boolean authorizedStudent(String userName, char[] password, StudentsGroup studentsGroup) {
        for (Student student : studentsGroup.getAllUsers()) {
            if (student.isMatches(userName, password)) {
                currentStudent = student;
                return true;
            }
        }
        return false;
    }
}
