import studentGI.StudentWorkspaceGI;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;

public class StartStudentWorkspaceGI {

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        studentManager.authorizeUser("Іванов Іван Іванович", "".toCharArray(), new StudentsGroup("CGC-1466", "", ""));
        new StudentWorkspaceGI(studentManager);
    }
}
