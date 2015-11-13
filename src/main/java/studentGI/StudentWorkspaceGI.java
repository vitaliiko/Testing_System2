package studentGI;

import components.BoxPanel;
import components.MainFrame;
import components.TableParameters;
import testingClasses.TestTask;
import testingClasses.TestTaskWrapper;
import usersClasses.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentWorkspaceGI extends MainFrame {

    private JTable testTaskTable;
    private TableParameters<TestTaskWrapper> testTaskTableParameters;
    private JPanel labelPanel;
    private JLabel completedTestsCount;
    private JLabel notCompletedTestCount;

    public StudentWorkspaceGI(StudentManager studentManager) throws HeadlessException {
        super("title", studentManager);
        this.studentManager.saveUserSet();
        frameSetup();
    }

    @Override
    public void frameSetup() {
        fillContainer();
        fillToolsPanel();
        setTabbedItems("Список тестів", "Складені тести");
        addListenerToTabbedList(e -> {});
        super.frameSetup();
    }

    @Override
    public void fillToolsPanel() {

    }

    @Override
    public void fillContainer() {
        prepareTestTasksTable();
        addOnContainer(new JScrollPane(testTaskTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    }

    private void prepareTestTasksTable() {
        testTaskManager.wrappingTests(studentManager.getCurrentUser());
        testTaskTableParameters = new TableParameters<>(studentManager.getCurrentUser().getTestTaskWrapperList());
        testTaskTable = createTable(testTaskTableParameters);
        testTaskTable.getSelectionModel().addListSelectionListener(e ->
                testTaskManager.setCurrentTest(testTaskTable.getSelectedRow()));
        testTaskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TestTaskWrapper testTaskWrapper = studentManager.getCurrentUser().getTestTaskWrapperList().
                            get(testTaskTable.getSelectedRow());
                    new PassingTheTestGI(testTaskWrapper.createCard(), testTaskWrapper.getTestTask().getTimeLimit());
                }
            }
        });
    }

    private void prepareLabelPanel() {
        labelPanel = new BoxPanel(BoxLayout.Y_AXIS);

        completedTestsCount = new JLabel("0");
        labelPanel.add(new BoxPanel(new JLabel("ʳ������ ��������� �����: "), completedTestsCount));

        notCompletedTestCount = new JLabel(String.valueOf(testTaskTable.getRowCount()));
        labelPanel.add(new BoxPanel(new JLabel("ʳ������ �����, �� ��������� �������: "),
                notCompletedTestCount));
    }
}
