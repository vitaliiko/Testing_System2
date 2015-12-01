package studentGI;

import components.AutoCompleteComboBox;
import components.BoxPanel;
import components.FrameUtils;
import components.LabelComponentPanel;
import usersClasses.Student;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

import static components.SingleMessage.*;

public class StudentAuthGI extends JFrame {

    private static final int COLUMNS_COUNT = 35;

    private JPanel loginPanel;
    private JComboBox<Object> groupsBox;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    private StudentManager studentManager;

    public StudentAuthGI() throws HeadlessException {
        super("Вхід");
        studentManager = new StudentManager();

        FrameUtils.setLookAndFill();

        prepareLoginPanel();
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        getContentPane().add(getMessageInstance(), BorderLayout.NORTH);
        setupFrame();
    }

    private void setupFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(new EmptyBorder(135, 50, 145, 80));
        JPanel fieldsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        prepareGroupsBox();
        fieldsPanel.add(new LabelComponentPanel("Група: ", groupsBox));

        prepareNameField();
        fieldsPanel.add(new LabelComponentPanel("ПІП: ", nameField));

        preparePasswordField();
        fieldsPanel.add(new LabelComponentPanel("Пароль: ", passwordField));

        loginPanel.add(fieldsPanel, BorderLayout.EAST);

        prepareLoginButton();
        prepareCancelButton();
        JPanel buttonsPanel = new BoxPanel(loginButton, cancelButton);
        buttonsPanel.setBorder(new EmptyBorder(0, 35, 0, 0));
        loginPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void prepareNameField() {
        nameField = new JTextField(COLUMNS_COUNT);
        nameField.setEnabled(false);
        nameField.getDocument().addDocumentListener(new LoginTypeListener());
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String studentName = nameField.getText();
                StudentsGroup group = studentManager.getStudentGroup((String) groupsBox.getSelectedItem());
                if (group != null) {
                    if (studentManager.getUsersNameList(group).contains(studentName)) {
                        checkForPassword(group, studentName);
                    } else {
                        setWarningMessage("В цій групі немає такого студента");
                    }
                }
            }
        });
    }

    private void checkForPassword(StudentsGroup group, String studentName) {
        for (Student student : group.getUsersSet()) {
            if (student.getUserName().equals(studentName) && student.isPasswordEmpty()) {
                setDefaultMessage("Додайте пароль до свого облікового запису");
                return;
            } else {
                setEmptyMessage();
            }
        }
    }

    private void preparePasswordField() {
        passwordField = new JPasswordField(COLUMNS_COUNT);
        passwordField.setEnabled(false);
        passwordField.getDocument().addDocumentListener(new LoginTypeListener());
    }

    private void prepareGroupsBox() {
        groupsBox = new AutoCompleteComboBox<>(studentManager.getGroupNamesList().toArray());
        groupsBox.setSelectedIndex(-1);
        groupsBox.setEditable(true);
        ((JTextField) groupsBox.getEditor().getEditorComponent()).getDocument()
                .addDocumentListener(new LoginTypeListener());

    }

    private void prepareLoginButton() {
        loginButton = new JButton("Вхід");
        loginButton.setEnabled(false);
        loginButton.addActionListener(e -> {
            StudentsGroup group = studentManager.getStudentGroup((String) groupsBox.getSelectedItem());
            try {
                if (group != null) {
                    if (studentManager.authorizeUser(nameField.getText(), passwordField.getPassword(), group)) {
                        new StudentWorkspaceGI(studentManager);
                        dispose();
                    } else {
                        setWarningMessage(WRONG_USER_OR_PASS);
                    }
                } else {
                    setWarningMessage(WRONG_GROUP);
                }
            } catch (IOException e1) {
                setWarningMessage(e1.getMessage());
            }
        });
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Скасувати");
        cancelButton.addActionListener(e -> dispose());
    }

    private class LoginTypeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            loginButton.setEnabled(groupsBox.getSelectedItem() != null
                    && !nameField.getText().isEmpty()
                    && passwordField.getPassword().length != 0);
            String groupName = ((JTextField) groupsBox.getEditor().getEditorComponent()).getText();
            nameField.setEnabled(!groupName.isEmpty());
            passwordField.setEnabled(!groupName.isEmpty());
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
