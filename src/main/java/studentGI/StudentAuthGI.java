package studentGI;

import components.AutoCompleteComboBox;
import components.BoxPanel;
import components.FrameUtils;
import components.LabelComponentPanel;
import supporting.Message;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class StudentAuthGI extends JFrame {

    private static final int COLUMNS_COUNT = 35;

    private JPanel loginPanel;
    private JComboBox<Object> groupsBox;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private StudentManager studentManager;

    public StudentAuthGI() throws HeadlessException {
        super("Вхід");
        studentManager = new StudentManager();

        FrameUtils.setLookAndFill();

        prepareLoginPanel();
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        messageLabel = Message.prepareMessageLabel(Message.LOGIN);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        setupFrame();
    }

    private void setupFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(350, 200));
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        prepareGroupsBox();
        fieldsPanel.add(new LabelComponentPanel("Група: ", groupsBox));

        nameField = new JTextField(COLUMNS_COUNT);
        nameField.getDocument().addDocumentListener(new LoginTypeListener());
        fieldsPanel.add(new LabelComponentPanel("ПІП: ", nameField));

        passwordField = new JPasswordField(COLUMNS_COUNT);
        passwordField.getDocument().addDocumentListener(new LoginTypeListener());
        fieldsPanel.add(new LabelComponentPanel("Пароль: ", passwordField));

        loginPanel.add(fieldsPanel, BorderLayout.EAST);

        prepareLoginButton();
        prepareCancelButton();
        loginPanel.add(new BoxPanel(loginButton, cancelButton), BorderLayout.SOUTH);
    }

    private void prepareGroupsBox() {
        groupsBox = new AutoCompleteComboBox<>(studentManager.getGroupNamesList().toArray());
        groupsBox.setSelectedIndex(-1);
        groupsBox.setEditable(true);
        ((JTextField) groupsBox.getEditor().getEditorComponent()).getDocument().
                addDocumentListener(new LoginTypeListener());

    }

    private void prepareLoginButton() {
        loginButton = new JButton("Вхід");
        loginButton.setEnabled(false);
        loginButton.addActionListener(e -> {
            StudentsGroup group = studentManager.getStudentGroup((String) groupsBox.getSelectedItem());
            if (group != null) {
                if (studentManager.authorizeUser(nameField.getText(), passwordField.getPassword(), group)) {
                    setVisible(false);
                    new StudentWorkspaceGI(studentManager);
                    dispose();
                } else {
                    messageLabel.setIcon(Message.WARNING_IMAGE);
                    messageLabel.setText(Message.WRONG_USER);
                }
            } else {
                messageLabel.setIcon(Message.WARNING_IMAGE);
                messageLabel.setText(Message.WRONG_GROUP);
            }
        });
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
    }

    private class LoginTypeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            loginButton.setEnabled(groupsBox.getSelectedItem() != null && !nameField.getText().isEmpty());
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
