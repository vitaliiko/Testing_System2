import supporting.IOFileHandling;
import userGI.AuthenticationGI;
import usersClasses.Teacher;
import usersClasses.TeacherController;

import java.util.HashSet;
import java.util.Set;

public class Start {

    public static void main(String[] args) {
//        new ShowTaskGI();

//        new ShowTaskGI(IOFileHandling.loadTestTask("111"));

//        new AddQuestionGI(5);

        TeacherController controller = new TeacherController(IOFileHandling.loadTeachersSet());
        try {
            new AuthenticationGI(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Set<Teacher> teacherSet = new HashSet<>();
//        teacherSet.add(new Teacher("Іванов", "Іван", "Іванович", "111111"));
//        teacherSet.add(new Teacher("Петров", "Іван", "Іванович", "111111"));
//        teacherSet.add(new Teacher("Сидоров", "Іван", "Іванович", "111111"));
//        teacherSet.add(new Teacher("Іваненко", "Іван", "Іванович", "111111"));
//        teacherSet.add(new Teacher("Петренко", "Іван", "Іванович", "111111"));
//        IOFileHandling.saveTeachersSet(teacherSet);


//        ArrayList<String> answersList = new ArrayList<>();
//        answersList.add("1");
//        answersList.add("2");
//        answersList.add("3");
//        ArrayList<String> rightAnswersList = new ArrayList<>();
//        rightAnswersList.add("2");
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new Question("dfdfdf", answersList, rightAnswersList));
//        questions.add(new Question("dfdf212121df", answersList, rightAnswersList));
//        questions.add(new Question("dfdfdf dfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
//        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
//        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
//        questions.add(new Question("dfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdfdfdfdf dfdfdfdfdfdfdfdfdf dfdfdfdfdfdf dfdfdfdfdfdf dfdfdf dfdfdf dfdfdf", answersList, rightAnswersList));
//        TestTask testTask = new TestTask("111", "222", "333");
//        testTask.setQuestionsList(questions);
//        new ShowTaskGI(testTask);
    }
}
