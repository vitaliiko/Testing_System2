package userGI;

import supporting.IOFileHandling;
import testingClasses.TestTask;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestTaskNameGI extends JDialog {

    private TestTask testTask;
    private JButton completeButton;
    private JButton cancelButton;
    private JTextField taskNameField;
    private JTextField disciplineNameField;

    public TestTaskNameGI() {
        prepareTaskNamePanel();
        prepareDisciplineNamePanel();
        prepareButtonPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public TestTask getTestTask() {
        return testTask;
    }

    public void prepareTaskNamePanel() {
        taskNameField = new JTextField(20);
        taskNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (taskNameField.getText().isEmpty() || disciplineNameField.getText().isEmpty()) {
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
        });
        JPanel taskNamePanel = new JPanel();
        taskNamePanel.add(new JLabel("Назва тесту: "));
        taskNamePanel.add(taskNameField);
        getContentPane().add(taskNamePanel, BorderLayout.NORTH);
    }

    public void prepareDisciplineNamePanel() {
        disciplineNameField = new JTextField(20);
        disciplineNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (taskNameField.getText().isEmpty() || disciplineNameField.getText().isEmpty()) {
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
        });
        JPanel disciplineNamePanel = new JPanel();
        disciplineNamePanel.add(new JLabel("Назва дисципліни"));
        disciplineNamePanel.add(disciplineNameField);
        getContentPane().add(disciplineNamePanel, BorderLayout.CENTER);
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
            testTask = new TestTask(taskNameField.getText(), disciplineNameField.getText(), null);
            IOFileHandling.saveTestTask(testTask, taskNameField.getText());
            dispose();
        });
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        completeButton.addActionListener(e -> {
            System.out.println("close");
            dispose();
        });
    }
}


