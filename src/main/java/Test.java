import supporting.IOFileHandling;
import testingClasses.Question;
import testingClasses.TestTask;
import userGI.AddQuestionGI;
import userGI.ShowTaskGI;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
//        new ShowTaskGI();

//        new ShowTaskGI(IOFileHandling.loadTestTask("Модуль_1"));

//        new AddQuestionGI(5);
        TestTask testTask = new TestTask("111", "222", "333");
        ArrayList<String> answersList = new ArrayList<>();
        answersList.add("1");
        answersList.add("2");
        answersList.add("3");
        ArrayList<String> rightAnswersList = new ArrayList<>();
        rightAnswersList.add("2");

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("dfdfdf", answersList, rightAnswersList));
        questions.add(new Question("dfdffddfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf dfdffdfdf fdf ", answersList, rightAnswersList));
        questions.add(new Question("dfdfdfdfdfdfdfdfdf", answersList, rightAnswersList));
        testTask.setQuestionsList(questions);
        new ShowTaskGI(testTask);
    }
}
