import supporting.IOFileHandling;
import supporting.ImageUtils;
import testingClasses.Question;
import testingClasses.TestTask;
import testingClasses.TestTaskWrapper;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.Teacher;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StartInitObjects {

    public static final ArrayList<StudentsGroup> studentsGroupsList = new ArrayList<>();

    private static Set<Teacher> teacherSet;
    private static Teacher ivanov;
    private static Student ivan;

    public static void main(String[] args) throws IOException {
        initTeacherSet();
        initTestTask();
        initStudents();
    }

    public static void initStudents() throws IOException {
        studentsGroupsList.add(new StudentsGroup("СВС-1466", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("СВС-1566", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("СВС-1366", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("КЛ-126", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("РП-125", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("РП-126", "", "", ivanov));
        studentsGroupsList.add(new StudentsGroup("МВ-127", "", "", ivanov));

        String[] names = {"Іван", "Петро", "Василь", "Артем", "Максим", "Сергій", "Олександр", "Олексій", "Валєра", "Денис"};
        String[] surnames = {"Іванов", "Петров", "Васильєв", "Артемов", "Максимов", "Сергійов", "Олександров", "Олексійов", "Ковальов", "Денисов"};
        String[] secondNames = {"Іванович", "Петрович", "Васильович", "Артемович", "Максимович", "Сергійович", "Олександрович", "Олексійович", "Валєрійович", "Денисович"};

        Random random = new Random();
        for (StudentsGroup studentsGroup : studentsGroupsList) {
            for (int i = 0; i <= 15; i++) {
                Student student = new Student(surnames[random.nextInt(10)], names[random.nextInt(10)], secondNames[random.nextInt(10)], studentsGroup);
//                TestTaskWrapper testTaskWrapper = new TestTaskWrapper()
//                student.addTestTaskWrapper();
            }
        }
        ivan = new Student("Іванов", "Іван", "Іванович", studentsGroupsList.get(0));
        IOFileHandling.saveStudentsGroupSet(new TreeSet<>(studentsGroupsList));
    }

    public static void initTeacherSet() {
        teacherSet = new TreeSet<>();
        ivanov = new Teacher("Іванов", "Іван", "Іванович", "00000");
        teacherSet.add(ivanov);
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
        String[] disciplineNames = {"Інформатика", "Інформатика", "Дискратна математика"};
        List<Teacher> teachers = new ArrayList<>(teacherSet);
        try {
            int j = 0, i = 1;
            for (Teacher teacher : teachers) {
                TestTask testTask = createTests("tests/test.txt", "Модульна контрольна робота " + i++, disciplineNames[j], teacher);
                testTask.getAuthorsList().add(ivanov);
                testTasks.add(testTask);
                testTask.setStudentGroupsList(studentsGroupsList);
                j = j == 2 ? 0 : ++j;
            }
        } catch (IOException e) {
            e.getMessage();
        }
        IOFileHandling.saveTestTasks(testTasks);
    }

    public static TestTask createTests(String fileName, String testName, String discipline, Teacher creatorName)
            throws IOException {
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
                        try {
                            Path imagePath = Paths.get("tests", s);
                            image = ImageUtils.imageInByteArr(imagePath.toString());
                            imageName = s;
                        } catch (InvalidPathException e) {
                            System.out.println(s);
                        }
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
