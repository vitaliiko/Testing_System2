import studentGI.StudentWorkspaceGI;
import usersClasses.StudentManager;

import java.io.IOException;

public class StartStudentWorkspaceGI {

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        StartInitObjects.initStudents();
        try {
            studentManager.authorizeUser("Іванов Іван Іванович", "".toCharArray(),
                    StartInitObjects.studentsGroupsList.get(0));
        } catch (IOException e) {
            e.getMessage();
        }
        new StudentWorkspaceGI(studentManager);
    }
}
