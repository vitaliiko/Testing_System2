import supporting.IOFileHandling;
import testingClasses.Question;
import testingClasses.TestTask;
import userGI.AuthenticationGI;
import userGI.ShowTaskGI;
import userGI.TeacherWorkspaceGI;
import usersClasses.*;

import java.util.*;

public class Start {

    public static void main(String[] args) {

//        initTeacherSet();
//        initTestTask();
//        createShowTaskWindow();
        crateAuthenticationWindow();
//        createTeacherWorkspace();
    }

    public static void createShowTaskWindow() {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.authorizedTeacher("Іванов Іван Іванович", "00000".toCharArray());
        try {
            //new ShowTaskGI(teacherManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTeacherWorkspace() {
        TeacherManager teacherManager = new TeacherManager();
        teacherManager.authorizedTeacher("Іванов Іван Іванович", "00000".toCharArray());
        new TeacherWorkspaceGI(teacherManager);
    }

    public static void crateAuthenticationWindow() {
        new AuthenticationGI();
    }

    public static void initTestTask() {


        ArrayList<TestTask> testTasks = new ArrayList<>();
        testTasks.add(createTestObject("111", "222", "333"));
        testTasks.add(createTestObject("222", "222", "333"));
        testTasks.add(createTestObject("000", "222", "333"));
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
        authorsList.add("Іванов І. І.");
        authorsList.add("Петров І. І.");

        TestTask testTask = new TestTask(names[0], names[1], names[2]);
        testTask.setQuestionsList(questions);
        testTask.setAuthorsList(authorsList);

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
}
