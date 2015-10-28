import supporting.IOFileHandling;
import testingClasses.Question;
import testingClasses.TestTask;
import userGI.AuthenticationGI;
import userGI.ShowTaskGI;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.Teacher;
import usersClasses.TeacherController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Start {

    public static void main(String[] args) {

        createShowTaskWindow();

    }

    public static void createShowTaskWindow() {
        TeacherController controller = new TeacherController(IOFileHandling.loadTeachersSet());
        try {
            new ShowTaskGI(IOFileHandling.loadTestTask("111"), new Teacher("Іванов", "Іван", "Іванович", "111111"), controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crateAuthenticationWindow() {
        TeacherController controller = new TeacherController(IOFileHandling.loadTeachersSet());
        try {
            new AuthenticationGI(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initTestTask() {
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


        TestTask testTask = new TestTask("111", "222", "333");
        testTask.setQuestionsList(questions);
        IOFileHandling.saveTestTask(testTask, "111");
    }

    public static void initTeacherSet() {
        Set<Teacher> teacherSet = new TreeSet<>();
        teacherSet.add(new Teacher("Іванов", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Петров", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Сидоров", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Іваненко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Петренко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Федоренко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Сидоренко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Клименко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Лук\'яненко", "Іван", "Іванович", "111111"));
        teacherSet.add(new Teacher("Мироненко", "Іван", "Іванович", "111111"));
        IOFileHandling.saveTeachersSet(teacherSet);
    }

    public static void initStudents() {
        Set<Student> studentSet;
        Set<StudentsGroup> studentsGroupSet;

        studentsGroupSet = new HashSet<>();
        studentsGroupSet.add(new StudentsGroup("CGC-1466", "", ""));
        studentsGroupSet.add(new StudentsGroup("CGC-1566", "", ""));
        studentsGroupSet.add(new StudentsGroup("CGC-1366", "", ""));
        studentsGroupSet.add(new StudentsGroup("CG-126", "", ""));
        studentsGroupSet.add(new StudentsGroup("RV-125", "", ""));

        ArrayList<StudentsGroup> studentsGroupsList = new ArrayList<>(studentsGroupSet);
        studentSet = new HashSet<>();
        studentSet.add(new Student("Іванов", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Іваненко", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Петренко", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Петров", "Іван", "Іванович", studentsGroupsList.get(0)));
        studentSet.add(new Student("Іванов", "Петро", "Іванович", studentsGroupsList.get(1)));
        studentSet.add(new Student("Іванов", "Іван", "Петрович", studentsGroupsList.get(1)));
        studentSet.add(new Student("Іванов", "Федір", "Петрович", studentsGroupsList.get(1)));
    }
}
