package userGI;

import panelsAndFrames.MainFrame;
import supporting.IOFileHandling;
import testingClasses.TestTask;

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
    private MainFrame frame;

    public CreateTestTaskGI(JFrame frame) {
        super(frame);
        this.frame = (MainFrame) frame;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setTitle("Створення тесту");
        setModal(true);
        setSize(new Dimension(400, 140));
        prepareLabelPanel();
        prepareFieldsPanel();
        prepareButtonPanel();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void prepareLabelPanel() {
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

    public void prepareFieldsPanel() {
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

    public void prepareButtonPanel() {
        JPanel buttonPanel = new JPanel();
        prepareCompleteButton();
        buttonPanel.add(completeButton);
        prepareCancelButton();
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Створити");
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            testTask = new TestTask(taskNameField.getText(), subjectNameField.getText(),
                    frame.getTeacherManager().getCurrentTeacher().toString());
            frame.getTestTaskManager().addTest(testTask);
            frame.getTestTaskManager().setCurrentTestIndex(frame.getTestTaskManager().getTestTaskList().size() - 1);
            dispose();
        });
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        completeButton.addActionListener(e -> dispose());
    }

    public class InputListener implements DocumentListener {
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


