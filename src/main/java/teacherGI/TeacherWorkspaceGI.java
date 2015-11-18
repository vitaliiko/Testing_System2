package teacherGI;

import components.BoxPanel;
import components.FrameUtils;
import components.MainFrame;
import supporting.IOFileHandling;
import components.TableParameters;
import testingClasses.TestTask;
import testingClasses.TestTaskWrapper;
import usersClasses.Student;
import usersClasses.StudentsGroup;
import usersClasses.TeacherManager;

import javax.swing.*;
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
        addOnContainer(viewStudentsInfoTab);
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

    private void prepareViewStudentsInfoTab() {
        viewStudentsInfoTab = new JPanel();

        prepareStudentsGroupJList();
        viewStudentsInfoTab.add(FrameUtils.createScroll(studentsGroupJList));

        prepareStudentsJList();
        viewStudentsInfoTab.add(FrameUtils.createScroll(studentsJList));
    }

    private void prepareStudentsInfoPanel() {
        studentsInfoPanel = new JPanel(new BorderLayout());

    }

    private void prepareStudentsGroupJList() {
        studentsGroupListModel = new DefaultListModel<>();
        for (StudentsGroup studentsGroup : studentManager.getStudentsGroupSet()) {
            studentsGroupListModel.addElement(studentsGroup);
        }

        studentsGroupJList = new JList<>(studentsGroupListModel);
        studentsGroupJList.setVisibleRowCount(5);
        studentsGroupJList.addListSelectionListener(e -> {
            studentListModel.removeAllElements();
            int index = studentsGroupJList.getSelectedIndex();
            for (Student student : studentsGroupListModel.getElementAt(index).getUsersSet()) {
                studentListModel.addElement(student);
            }
        });
    }

    private void prepareStudentsJList() {
        studentListModel = new DefaultListModel<>();
        studentsJList = new JList<>(studentListModel);
        studentsJList.setVisibleRowCount(5);
    }
}
