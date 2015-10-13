package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.LabelComponentPanel;
import testingClasses.TestTask;
import usersClasses.StudentController;
import usersClasses.TeacherController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 15;

    private TestTask testTask;
    private TeacherController teacherController;
    private StudentController studentController;

    private JPanel testSettingsPanel;
    private JPanel setLimitPanel;
    private JTextField nameField;
    private JTextField disciplineNameField;
    private JComboBox<Object> attributeBox;
    private JSpinner answersLimit;
    private JSpinner questionsLimit;
    private JSpinner timeLimit;
    private JSpinner attemptLimit;
    private JList<Object> authorsList;
    private JList<Object> groupsList;
    private JList<Object> questionGroupsList;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JButton selectionAuthorsButton;
    private JButton selectionGroupsButton;
    private JButton selectionQuestionsButton;

    public TestTaskSettingsGI(JFrame owner, TestTask testTask, TeacherController teacherController,
                              StudentController studentController) {
        super(owner, "Налаштування тесту");
        this.testTask = testTask;
        this.teacherController = teacherController;
        this.studentController = studentController;

        prepareTestSettingsPanel();
        getContentPane().add(testSettingsPanel, BorderLayout.EAST);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(480, 500));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void prepareTestSettingsPanel() {
        testSettingsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        nameField = new JTextField(testTask.getTaskName(), COLUMNS_COUNT);
        testSettingsPanel.add(new LabelComponentPanel("Назва тесту: ", nameField));

        disciplineNameField = new JTextField(testTask.getDisciplineName(), COLUMNS_COUNT);
        testSettingsPanel.add(new LabelComponentPanel("Назва дисципліни: ", disciplineNameField));

        testSettingsPanel.add(new LabelComponentPanel("Створив: ", new JLabel(testTask.getCreatorName())));

        String[] attributeItems = {"Загальнодоступний", "З обмеженим доступом", "Лише перегляд"};
        attributeBox = new JComboBox<>(attributeItems);
        attributeBox.setSelectedIndex(testTask.getAttribute());
        testSettingsPanel.add(new LabelComponentPanel("Режим доступу: ", attributeBox), new JSeparator());

        prepareSelectionAuthorsButton();
        authorsList = createSelectionList(testTask.getAuthorsList().toArray());
        testSettingsPanel.add(new LabelComponentPanel("Автори: ", new BoxPanel(new JScrollPane(authorsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionAuthorsButton))
        );

        prepareSelectionGroupsButton();
        groupsList = createSelectionList(testTask.getStudentGroupsList().toArray());
        testSettingsPanel.add(new LabelComponentPanel("Групи студентів: ", new BoxPanel(new JScrollPane(groupsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionGroupsButton))
        );

        prepareSetLimitPanel();
        testSettingsPanel.add(setLimitPanel);
    }

    public JList<Object> createSelectionList(Object[] dataList) {
        JList<Object> selectionList = new JList<>(dataList);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setDragEnabled(false);
        selectionList.setVisibleRowCount(5);
        return selectionList;
    }

    public void prepareSelectionAuthorsButton() {
        selectionAuthorsButton = new JButton(new ImageIcon("resources/selection.png"));
        selectionAuthorsButton.addActionListener(e -> {
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір авторів",
                    new ArrayList<>(teacherController.getTeachersNamesList()), testTask.getAuthorsList());
            selectionTableGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    authorsList.setListData(selectionTableGI.getMarkedNamesList().toArray());
                }
            });
        });
    }

    public void prepareSelectionGroupsButton() {
        selectionGroupsButton = new JButton(new ImageIcon("resources/selection.png"));
        selectionGroupsButton.addActionListener(e -> {
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір груп студентів",
                    studentController.getGroupNamesList(), testTask.getStudentGroupsList());
            selectionTableGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    groupsList.setListData(selectionTableGI.getMarkedNamesList().toArray());
                }
            });
        });
    }

    public void prepareSetLimitPanel() {
        setLimitPanel = new BoxPanel(BoxLayout.Y_AXIS);

        answersLimit = new JSpinner(new SpinnerNumberModel(testTask.getAnswersLimit(), 3, 7, 1));
        setLimitPanel.add(new LabelComponentPanel("<html>Максимальна кількість <br>варіантів відповідей: </html>",
                new GridPanel(answersLimit)));

        questionsLimit = new JSpinner(new SpinnerNumberModel(testTask.getQuestionsLimit(), 10, 50, 1));
        setLimitPanel.add(new LabelComponentPanel("<html>Максимальна кількість <br>запитань у тесті: </html>",
                new GridPanel(questionsLimit)));

        timeLimit = new JSpinner(new SpinnerNumberModel(testTask.getTimeLimit(), 0, 80, 5));
        setLimitPanel.add(new LabelComponentPanel("<html>Максимальна кількість <br>часу, хв.: </html>",
                new GridPanel(timeLimit)));

        attemptLimit = new JSpinner(new SpinnerNumberModel(testTask.getAttemptsLimit(), 0, 3, 1));
        setLimitPanel.add(new LabelComponentPanel("<html>Максимальна кількість <br>спроб: </html>",
                new GridPanel(attemptLimit)));
    }

    public class GridPanel extends JPanel {

        public GridPanel(JComponent component) {
            setLayout(new GridLayout(1, 2));
            add(component);
            add(new Container());
        }
    }
}
