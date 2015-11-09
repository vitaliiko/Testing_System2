package studentGI;

import components.AutoCompleteComboBox;
import components.BoxPanel;
import components.FrameUtils;
import components.LabelComponentPanel;
import supporting.Message;
import teacherGI.TeacherWorkspaceGI;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class StudentAuthGI extends JFrame {

    private static final int COLUMNS_COUNT = 35;

    private JPanel loginPanel;
    private JPanel signUpPanel;
    private JPanel fieldsPanel;
    private JComboBox<Object> teacherNamesBox;
    private JComboBox<Object> groupsBox;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private StudentManager studentManager;

    public StudentAuthGI() throws HeadlessException {
        super("Âõ³ä");
        studentManager = new StudentManager();

        FrameUtils.setLookAndFill();
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

        prepareUsernameBox();
        fieldsPanel.add(new LabelComponentPanel("Ï²Ï: ", teacherNamesBox), BorderLayout.EAST);

        passwordField = new JPasswordField(COLUMNS_COUNT);
        passwordField.getDocument().addDocumentListener(new LoginTypeListener());
        fieldsPanel.add(new LabelComponentPanel("Ïàðîëü: ", passwordField));

        loginPanel.add(fieldsPanel, BorderLayout.EAST);

        prepareLoginButton();
        loginPanel.add(new BoxPanel(loginButton), BorderLayout.SOUTH);
    }

    private void prepareGroupsBox() {
        groupsBox = new AutoCompleteComboBox<>(studentManager.getGroupNamesList().toArray());
        groupsBox.setSelectedIndex(-1);
        groupsBox.setEditable(true);
        ((JTextField) groupsBox.getEditor().getEditorComponent()).getDocument().
                addDocumentListener(new LoginTypeListener());
        groupsBox.addItemListener(e -> {
            StudentsGroup studentsGroup = studentManager.getStudentGroup((String) groupsBox.getSelectedItem());
            if (studentsGroup != null) {
                teacherNamesBox.removeAll();
                studentsGroup.getAllUsers().forEach(teacherNamesBox::addItem);
                teacherNamesBox.setEnabled(true);
            } else {
                teacherNamesBox.setEnabled(false);
            }
        });
    }

    private void prepareUsernameBox() {
        teacherNamesBox = new AutoCompleteComboBox<>(new String[]{""});
        teacherNamesBox.setEnabled(false);
        teacherNamesBox.setSelectedIndex(-1);
        teacherNamesBox.setEditable(true);
        ((JTextField) teacherNamesBox.getEditor().getEditorComponent()).getDocument().
                addDocumentListener(new LoginTypeListener());
    }

    private void prepareLoginButton() {
        loginButton = new JButton("Âõ³ä");
        loginButton.setEnabled(false);
        loginButton.addActionListener(e -> {
            String userName = ((JTextField) teacherNamesBox.getEditor().getEditorComponent()).getText();
            if (studentManager.authorizedStudent(userName, passwordField.getPassword(),
                    studentManager.getStudentGroup((String) groupsBox.getSelectedItem()))) {
                setVisible(false);
                new StudentWorkspaceGI(studentManager);
                dispose();
            } else {
                messageLabel.setIcon(Message.WARNING_IMAGE);
                messageLabel.setText(Message.WRONG_USER);
            }
        });
    }

    public class LoginTypeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            loginButton.setEnabled(passwordField.getPassword().length != 0 &&
                    teacherNamesBox.getSelectedItem() != null);
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
