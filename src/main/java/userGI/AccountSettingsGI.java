package userGI;

import components.BoxPanel;
import components.LabelComponentPanel;
import supporting.Message;
import usersClasses.Teacher;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class AccountSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 26;

    private Teacher teacher;
    private TeacherManager teacherManager;
    private JFrame frame;

    private JPanel fieldsPanel;
    private JPanel passwordPanel;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField secondNameField;
    private JTextField telephoneField;
    private JTextField mailField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatPasswordField;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JLabel messageLabel;
    private TypeListener typeListener;

    public AccountSettingsGI(Frame frame, TeacherManager teacherManager) {
        super(frame, "Налаштування облікового запису");
        this.frame = (JFrame) frame;
        this.teacherManager = teacherManager;
        teacher = teacherManager.getCurrentTeacher();
        typeListener = new TypeListener();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }

        messageLabel = Message.prepareMessageLabel(Message.SETTINGS);
        getContentPane().add(messageLabel, BorderLayout.NORTH);
        prepareFieldsPanel();
        getContentPane().add(fieldsPanel, BorderLayout.EAST);

        prepareSaveButton();
        prepareCancelButton();
        getContentPane().add(new BoxPanel(saveButton, cancelButton), BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(335, 430));
        setIconImage(new ImageIcon("resources/account.png").getImage());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void prepareFieldsPanel() {
        fieldsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        surnameField = new JTextField(teacher.getSurname(), COLUMNS_COUNT);
        surnameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Прізвище: ", surnameField));

        nameField = new JTextField(teacher.getName(), COLUMNS_COUNT);
        nameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Ім\'я: ", nameField));

        secondNameField = new JTextField(teacher.getSecondName(), COLUMNS_COUNT);
        secondNameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("По-батькові: ", secondNameField));

        telephoneField = new JTextField(teacher.getTelephoneNum(), COLUMNS_COUNT);
        telephoneField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Телефон: ", telephoneField));

        mailField = new JTextField(teacher.getMailAddress(), COLUMNS_COUNT);
        mailField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("E-mail: ", mailField));

        prepareRemoveButton();
        fieldsPanel.add(new BoxPanel(removeButton));

        preparePasswordPanel();
        fieldsPanel.add(passwordPanel);
    }

    public void prepareRemoveButton(){
        removeButton = new JButton("Видалити обліковий запис");
        removeButton.setHorizontalAlignment(SwingConstants.CENTER);
        removeButton.addActionListener(e -> {
            teacherManager.deleteCurrentTeacher();
            teacherManager.saveTeacherSet();
            dispose();
            frame.dispose();
        });
    }

    public void preparePasswordPanel() {
        passwordPanel = new BoxPanel(BoxLayout.Y_AXIS);
        passwordPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
        passwordPanel.add(new JSeparator());

        JLabel label = new JLabel("Змінити пароль");
        label.setHorizontalAlignment(JLabel.CENTER);
        passwordPanel.add(label);

        currentPasswordField = new JPasswordField(COLUMNS_COUNT);
        currentPasswordField.getDocument().addDocumentListener(typeListener);
        passwordPanel.add(new LabelComponentPanel("Поточний пароль: ", currentPasswordField));

        newPasswordField = new JPasswordField(COLUMNS_COUNT);
        newPasswordField.getDocument().addDocumentListener(typeListener);
        passwordPanel.add(new LabelComponentPanel("Новий пароль: ", newPasswordField));

        repeatPasswordField = new JPasswordField(COLUMNS_COUNT);
        repeatPasswordField.getDocument().addDocumentListener(typeListener);
        passwordPanel.add(new LabelComponentPanel("Повторіть пароль: ", repeatPasswordField));
    }

    public void prepareSaveButton() {
        saveButton = new JButton("Зберегти");
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> {
            try {
                teacherManager.updateCurrentTeacherInfo(surnameField.getText(), nameField.getText(),
                        secondNameField.getText(), telephoneField.getText(), mailField.getText());

                if (isNotPasswordsFieldsEmpty()) {
                    checkPassword();
                }
                messageLabel.setIcon(null);
                messageLabel.setText(Message.SAVED);
                teacherManager.saveTeacherSet();
                currentPasswordField.setText("");
                newPasswordField.setText("");
                repeatPasswordField.setText("");
                saveButton.setEnabled(false);
            } catch (IOException e1) {
                messageLabel.setIcon(Message.WARNING_IMAGE);
                messageLabel.setText(e1.getMessage());
            }
        });
    }

    public void checkPassword() throws IOException {
        if (!teacher.isPasswordsMatches(currentPasswordField.getPassword())){
            throw new IOException(Message.INCORRECT_PASSWORD);
        }
        if (!Arrays.equals(newPasswordField.getPassword(), repeatPasswordField.getPassword())) {
            throw new IOException(Message.PASSWORDS_DOES_NOT_MATCH);
        }
        if (teacherManager.validatePassword(newPasswordField.getPassword())) {
            teacher.setPassword(newPasswordField.getPassword());
        }
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
    }

    private boolean isPasswordsFieldsEmpty() {
        return currentPasswordField.getPassword().length == 0 &&
                    newPasswordField.getPassword().length == 0 &&
                    repeatPasswordField.getPassword().length == 0;
    }

    private boolean isNotPasswordsFieldsEmpty() {
        return currentPasswordField.getPassword().length != 0 &&
                    newPasswordField.getPassword().length != 0 &&
                    repeatPasswordField.getPassword().length != 0;
    }

    private boolean isNotFieldsEmpty() {
        return !nameField.getText().isEmpty() &&
                !surnameField.getText().isEmpty() &&
                !secondNameField.getText().isEmpty();
    }

    public class TypeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            saveButton.setEnabled(isNotFieldsEmpty() && (isNotPasswordsFieldsEmpty() || isPasswordsFieldsEmpty()));
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
