package supporting;

import testingClasses.Question;
import testingClasses.TestTask;
import usersClasses.Teacher;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class IOFileHandling {

    public static final String QUESTIONS_SER = "IOFiles/questions.ser";
    public static final String TEST_TASK_SER = "IOFiles/theTestTask.ser";
    public static final String RESOURCES = "resources/";
    private final static String TEACHERS_SER = "IOFiles/teachers.ser";

    public static void saveQuestionsList(ArrayList<Question> questionsList) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(QUESTIONS_SER));
            os.writeObject(questionsList);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при збереженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
    }

    public static ArrayList<Question> loadQuestionsList() {
        ArrayList<Question> questions = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(QUESTIONS_SER));
            questions = (ArrayList<Question>) is.readObject();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return questions;
    }

    public static void saveQuestion(Question question) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(QUESTIONS_SER));
            os.writeObject(question);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при збереженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
    }

    public static Question loadQuestion() {
        Question question = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(QUESTIONS_SER));
            question = (Question) is.readObject();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return question;
    }

    public static void saveTestTask(TestTask testTask, String fileName) {
        fileName = fileName.replace(" ", "_");
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("IOFiles/" + fileName + ".ser"));
            os.writeObject(testTask);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при збереженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
    }

    public static TestTask loadTestTask(String fileName) {
        TestTask testTask = null;
        fileName = fileName.replace(" ", "_");
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("IOFiles/" + fileName + ".ser"));
            testTask = (TestTask) is.readObject();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return testTask;
    }

    public static void saveTeachersSet(Set<Teacher> teacherSet) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(TEACHERS_SER));
            os.writeObject(teacherSet);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error when saving data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
    }

    public static Set<Teacher> loadTeachersSet() {
        Set<Teacher> usersSet = null;
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(TEACHERS_SER));
            usersSet = (Set<Teacher>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Error when reading data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
        return usersSet;
    }
}
