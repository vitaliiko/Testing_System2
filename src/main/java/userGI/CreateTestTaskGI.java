package userGI;

import panelsAndFrames.MainFrame;
import testingClasses.TestTask;
import testingClasses.TestTaskManager;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CreateTestTaskGI extends JDialog {

    private TestTask testTask;
    private JButton completeButton;
    private JButton cancelButton;
    private JTextField taskNameField;
    private JTextField subjectNameField;

    private TeacherManager teacherManager;
    private TestTaskManager testTaskManager;
    private JFrame frame;

    public CreateTestTaskGI(JFrame frame, TeacherManager teacherManager, TestTaskManager testTaskManager) {
        super(frame);

        this.frame = frame;
        this.teacherManager = teacherManager;
        this.testTaskManager = testTaskManager;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        setupDialog();
    }

    private void setupDialog() {
        setTitle("Створення тесту");
        setModal(true);
        setSize(new Dimension(400, 120));
        prepareLabelPanel();
        prepareFieldsPanel();
        prepareButtonPanel();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void prepareLabelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Border border = new EmptyBorder(8, 5, 4 ,0);

        JLabel testNameLabel = new JLabel("Назва тесту:");
        testNameLabel.setAlignmentX(RIGHT_ALIGNMENT);
        testNameLabel.setBorder(border);
        panel.add(testNameLabel);

        JLabel subjectNameLabel = new JLabel("Назва дисципліни:");
        subjectNameLabel.setAlignmentX(RIGHT_ALIGNMENT);
        subjectNameLabel.setBorder(border);
        panel.add(subjectNameLabel);

        getContentPane().add(panel, BorderLayout.WEST);
    }

    private void prepareFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        taskNameField = new JTextField(20);
        taskNameField.getDocument().addDocumentListener(new InputListener());
        panel.add(taskNameField);

        subjectNameField = new JTextField(20);
        subjectNameField.getDocument().addDocumentListener(new InputListener());
        panel.add(subjectNameField);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void prepareButtonPanel() {
        JPanel buttonPanel = new JPanel();
        prepareCompleteButton();
        buttonPanel.add(completeButton);
        prepareCancelButton();
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Створити");
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            testTask = new TestTask(taskNameField.getText(), subjectNameField.getText(),
                    teacherManager.getCurrentTeacher().toString());
            testTaskManager.addTest(testTask);
            testTaskManager.saveTests();
            frame.dispose();
            new ShowTaskGI(teacherManager, testTaskManager.getCurrentTestIndex());
            dispose();
        });
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
    }

    private class InputListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (taskNameField.getText().isEmpty() || subjectNameField.getText().isEmpty()) {
                completeButton.setEnabled(false);
            } else {
                completeButton.setEnabled(true);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            insertUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            insertUpdate(e);
        }
    }
}


