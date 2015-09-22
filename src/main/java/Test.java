import supporting.IOFileHandling;
import testingClasses.Question;
import testingClasses.TestTask;
import userGI.AddQuestionGI;
import userGI.ShowTaskGI;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        new ShowTaskGI();

//        new ShowTaskGI(IOFileHandling.loadTestTask("Модуль_1"));

//        new AddQuestionGI(5);

//        ArrayList<String> answersList = new ArrayList<>();
//        answersList.add("1");
//        answersList.add("2");
//        answersList.add("3");
//        ArrayList<String> rightAnswersList = new ArrayList<>();
//        rightAnswersList.add("2");
//        new AddQuestionGI(new Question("dfdfdf", answersList, rightAnswersList), 5);

    }
}
