import studentGI.StudentWorkspaceGI;
import usersClasses.StudentManager;

public class StartStudentWorkspaceGI {

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        StartInitObjects.initStudents();
        studentManager.authorizeUser("Іванов Іван Іванович", "".toCharArray(),
                StartInitObjects.studentsGroupsList.get(0));
        new StudentWorkspaceGI(studentManager);
    }
}
