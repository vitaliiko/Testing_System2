import org.omg.PortableInterceptor.ServerRequestInfo;
import supporting.IOFileHandling;
import supporting.ImageUtils;
import testingClasses.Question;
import testingClasses.TestTask;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StartInitObjects {

    public static final ArrayList<StudentsGroup> studentsGroupsList = new ArrayList<>();

    public static void main(String[] args) {
        initStudents();
        initTeacherSet();
        initTestTask();
    }

    public static void initStudents() {
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

        IOFileHandling.saveStudentsGroupSet(new TreeSet<>(studentsGroupsList));
    }

    public static TestTask createTestObject(String... names) {
        ArrayList<String> answersList = new ArrayList<>();
        answersList.add("11111111111111111111111111111111111111111111");
        answersList.add("2222222222222222222222222222222222222222222");
        answersList.add("333333333333333333333333333333 333333 333333333 333333333333333 33333333333333333 3333333333 3333333333333 3333333333 33333333333333333 3333333333 333333333 3333333333333 3333333333 333333333333 33333333333333 333333333 333333");

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

    public static void initTestTask() {
        ArrayList<TestTask> testTasks = new ArrayList<>();
        String[] testNames = {"1 курс. Модульна контрольна робота", "1 курс. Залік", "1 курс. Модульна контрольна робота"};
        String[] disciplineNames = {"Інформатика", "Інформатика", "Дискратна математика"};
        String[] creatorNames = {"Іванов Іван Іванович", "Іванов Іван Іванович", "Іваненко Іван Іванович"};
        try {
            for (int i = 0; i < 3; i++) {
                testTasks.add(createTests("tests/test.txt", testNames[i], disciplineNames[i], creatorNames[i]));
            }
        } catch (IOException e) {
            e.getMessage();
        }
        IOFileHandling.saveTestTasks(testTasks);
    }

    public static TestTask createTests(String fileName, String testName, String discipline, String creatorName) throws IOException {
        List<String> stringList = IOFileHandling.readFromFile(fileName);
        if (stringList == null) {
            throw new IOException("fuck");
        }

        int i = 0;
        String task = null;
        byte[] image = null;
        String imageName = null;
        List<String> answers = new ArrayList<>();
        List<String> rightAnswers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (String s : stringList) {
            switch (i) {
                case 0: {
                    task = s;
                    i++;
                    break;
                }
                case 1: {
                    if (!s.isEmpty()) {
                        image = ImageUtils.imageInByteArr("E:\\Intellij\\Testing_System2\\tests\\" + s);
                        imageName = "E:\\Intellij\\Testing_System2\\tests\\" + s;
                    }
                    i++;
                    break;
                }
                default: {
                    if (s.equals("***")) {
                        if (image != null) {
                            questions.add(new Question(imageName, image, task, answers, rightAnswers));
                        } else {
                            questions.add(new Question(task, answers, rightAnswers));
                        }
                        image = null;
                        answers = new ArrayList<>();
                        rightAnswers = new ArrayList<>();
                        i = 0;
                    } else {
                        if (s.startsWith("*")) {
                            s = s.replace("*", "");
                            answers.add(s);
                            rightAnswers.add(s);
                        } else {
                            answers.add(s);
                        }
                    }
                }
            }
        }

        TestTask testTask = new TestTask(testName, discipline, creatorName);
        testTask.setQuestionsList(questions);
        return testTask;
    }
}
