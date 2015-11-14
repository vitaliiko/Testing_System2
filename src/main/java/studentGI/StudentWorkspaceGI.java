package studentGI;

import components.BoxPanel;
import components.MainFrame;
import components.TableParameters;
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
    private JButton startButton;
    private JButton viewResultButton;

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
        prepareButtons();
        addOnToolsPanel(new BoxPanel(viewResultButton, startButton), new JPanel());
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
        testTaskTable.getSelectionModel().addListSelectionListener(e -> {
            testTaskManager.setCurrentTest(testTaskTable.getSelectedRow());
            startButton.setEnabled(true);
            viewResultButton.setEnabled(true);
        });
        testTaskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    startButton.doClick();
                }
            }
        });
    }

    private void prepareButtons() {
        startButton = new JButton("Старт");
        startButton.setEnabled(false);
        startButton.addActionListener(e ->
                new StartTestGI(this, studentManager.getCurrentUser().getTestTaskWrapperList().
                        get(testTaskTable.getSelectedRow())));

        viewResultButton = new JButton("Переглянути результат");
        viewResultButton.setEnabled(false);
    }

    private void prepareLabelPanel() {
        labelPanel = new BoxPanel(BoxLayout.Y_AXIS);

        completedTestsCount = new JLabel("0");
        labelPanel.add(new BoxPanel(new JLabel("Тестів складено: "), completedTestsCount));
    }
}
