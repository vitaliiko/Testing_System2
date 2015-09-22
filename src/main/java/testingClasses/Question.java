package testingClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private String imageName;
    private byte[] imageInByte;
    private String task;
    private ArrayList<String> answersList;
    private ArrayList<String> rightAnswersList;

    public Question(String imageName, byte[] imageInByte, String task, ArrayList<String> answersList,
                    ArrayList<String> rightAnswersList) {
        this.imageName = imageName;
        this.imageInByte = imageInByte;
        this.task = task;
        this.answersList = answersList;
        this.rightAnswersList = rightAnswersList;
    }

    public Question(String task, ArrayList<String> answersList, ArrayList<String> rightAnswersList) {
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

    public ArrayList<String> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(ArrayList<String> answersList) {
        this.answersList = answersList;
    }

    public ArrayList<String> getRightAnswersList() {
        return rightAnswersList;
    }

    public void setRightAnswersList(ArrayList<String> rightAnswersList) {
        this.rightAnswersList = rightAnswersList;
    }
}