package testingClasses;

import usersClasses.Student;
import usersClasses.User;
import usersClasses.UserDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestTask implements Serializable, UserDAO {

    private static final int PUBLIC_ATR = 0;
    private static final int PRIVATE_ATR = 1;
    private static final int VIEW_ONLY_ATR = 3;

    private String taskName;
    private String disciplineName;
    private String creatorName;
    private int attribute;
    private int answersLimit;
    private int questionsLimit;
    private int timeLimit;
    private int attemptsLimit;

    private ArrayList<Question> questionsList = new ArrayList<>();
    private ArrayList<ArrayList<Question>> questionGroupsList = new ArrayList<>();
    private ArrayList<String> authorsList = new ArrayList<>();
    private ArrayList<String> studentGroupsList = new ArrayList<>();
    private ArrayList<Student> notAllowedStudentsList = new ArrayList<>();

    public TestTask(String taskName, String disciplineName, String creatorName) {
        this.taskName = taskName;
        this.disciplineName = disciplineName;
        this.creatorName = creatorName;
        attribute = PRIVATE_ATR;
        answersLimit = 5;
        questionsLimit = 30;
        timeLimit = 60;
        attemptsLimit = 2;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
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

    public ArrayList<String> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<String> authorsList) {
        this.authorsList = (ArrayList<String>) authorsList;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public int getAttemptsLimit() {
        return attemptsLimit;
    }

    public void setAttemptsLimit(int attemptsLimit) {
        this.attemptsLimit = attemptsLimit;
    }

    public ArrayList<ArrayList<Question>> getQuestionGroupsList() {
        return questionGroupsList;
    }

    public void setQuestionGroupsList(ArrayList<ArrayList<Question>> questionGroupsList) {
        this.questionGroupsList = questionGroupsList;
    }

    public ArrayList<String> getStudentGroupsList() {
        return studentGroupsList;
    }

    public void setStudentGroupsList(ArrayList<String> studentGroupsList) {
        this.studentGroupsList = studentGroupsList;
    }

    public ArrayList<Student> getNotAllowedStudentsList() {
        return notAllowedStudentsList;
    }

    public void setNotAllowedStudentsList(ArrayList<Student> notAllowedStudentsList) {
        this.notAllowedStudentsList = notAllowedStudentsList;
    }

    public ArrayList<String> createQuestionGroupsNames() {
        ArrayList<String> dataList = new ArrayList<>();
        for (ArrayList<Question> questionGroup : questionGroupsList) {
            String questionGroupName = "";
            for (Question question : questionGroup) {
                questionGroupName += questionsList.indexOf(question) + ", ";
            }
            dataList.add(questionGroupName);
        }
        return dataList;
    }

    public ArrayList<String> getQuestionNamesList() {
        ArrayList<String> questionNamesList = new ArrayList<>();
        for (Question question : questionsList) {
            questionNamesList.add(question.getTask());
        }
        return questionNamesList;
    }

    @Override
    public <T extends User> Set<T> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(int index) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public int getUserIndex(User user) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTask testTask = (TestTask) o;

        return taskName.equals(testTask.taskName);
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

    @Override
    public String toString() {
        String authors = "\t";
        if (attribute != PUBLIC_ATR) {
            int i = 0;
            authors += "Автори: ";
            while (i < 3 && i < authorsList.size()) {
                authors += authorsList.get(i) + (i == 2 || i == authorsList.size() - 1 ? "" : ", ");
                i++;
            }
        }
        return taskName + "\n" + "\tКількість запитань: " + questionsList.size() + " " + authors;
    }
}
