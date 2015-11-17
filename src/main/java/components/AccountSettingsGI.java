package components;

import supporting.SingleMessage;
import usersClasses.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class AccountSettingsGI<U extends User, M extends UserManager<U>> extends JDialog {

    private static final int COLUMNS_COUNT = 26;

    private U user;
    private M manager;
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
    private JLabel passwordLabel;
    private TypeListener typeListener;

    public AccountSettingsGI(JFrame frame, M manager) {
        super(frame, "Налаштування облікового запису");
        this.frame = frame;
        this.manager = manager;
        user = manager.getCurrentUser();

        typeListener = new TypeListener();

        FrameUtils.setLookAndFill();

        getContentPane().add(SingleMessage.getInstance(SingleMessage.SETTINGS), BorderLayout.NORTH);
        prepareFieldsPanel();
        getContentPane().add(fieldsPanel, BorderLayout.EAST);

        prepareSaveButton();
        prepareCancelButton();
        getContentPane().add(new BoxPanel(saveButton, cancelButton), BorderLayout.SOUTH);

        dialogSetup();
    }

    private void dialogSetup() {
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

        if (user instanceof Student) {
            fieldsPanel.add(new LabelComponentPanel("Група: ", new JLabel(((Student) user).getGroupName())));
        }

        surnameField = new JTextField(user.getSurname(), COLUMNS_COUNT);
        surnameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Прізвище: ", surnameField));

        nameField = new JTextField(user.getName(), COLUMNS_COUNT);
        nameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Ім\'я: ", nameField));

        secondNameField = new JTextField(user.getSecondName(), COLUMNS_COUNT);
        secondNameField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("По-батькові: ", secondNameField));

        telephoneField = new JTextField(user.getTelephoneNum(), COLUMNS_COUNT);
        telephoneField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("Телефон: ", telephoneField));

        mailField = new JTextField(user.getMailAddress(), COLUMNS_COUNT);
        mailField.getDocument().addDocumentListener(typeListener);
        fieldsPanel.add(new LabelComponentPanel("E-mail: ", mailField));

        prepareRemoveButton();
        JPanel removePanel = new BoxPanel(removeButton);
        removePanel.setBorder(new EmptyBorder(0, 110, 0, 0));
        fieldsPanel.add(removePanel);

        preparePasswordPanel();
        fieldsPanel.add(passwordPanel);
    }

    public void prepareRemoveButton(){
        removeButton = new JButton("Видалити обліковий запис");
        removeButton.setVisible(user instanceof Teacher);
        removeButton.addActionListener(e -> {
            manager.deleteCurrentUser();
            manager.saveUserSet();
            dispose();
            frame.dispose();
        });
    }

    public void preparePasswordPanel() {
        passwordPanel = new BoxPanel(BoxLayout.Y_AXIS);
        passwordPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
        passwordPanel.add(new JSeparator());

        passwordLabel = new JLabel(user.isPasswordEmpty() ? "Додати пароль" : "Змінити пароль");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordPanel.add(passwordLabel);

        currentPasswordField = new JPasswordField(COLUMNS_COUNT);
        currentPasswordField.getDocument().addDocumentListener(typeListener);
        currentPasswordField.setEnabled(!user.isPasswordEmpty());
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
                manager.updateCurrentUserInfo(surnameField.getText(), nameField.getText(),
                        secondNameField.getText(), telephoneField.getText(), mailField.getText());

                if (isNotPasswordFieldsEmpty()) {
                    checkPassword();
                }
                SingleMessage.setDefaultMessage(SingleMessage.SAVED);
                manager.saveUserSet();
                currentPasswordField.setText("");
                newPasswordField.setText("");
                repeatPasswordField.setText("");
                saveButton.setEnabled(false);
            } catch (IOException e1) {
                SingleMessage.setWarningMessage(e1.getMessage());
            }
            currentPasswordField.setEnabled(true);
            passwordLabel.setText("Змінити пароль");
        });
    }

    public void checkPassword() throws IOException {
        if (!user.isPasswordEmpty() && !user.isPasswordsMatches(currentPasswordField.getPassword())) {
            throw new IOException(SingleMessage.INCORRECT_PASSWORD);
        }
        if (!Arrays.equals(newPasswordField.getPassword(), repeatPasswordField.getPassword())) {
            throw new IOException(SingleMessage.PASSWORDS_DOES_NOT_MATCH);
        }
        if (((Validator) manager).validatePassword(newPasswordField.getPassword(), manager)) {
            user.setPassword(newPasswordField.getPassword());
        }
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
    }

    private boolean isPasswordFieldsEmpty() {
        if (user.isPasswordEmpty()) {
            return newPasswordField.getPassword().length == 0 &&
                    repeatPasswordField.getPassword().length == 0;
        }
        return currentPasswordField.getPassword().length == 0 &&
                newPasswordField.getPassword().length == 0 &&
                repeatPasswordField.getPassword().length == 0;
    }

    private boolean isNotPasswordFieldsEmpty() {
        if (user.isPasswordEmpty()) {
            return newPasswordField.getPassword().length != 0 &&
                    repeatPasswordField.getPassword().length != 0;
        }
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
            saveButton.setEnabled(isNotFieldsEmpty() && (isNotPasswordFieldsEmpty() || isPasswordFieldsEmpty()));
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
