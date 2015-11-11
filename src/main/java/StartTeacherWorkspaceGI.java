import teacherGI.TeacherWorkspaceGI;
import usersClasses.*;

public class StartTeacherWorkspaceGI {

    public static void main(String[] args) {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.authorizeUser("Іванов Іван Іванович", "00000".toCharArray());
        new TeacherWorkspaceGI(teacherManager);
    }
}
