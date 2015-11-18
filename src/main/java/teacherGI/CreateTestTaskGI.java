package teacherGI;

import components.BoxPanel;
import components.FrameUtils;
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
    private JTextField disciplineField;

    private TeacherManager teacherManager;
    private TestTaskManager testTaskManager;
    private JFrame frame;

    public CreateTestTaskGI(JFrame frame, TeacherManager teacherManager, TestTaskManager testTaskManager) {
        super(frame);
        this.frame = frame;
        this.teacherManager = teacherManager;
        this.testTaskManager = testTaskManager;

        FrameUtils.setLookAndFill();

        JPanel labelsPanel = FrameUtils.createLabelGridPanel("Назва тесту:", "Назва дисципліни:");
        labelsPanel.setBorder(new EmptyBorder(10, 10, 10, 0));
        getContentPane().add(labelsPanel, BorderLayout.WEST);

        prepareFields();
        JPanel fieldsPanel = FrameUtils.createComponentsGridPanel(taskNameField, disciplineField);
        fieldsPanel.setBorder(new EmptyBorder(10, 5, 10, 10));
        getContentPane().add(fieldsPanel, BorderLayout.CENTER);

        prepareCancelButton();
        prepareCompleteButton();
        getContentPane().add(new BoxPanel(completeButton, cancelButton), BorderLayout.SOUTH);

        setupDialog();
    }

    private void setupDialog() {
        setTitle("Створення тесту");
        setModal(true);
        setSize(new Dimension(400, 140));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void prepareFields() {
        taskNameField = new JTextField(20);
        taskNameField.getDocument().addDocumentListener(new InputListener());

        disciplineField = new JTextField(20);
        disciplineField.getDocument().addDocumentListener(new InputListener());
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Створити");
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            testTask = new TestTask(taskNameField.getText(), disciplineField.getText(),
                    teacherManager.getCurrentUser().toString());
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
            if (taskNameField.getText().isEmpty() || disciplineField.getText().isEmpty()) {
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


