package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.LabelComponentPanel;
import testingClasses.TestTask;
import usersClasses.StudentController;
import usersClasses.TeacherController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 35;

    private TestTask testTask;
    private TeacherController teacherController;
    private StudentController studentController;

    private JTabbedPane tabbedPane;
    private JPanel testSettingsPanel;
    private JPanel setLimitPanel;
    private JPanel generalPanel;
    private JTextField nameField;
    private JTextField disciplineField;
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

        prepareTabbedPanel();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(480, 600));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setModal(true);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void prepareTabbedPanel() {
        tabbedPane = new JTabbedPane();

        prepareGeneralPanel();
        tabbedPane.addTab("Загальні", new BoxPanel(generalPanel, BorderLayout.EAST));

    }

    public void prepareGeneralPanel() {
        generalPanel = new BoxPanel(new BorderLayout());
        generalPanel.add(createLabelPanel("Назва:", "Дисципліна:", "Створив:", "Режим доступу:"), BorderLayout.WEST);
        generalPanel.add(createGeneralComponents(), BorderLayout.CENTER);

        prepareSelectionAuthorsButton();
        authorsList = createSelectionList(testTask.getAuthorsList().toArray());
        JPanel authorsPanel = new BoxPanel(new JScrollPane(authorsList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                selectionAuthorsButton);
        authorsPanel.setBorder(new TitledBorder("Перелік авторів"));
        generalPanel.add(authorsPanel, BorderLayout.SOUTH);
    }

    private JPanel createLabelPanel(String... strings) {
        JPanel panel = new JPanel(new GridLayout(strings.length, 1));
        for (String s : strings) {
            JLabel label = new JLabel(s, JLabel.RIGHT);
            panel.add(label);
        }
        return panel;
    }

    public JPanel createGeneralComponents() {
        JPanel componentsPanel = new JPanel(new GridLayout(4, 1));

        nameField = new JTextField(testTask.getTaskName(), COLUMNS_COUNT);
        componentsPanel.add(nameField);

        disciplineField = new JTextField(testTask.getDisciplineName(), COLUMNS_COUNT);
        componentsPanel.add(disciplineField);

        componentsPanel.add(new JLabel(testTask.getCreatorName()));

        String[] attributeItems = {"Загальнодоступний", "З обмеженим доступом", "Лише перегляд"};
        attributeBox = new JComboBox<>(attributeItems);
        attributeBox.setSelectedIndex(testTask.getAttribute());
        componentsPanel.add(attributeBox);

        return componentsPanel;
    }

    public void prepareTestSettingsPanel() {


        prepareSelectionGroupsButton();
        groupsList = createSelectionList(testTask.getStudentGroupsList().toArray());
        testSettingsPanel.add(new LabelComponentPanel("Групи студентів: ", new BoxPanel(new JScrollPane(groupsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionGroupsButton))
        );

        prepareSetLimitPanel();
        testSettingsPanel.add(setLimitPanel);

        testSettingsPanel.add(new JSeparator());
        testSettingsPanel.add(new JLabel("Додайте групи запитань, якi не повиннi повторюватись у одному тестi"));

        prepareSelectionQuestionsButton();
        questionGroupsList = createSelectionList(testTask.createQuestionGroupsNames().toArray());
        testSettingsPanel.add(new LabelComponentPanel("Групи запитань: ", new BoxPanel(new JScrollPane(questionGroupsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionQuestionsButton))
        );
    }

    public JList<Object> createSelectionList(Object[] dataList) {
        JList<Object> selectionList = new JList<>(dataList);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setDragEnabled(false);
        selectionList.setVisibleRowCount(6);
        return selectionList;
    }

    public void prepareSelectionAuthorsButton() {
        selectionAuthorsButton = new JButton(new ImageIcon("resources/selection.png"));
        selectionAuthorsButton.addActionListener(e -> {
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір авторів",
                    new ArrayList<>(teacherController.getTeacherSet()), new ArrayList<>(testTask.getAuthorsList()));
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
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір групи студентів",
                    new ArrayList<>(studentController.getStudentsGroupSet()), new ArrayList<>(testTask.getStudentGroupsList()));
            selectionTableGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    groupsList.setListData(selectionTableGI.getMarkedNamesList().toArray());
                }
            });
        });
    }

    public void prepareSelectionQuestionsButton() {
        selectionQuestionsButton = new JButton(new ImageIcon("resources/selection.png"));
        selectionQuestionsButton.addActionListener(e -> {
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір групи запитань",
                    new ArrayList<>(testTask.getQuestionsList()),
                    new ArrayList<>(testTask.getQuestionGroupsList().get(questionGroupsList.getSelectedIndex())));
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
