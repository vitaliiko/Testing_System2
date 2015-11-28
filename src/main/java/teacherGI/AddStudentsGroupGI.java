package teacherGI;

import components.BoxPanel;
import components.FrameUtils;
import components.SingleMessage;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;
import usersClasses.Teacher;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

public class AddStudentsGroupGI extends JDialog {

    private StudentsGroup studentsGroup = null;
    private StudentManager studentManager;
    private TeacherManager teacherManager;

    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField facultyField;
    private JComboBox<Teacher> curatorBox;
    private JButton completeButton;
    private JButton cancelButton;

    public AddStudentsGroupGI(Frame owner, TeacherManager teacherManager, StudentManager studentManager) {
        super(owner, "Нова група студентів");
        this.studentManager = studentManager;
        this.teacherManager = teacherManager;

        FrameUtils.setLookAndFill();
        prepareMainPanel();
        getContentPane().add(SingleMessage.getMessageInstance(), BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setupDialog();
    }

    public AddStudentsGroupGI(Frame owner, TeacherManager teacherManager, StudentsGroup studentsGroup,
                              StudentManager studentManager) {
        super(owner, "Редагування інформації про групу");
        this.teacherManager = teacherManager;
        this.studentsGroup = studentsGroup;
        this.studentManager = studentManager;

        FrameUtils.setLookAndFill();
        prepareMainPanel();
        getContentPane().add(SingleMessage.getMessageInstance(), BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        fillFields();
        setupDialog();
    }

    private void setupDialog() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(415, 300));
        setIconImage(new ImageIcon("resources/add_group.png").getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 20, 50, 20));

        mainPanel.add(FrameUtils.createLabelGridPanel("Назва:", "Факультет:", "Кафедра:", "Куратор:"),
                BorderLayout.WEST);

        prepareFields();
        mainPanel.add(FrameUtils.createComponentsGridPanel(nameField, facultyField, departmentField, curatorBox),
                BorderLayout.CENTER);

        prepareCancelButton();
        prepareCompleteButton();
        mainPanel.add(new BoxPanel(completeButton, cancelButton), BorderLayout.SOUTH);
    }

    private void fillFields() {
        nameField.setText(studentsGroup.getName());
        departmentField.setText(studentsGroup.getDepartment());
        facultyField.setText(studentsGroup.getFaculty());
        curatorBox.setSelectedItem(studentsGroup.getCurator());
        completeButton.setEnabled(false);
    }

    private void prepareFields() {
        DocumentListener listener = new TypeListener();

        nameField = new JTextField();
        nameField.getDocument().addDocumentListener(listener);

        departmentField = new JTextField();
        departmentField.getDocument().addDocumentListener(listener);

        facultyField = new JTextField();
        facultyField.getDocument().addDocumentListener(listener);

        DefaultComboBoxModel<Teacher> comboBoxModel = new DefaultComboBoxModel<>();
        teacherManager.getUserSet().forEach(comboBoxModel::addElement);
        curatorBox = new JComboBox<>(comboBoxModel);
        curatorBox.setSelectedItem(teacherManager.getCurrentUser());
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Скасувати");
        cancelButton.addActionListener(e -> dispose());
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            try {
                if (studentsGroup == null) {
                    studentManager.addStudentsGroup(new StudentsGroup(nameField.getText(),
                                                                facultyField.getText(),
                                                                departmentField.getText(),
                                                                (Teacher) curatorBox.getSelectedItem()));
                } else {
                    String name = nameField.getText();
                    if (!studentsGroup.getName().equals(name)) {
                        studentManager.checkStudentsGroupName(name);
                    }
                    studentsGroup.setName(name);
                    studentsGroup.setDepartment(departmentField.getText());
                    studentsGroup.setFaculty(facultyField.getText());
                    studentsGroup.setCurator((Teacher) curatorBox.getSelectedItem());
                    studentManager.saveUserSet();
                }
                dispose();
            } catch (IOException e1) {
                SingleMessage.setWarningMessage(e1.getMessage());
                completeButton.setEnabled(false);
            }
        });
    }

    private class TypeListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            completeButton.setEnabled(!nameField.getText().isEmpty()
                    && !facultyField.getText().isEmpty()
                    && !departmentField.getText().isEmpty());
            SingleMessage.setEmptyMessage();
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
