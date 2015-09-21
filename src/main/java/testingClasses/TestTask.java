package testingClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class TestTask implements Serializable {

    private String taskName;
    private String disciplineName;
    private String teacherName;
    private int answersLimit;
    private int questionsLimit;
    private int timeLimit;
    private ArrayList<String> availableGroupList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();

    public TestTask(String taskName, String disciplineName, String teacherName) {
        this.taskName = taskName;
        this.disciplineName = disciplineName;
        this.teacherName = teacherName;
        answersLimit = 5;
        questionsLimit = 30;
        timeLimit = 60;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getAnswersLimit() {
        return answersLimit;
    }

    public void setAnswersLimit(int answersLimit) {
        this.answersLimit = answersLimit;
    }

    public int getQuestionsLimit() {
        return questionsLimit;
    }

    public void setQuestionsLimit(int questionsLimit) {
        this.questionsLimit = questionsLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public ArrayList<String> getAvailableGroupList() {
        return availableGroupList;
    }

    public void setAvailableGroupList(ArrayList<String> availableGroupList) {
        this.availableGroupList = availableGroupList;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public void addQuestions(ArrayList<Question> questionsList) {
        this.questionsList.addAll(questionsList);
    }
}
