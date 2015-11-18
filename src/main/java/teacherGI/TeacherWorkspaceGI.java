package teacherGI;

import components.BoxPanel;
import components.FrameUtils;
import components.MainFrame;
import supporting.IOFileHandling;
import components.TableParameters;
import supporting.SingleMessage;
import testingClasses.TestTask;
import testingClasses.TestTaskWrapper;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class TeacherWorkspaceGI extends MainFrame {

    private static final int COLUMNS_COUNT = 35;

    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton settingsButton;
    private JTable testTaskTable;
    private JPanel viewStudentsInfoTab;
    private JPanel studentsInfoPanel;
    private JList<StudentsGroup> studentsGroupJList;
    private DefaultListModel<StudentsGroup> studentsGroupListModel;
    private JList<Student> studentsJList;
    private DefaultListModel<Student> studentListModel;
    private JList<TestTaskWrapper> wrapperJList;
    private DefaultListModel<TestTaskWrapper> wrapperListModel;
    private TableParameters<TestTask> testTaskTableParameters;
    private TableParameters<StudentsGroup> studentsGroupTableParameters;
    private TableParameters<Student> studentTableParameters;
    private JComboBox<StudentsGroup> studentGroupsBox;
    private DefaultComboBoxModel<StudentsGroup> comboBoxModel;
    private JPanel testWrapperPanel;
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField secondNameField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JButton saveStudentButton;
    private JButton addNewStudentButton;

    public TeacherWorkspaceGI(TeacherManager teacherManager) {
        super("Робоче середовище", teacherManager);
        frameSetup();
    }

    @Override
    public void frameSetup() {
        fillContainer();
        fillToolsPanel();
        setTabbedItems("Список тестів", "Список груп студентів");
        addListenerToTabbedList(e -> {});
        super.frameSetup();
    }

    @Override
    public void fillToolsPanel() {
        prepareAddButton();
        prepareEditButton();
        prepareRemoveButton();
        prepareSetupButton();

        BoxPanel box = new BoxPanel(BoxLayout.Y_AXIS);
        box.add(new BoxPanel(addButton, editButton, removeButton, settingsButton));

        addOnToolsPanel(box, new JButton("Готово"));
    }

    @Override
    public void fillContainer() {
        prepareTestTasksTable();
        addOnContainer(FrameUtils.createScroll(testTaskTable));

        prepareViewStudentsInfoTab();
        BoxPanel panel = new BoxPanel(SingleMessage.getInstance(), BorderLayout.NORTH);
        panel.add(viewStudentsInfoTab, BorderLayout.CENTER);
        addOnContainer(panel);

//        prepareStudentsGroupTable();
//        addOnContainer(new JScrollPane(studentsGroupTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    }

    private void prepareTestTasksTable() {
        testTaskTableParameters = new TableParameters<>(testTaskManager.getTestTaskList());
        testTaskTable = createTable(testTaskTableParameters);
        testTaskTable.getSelectionModel().addListSelectionListener(e -> {
            testTaskManager.setCurrentTest(testTaskTable.getSelectedRow());
            removeButton.setEnabled(testTaskManager.getCurrentTest().isCreator(teacherManager.getCurrentUser()));
            editButton.setEnabled(testTaskManager.getCurrentTest().isAuthor(teacherManager.getCurrentUser()));
            settingsButton.setEnabled(testTaskManager.getCurrentTest().isAuthor(teacherManager.getCurrentUser()));
        });
        testTaskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && editButton.isEnabled()) {
                    editButton.doClick();
                }
            }
        });
    }

    private void prepareAddButton() {
        addButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "add.png"));
        addButton.setToolTipText("Додати");
        addButton.addActionListener(e -> new CreateTestTaskGI(this, teacherManager, testTaskManager));
    }

    private void prepareRemoveButton() {
        removeButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "remove.png"));
        removeButton.setToolTipText("Видалити");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> {

        });
    }

    private void prepareEditButton() {
        editButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "edit.png"));
        editButton.setToolTipText("Редагувати");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            new ShowTaskGI(teacherManager, testTaskManager.getCurrentTestIndex());
            dispose();
        });
    }

    private void prepareSetupButton() {
        settingsButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "settings.png"));
        settingsButton.setToolTipText("Налаштування тесту");
        settingsButton.setEnabled(false);
        settingsButton.addActionListener(e ->
                new TestTaskSettingsGI(this, testTaskManager, teacherManager, studentManager));
    }

    private void prepareViewStudentsInfoTab() {
        viewStudentsInfoTab = new JPanel(new BorderLayout());

        prepareStudentsGroupJList();
        prepareStudentsJList();
        viewStudentsInfoTab.add(new BoxPanel(FrameUtils.createScroll(studentsGroupJList),
                FrameUtils.createScroll(studentsJList)), BorderLayout.NORTH);

        prepareStudentsInfoPanel();
        viewStudentsInfoTab.add(studentsInfoPanel, BorderLayout.WEST);

        prepareTestWrapperPanel();
        viewStudentsInfoTab.add(testWrapperPanel, BorderLayout.CENTER);
    }

    private void prepareStudentsInfoPanel() {
        studentsInfoPanel = new JPanel(new BorderLayout());
        studentsInfoPanel.setBorder(new TitledBorder("Інформація про студента"));

        Dimension dimension = new Dimension(300, 200);
        studentsInfoPanel.setMaximumSize(dimension);
        studentsInfoPanel.setPreferredSize(dimension);
        studentsInfoPanel.setMinimumSize(dimension);

        JPanel labels = FrameUtils.createLabelGridPanel(JLabel.RIGHT,
                "Прізвище: ", "Ім\'я: ", "Побатькові: ", "Група: ", "Телефон: ", "E-Mail: ");
        studentsInfoPanel.add(labels, BorderLayout.WEST);

        prepareFields();
        JPanel fields = FrameUtils.createComponentsGridPanel(surnameField, nameField, secondNameField,
                studentGroupsBox, telephoneField, emailField);
        studentsInfoPanel.add(fields, BorderLayout.CENTER);

        prepareSaveStudentButton();
        prepareAddNewStudentButton();
        studentsInfoPanel.add(new BoxPanel(saveStudentButton, addNewStudentButton), BorderLayout.SOUTH);
    }

    private void prepareFields() {
        surnameField = new JTextField(COLUMNS_COUNT);

        nameField = new JTextField(COLUMNS_COUNT);

        secondNameField = new JTextField(COLUMNS_COUNT);

        comboBoxModel = new DefaultComboBoxModel<>();
        for (StudentsGroup studentsGroup : studentManager.getStudentsGroupSet()) {
            comboBoxModel.addElement(studentsGroup);
        }
        studentGroupsBox = new JComboBox<>(comboBoxModel);

        telephoneField = new JTextField(COLUMNS_COUNT);

        emailField = new JTextField(COLUMNS_COUNT);

        setFieldsEnabled(false);
    }

    private void setFieldsEnabled(boolean enabled) {
        surnameField.setEnabled(enabled);
        nameField.setEnabled(enabled);
        secondNameField.setEnabled(enabled);
        studentGroupsBox.setEnabled(enabled);
        telephoneField.setEnabled(enabled);
        emailField.setEnabled(enabled);
    }

    private void clearFields() {
        surnameField.setText("");
        nameField.setText("");
        secondNameField.setText("");
        telephoneField.setText("");
        emailField.setText("");
    }

    private void prepareStudentsGroupJList() {
        studentsGroupListModel = new DefaultListModel<>();
        for (StudentsGroup studentsGroup : studentManager.getStudentsGroupSet()) {
            studentsGroupListModel.addElement(studentsGroup);
        }

        studentsGroupJList = new JList<>(studentsGroupListModel);
        studentsGroupJList.setVisibleRowCount(8);
        studentsGroupJList.addListSelectionListener(e -> {
            studentListModel.removeAllElements();
            int index = studentsGroupJList.getSelectedIndex();
            for (Student student : studentsGroupListModel.getElementAt(index).getUsersSet()) {
                studentListModel.addElement(student);
            }
            if (studentListModel.size() > 0) {
                studentsJList.setSelectedIndex(0);
            } else {
                clearFields();
                setFieldsEnabled(false);
            }
        });
    }

    private void prepareStudentsJList() {
        studentListModel = new DefaultListModel<>();
        studentsJList = new JList<>(studentListModel);
        studentsJList.setVisibleRowCount(8);
        studentsJList.addListSelectionListener(e -> {
            Student student = studentsJList.getSelectedValue();
            if (student != null) {
                nameField.setText(student.getName());
                surnameField.setText(student.getSecondName());
                secondNameField.setText(student.getSecondName());
                telephoneField.setText(student.getTelephoneNum());
                emailField.setText(student.getMailAddress());
                studentGroupsBox.setSelectedItem(student.getStudentsGroup());
                setFieldsEnabled(true);
            }
        });
    }

    private void prepareSaveStudentButton() {
        saveStudentButton = new JButton("Зберегти");
        saveStudentButton.setEnabled(false);
        saveStudentButton.addActionListener(e -> {
            try {
                studentManager.updateCurrentUserInfo(surnameField.getText(), nameField.getText(),
                        secondNameField.getText(), (StudentsGroup) studentGroupsBox.getSelectedItem(),
                        telephoneField.getText(), emailField.getText());
            } catch (IOException e1) {
                SingleMessage.setWarningMessage(e1.getMessage());
            }
        });
    }

    private void prepareAddNewStudentButton() {
        addNewStudentButton = new JButton("Додати студента");
    }

    private void prepareTestWrapperPanel() {
        testWrapperPanel = new JPanel();
        testWrapperPanel.setBorder(new TitledBorder("Складені тести"));

        wrapperListModel = new DefaultListModel<>();
        wrapperJList = new JList<>(wrapperListModel);
        wrapperJList.setVisibleRowCount(10);

        testWrapperPanel.add(FrameUtils.createScroll(wrapperJList, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }
}
