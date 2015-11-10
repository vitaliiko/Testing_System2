import studentGI.StudentAuthGI;
import studentGI.StudentWorkspaceGI;
import supporting.IOFileHandling;
import testingClasses.Question;
import testingClasses.TestTask;
import teacherGI.TeacherAuthGI;
import teacherGI.TeacherWorkspaceGI;
import usersClasses.*;

import java.util.*;

public class Start {

    private static ArrayList<StudentsGroup> studentsGroupsList;

    public static void main(String[] args) {

//        initTeacherSet();
//        initTestTask();
//        createShowTaskWindow();
        crateAuthenticationWindow();
//        createTeacherWorkspace();
//        createStudentAuthGI();
//        initStudents();
//        createStudentWorkspace();
    }

    public static void initStudents() {
        studentsGroupsList = new ArrayList<>();

        studentsGroupsList.add(new StudentsGroup("CGC-1466", "", ""));
        studentsGroupsList.add(new StudentsGroup("CGC-1566", "", ""));
        studentsGroupsList.add(new StudentsGroup("CGC-1366", "", ""));
        studentsGroupsList.add(new StudentsGroup("CG-126", "", ""));
        studentsGroupsList.add(new StudentsGroup("RV-125", "", ""));

        new Student("Іванов", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Іваненко", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Петренко", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Петров", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Іванов", "Петро", "Іванович", studentsGroupsList.get(1));
        new Student("Іванов", "Іван", "Петрович", studentsGroupsList.get(1));
        new Student("Іванов", "Федір", "Петрович", studentsGroupsList.get(1));

        IOFileHandling.saveStuentsGroupSet(new TreeSet<>(studentsGroupsList));
    }


    public static void createShowTaskWindow() {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.authorizeUser("Іванов Іван Іванович", "00000".toCharArray());
        try {
            //new ShowTaskGI(teacherManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createStudentWorkspace() {
        initStudents();
        StudentManager studentManager = new StudentManager();
        studentManager.authorizeUser("Іванов Іван Іванович", "".toCharArray(), studentsGroupsList.get(0));
        new StudentWorkspaceGI(studentManager);
    }

    public static void createTeacherWorkspace() {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.authorizeUser("Іванов Іван Іванович", "00000".toCharArray());
        new TeacherWorkspaceGI(teacherManager);
    }

    public static void crateAuthenticationWindow() {
        new TeacherAuthGI();
    }

    public static void initTestTask() {
        ArrayList<TestTask> testTasks = new ArrayList<>();
        testTasks.add(createTestObject("111", "222"));
        testTasks.add(createTestObject("222", "222"));
        testTasks.add(createTestObject("000", "222"));
        IOFileHandling.saveTestTasks(testTasks);
    }

    public static TestTask createTestObject(String... names) {
        ArrayList<String> answersList = new ArrayList<>();
        answersList.add("11111111111111111111111111111111111111111111");
        answersList.add("2222222222222222222222222222222222222222222");
        answersList.add("333333333333333333333333333333333333333333");

        ArrayList<String> rightAnswersList = new ArrayList<>();
        rightAnswersList.add("2222222222222222222222222222222222222222222");

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdf212121df", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));

        List<String> authorsList = new ArrayList<>();
        authorsList.add("Іванов Іван Іванович");
        authorsList.add("Петров Іван Іванович");

        TestTask testTask = new TestTask(names[0], names[1], authorsList.get(0));
        testTask.setQuestionsList(questions);
        testTask.setAuthorsList(authorsList);
        testTask.setAttribute(TestTask.PRIVATE_ATR);

        return testTask;
    }

    public static void initTeacherSet() {
        Set<Teacher> teacherSet = new TreeSet<>();
        teacherSet.add(new Teacher("Іванов", "Іван", "Іванович", "00000"));
        teacherSet.add(new Teacher("Петров", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Сидоров", "Іван", "Іванович", "22222"));
        teacherSet.add(new Teacher("Іваненко", "Іван", "Іванович", "1110111"));
        teacherSet.add(new Teacher("Петренко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Федоренко", "Іван", "Іванович", "1111111"));
        teacherSet.add(new Teacher("Сидоренко", "Іван", "Іванович", "11115811"));
        teacherSet.add(new Teacher("Клименко", "Іван", "Іванович", "22"));
        teacherSet.add(new Teacher("Лук\'яненко", "Іван", "Іванович", "5"));
        teacherSet.add(new Teacher("Мироненко", "Іван", "Іванович", new char[]{'5', '4', '3', '2', '1'}));
        IOFileHandling.saveUserSet(teacherSet, IOFileHandling.TEACHERS_SER);
    }

    public static void createStudentAuthGI() {
        new StudentAuthGI();
    }
}
