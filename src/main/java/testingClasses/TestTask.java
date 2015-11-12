package testingClasses;

import usersClasses.Student;
import usersClasses.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestTask implements Serializable {

    public static final int PUBLIC_ATR = 0;
    public static final int PRIVATE_ATR = 1;
    public static final int READ_ONLY_ATR = 2;

    private String taskName;
    private String disciplineName;
    private String description;
    private int attribute;
    private int answersLimit;
    private int questionsLimit;
    private int timeLimit;
    private int attemptsLimit;
    private int minPoint;

    private ArrayList<Question> questionsList = new ArrayList<>();
    private ArrayList<ArrayList<Question>> questionGroupsList = new ArrayList<>();
    private ArrayList<String> authorsList = new ArrayList<>();
    private ArrayList<String> studentGroupsList = new ArrayList<>();
    private ArrayList<Student> notAllowedStudentsList = new ArrayList<>();

    public TestTask(String taskName, String disciplineName, String creatorName) {
        this.taskName = taskName;
        this.disciplineName = disciplineName;
        authorsList.add(creatorName);
        setDefaultSettings();
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
        return authorsList.get(0);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(int minPoint) {
        this.minPoint = minPoint;
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

    public void setStudentGroupsList(List<String> studentGroupsList) {
        this.studentGroupsList = (ArrayList<String>) studentGroupsList;
    }

    public ArrayList<Student> getNotAllowedStudentsList() {
        return notAllowedStudentsList;
    }

    public void setNotAllowedStudentsList(ArrayList<Student> notAllowedStudentsList) {
        this.notAllowedStudentsList = notAllowedStudentsList;
    }

    public boolean isAuthor(Teacher teacher) {
        return authorsList.contains(teacher.getUserName()) || attribute != PRIVATE_ATR;
    }

    public boolean isCreator(Teacher teacher) {
        return authorsList.indexOf(teacher.getUserName()) == 0;
    }

    public boolean canReadOnly(Teacher teacher) {
        return !authorsList.contains(teacher.getUserName()) && attribute == READ_ONLY_ATR;
    }

    private void setDefaultSettings() {
        attribute = PRIVATE_ATR;
        answersLimit = 4;
        questionsLimit = 30;
        timeLimit = 60;
        attemptsLimit = 2;
        minPoint = 60;
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
    public String toString() {
        String authors = "\t";

        if (attribute != PUBLIC_ATR) {
            authors = (authorsList.size() != 1 ? "Автори: " : "Автор: ");
            int i = 0;
            while (i < 3 && i < authorsList.size()) {
                authors += authorsList.get(i) + (i == 2 || i == authorsList.size() - 1 ? "" : ", ");
                i++;
            }
        }

        return taskName + "\n" + "\tКількість запитань: " + questionsList.size() + " " + authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestTask testTask = (TestTask) o;

        return taskName.equals(testTask.taskName) &&
                disciplineName.equals(testTask.disciplineName) &&
                getCreatorName().equals(((TestTask) o).getCreatorName());

    }

    @Override
    public int hashCode() {
        int result = taskName.hashCode();
        result = 31 * result + disciplineName.hashCode();
        result = 31 * result + getCreatorName().hashCode();
        return result;
    }
}
