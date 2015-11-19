package testingClasses;

import usersClasses.DataList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question implements Serializable {

    private String imageName;
    private byte[] imageInByte;
    private String task;
    private List<String> answersList;
    private List<String> rightAnswersList;

    public Question(String imageName, byte[] imageInByte, String task, List<String> answersList,
                    List<String> rightAnswersList) {
        this.imageName = imageName;
        this.imageInByte = imageInByte;
        this.task = task;
        this.answersList = answersList;
        this.rightAnswersList = rightAnswersList;
    }

    public Question(String task, List<String> answersList, List<String> rightAnswersList) {
        this.task = task;
        this.answersList = answersList;
        this.rightAnswersList = rightAnswersList;
        imageInByte = null;
        imageName = "";
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageInByte() {
        return imageInByte;
    }

    public void setImageInByte(byte[] imageInByte) {
        this.imageInByte = imageInByte;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<String> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(ArrayList<String> answersList) {
        this.answersList = answersList;
    }

    public List<String> getRightAnswersList() {
        return rightAnswersList;
    }

    public void setRightAnswersList(ArrayList<String> rightAnswersList) {
        this.rightAnswersList = rightAnswersList;
    }

    @Override
    public String toString() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return !(imageName != null ? !imageName.equals(question.imageName) : question.imageName != null)
                && Arrays.equals(imageInByte, question.imageInByte)
                && task.equals(question.task)
                && answersList.equals(question.answersList)
                && rightAnswersList.equals(question.rightAnswersList);
    }
}