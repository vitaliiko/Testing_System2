package userGI;

import components.BoxPanel;
import components.MainFrame;
import supporting.IOFileHandling;
import supporting.TableParameters;
import testingClasses.TestTask;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TeacherWorkspaceGI extends MainFrame {

    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton settingsButton;
    private JTable testTaskTable;
    private JTable studentsGroupTable;
    private JTable studentsTable;
    private TableParameters<TestTask> testTaskTableParameters;
    private TableParameters<StudentsGroup> studentsGroupTableParameters;
    private TableParameters<Student> studentTableParameters;

    public TeacherWorkspaceGI(TeacherManager teacherManager) {
        super("Робоче середовище", teacherManager);
        System.out.println("Hello!");
        frameSetup();
    }

    @Override
    public void frameSetup() {
        fillContainer();
        fillToolsPanel();
        setTabbedItems("Список тестів", "Список груп студентів");
        addListenerToTabbedList(new SelectionListener());
        setMinimumSize(new Dimension(700, 400));
        setSize(new Dimension(924, 520));
        setLocationRelativeTo(null);
        setVisible(true);
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
        addOnContainer(new JScrollPane(testTaskTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        prepareStudentsGroupTable();
        addOnContainer(new JScrollPane(studentsGroupTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    }

    private void prepareTestTasksTable() {
        testTaskTableParameters = new TableParameters<>(testTaskManager.getTestTaskList());
        testTaskTable = createTable(testTaskTableParameters);
        testTaskTable.getSelectionModel().addListSelectionListener(e -> {
            testTaskManager.setCurrentTest(testTaskTable.getSelectedRow());
            removeButton.setEnabled(testTaskManager.getCurrentTest().isCreator(teacherManager.getCurrentTeacher()));
            editButton.setEnabled(testTaskManager.getCurrentTest().isAuthor(teacherManager.getCurrentTeacher()));
            settingsButton.setEnabled(testTaskManager.getCurrentTest().isCreator(teacherManager.getCurrentTeacher()));
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

    private void prepareStudentsGroupTable() {
        studentsGroupTableParameters = new TableParameters<>(new ArrayList<>(studentManager.getStudentsGroupSet()));
        studentsGroupTable = createTable(studentsGroupTableParameters);
        studentsGroupTable.getSelectionModel().addListSelectionListener(e -> {
            removeButton.setEnabled(true);
            settingsButton.setEnabled(true);
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

    public class SelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

        }
    }
}
