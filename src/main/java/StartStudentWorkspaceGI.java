import studentGI.StudentWorkspaceGI;
import usersClasses.StudentManager;

import java.io.IOException;

public class StartStudentWorkspaceGI {

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        try {
            studentManager.authorizeUser("Іванов Іван Іванович", "111111qQ".toCharArray(),
                    studentManager.getStudentGroup("CGC-1466"));
            new StudentWorkspaceGI(studentManager);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
