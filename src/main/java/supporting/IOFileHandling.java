package supporting;

import testingClasses.Question;
import testingClasses.TestTask;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class IOFileHandling {

    public static final String QUESTIONS_SER = "IOFiles/questions.ser";
    public static final String TEST_TASK_SER = "IOFiles/theTestTask.ser";
    public static final String TEST_TASK_ARCHIVE = "IOFiles/theTestTaskArchive.ser";
    public static final String RESOURCES = "resources/";
    public final static String TEACHERS_SER = "IOFiles/teachers.ser";

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

    public static void saveTestTasks(ArrayList<TestTask> testTaskList, String fileName) {
        fileName = fileName.replace(" ", "_");
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("IOFiles/" + fileName + ".ser"));
            os.writeObject(testTaskList);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при збереженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
    }

    public static ArrayList<TestTask> loadTestTasks() {
        ArrayList<TestTask> testTaskList = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(TEST_TASK_SER));
            testTaskList = (ArrayList<TestTask>) is.readObject();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return testTaskList;
    }

    public static <T> void saveCollection(Collection<T> objects, String path) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
            os.writeObject(objects);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error when saving data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
    }

    public static <T> Collection<T> loadCollection(String path) {
        Collection<T> collection = null;
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(path));
            collection = (Collection<T>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Error when reading data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
        return collection;
    }
}
