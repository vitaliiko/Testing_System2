package supporting;

import testingClasses.TestTask;
import usersClasses.StudentsGroup;
import usersClasses.Teacher;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IOFileHandling {

    public static final String QUESTIONS_SER = "IOFiles/questions.ser";
    public static final String TEST_TASK_SER = "IOFiles/theTestTask.ser";
    public static final String RESOURCES = "resources/";
    public final static String TEACHERS_SER = "IOFiles/teachers.ser";
    public final static String STUDENTS_SER = "IOFiles/students.ser";

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

    public static void saveTestTasks(List<TestTask> testTaskList) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(TEST_TASK_SER));
            os.writeObject(testTaskList);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error when saving data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
    }

    public static ArrayList<TestTask> loadTestTasks() {
        List<TestTask> testTaskList = null;
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(TEST_TASK_SER));
            testTaskList = (List<TestTask>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Error when reading data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
        return (ArrayList<TestTask>) testTaskList;
    }

    public static void saveUserSet(Set<Teacher> teacherSet, String fileName) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(teacherSet);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error when saving data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
    }

    public static Set<Teacher> loadUserSet(String fileName) {
        Set<Teacher> usersSet = null;
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(fileName));
            usersSet = (Set<Teacher>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Error when reading data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
        return usersSet;
    }

    public static void saveStuentsGroupSet(Set<StudentsGroup> studentsGroupSet) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(STUDENTS_SER));
            os.writeObject(studentsGroupSet);
            os.close();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error when saving data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
    }

    public static Set<StudentsGroup> loadStudentsGroupSet() {
        Set<StudentsGroup> studentsGroupSet = null;
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(STUDENTS_SER));
            studentsGroupSet = (Set<StudentsGroup>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Error when reading data base file", "ACHTUNG!",
                    JOptionPane.DEFAULT_OPTION);
        }
        return studentsGroupSet;
    }
}
