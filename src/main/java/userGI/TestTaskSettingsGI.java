package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.LabelComponentPanel;
import testingClasses.TestTask;
import usersClasses.StudentController;
import usersClasses.TeacherController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.ObjectView;
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
    private JPanel generalPanel;
    private JPanel limitPanel;
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
        prepareSaveButton();
        prepareCancelButton();
        getContentPane().add(new BoxPanel(saveButton, cancelButton), BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(415, 420));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void prepareTabbedPanel() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new EmptyBorder(5, 5, 0, 5));
        tabbedPane.setBackground(Color.WHITE);

        prepareGeneralPanel();
        tabbedPane.addTab("Загальні", generalPanel);

        prepareLimitPanel();
        tabbedPane.addTab("Обмеження", limitPanel);
    }

    public void prepareSaveButton(){
        saveButton = new JButton("Зберегти");
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
    }

    public void prepareRemoveButton() {
        removeButton = new JButton("Видалити");
    }

    public void prepareGeneralPanel() {
        generalPanel = new BoxPanel(new BorderLayout());
        generalPanel.setBackground(Color.WHITE);
        generalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        generalPanel.add(createLabelPanel("Назва: ", "Дисципліна: ", "Створив: ", "Режим доступу: "), BorderLayout.WEST);
        generalPanel.add(createGeneralComponents(), BorderLayout.CENTER);

        prepareSelectionAuthorsButton();
        authorsList = createSelectionList(testTask.getAuthorsList().toArray(), 7);
        JPanel authorsPanel = new BoxPanel(new JScrollPane(authorsList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                selectionAuthorsButton);
        authorsPanel.setBorder(new TitledBorder("Перелік авторів"));
        authorsPanel.setBackground(Color.WHITE);
        generalPanel.add(authorsPanel, BorderLayout.SOUTH);
    }

    private JPanel createLabelPanel(String... strings) {
        JPanel panel = new JPanel(new GridLayout(strings.length, 1, 0, 6));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 5, 8, 0));
        for (String s : strings) {
            JLabel label = new JLabel(s, JLabel.RIGHT);
            panel.add(label);
        }
        return panel;
    }

    private JPanel createCheckBoxPanel(ArrayList<Object> dataList) {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(Color.WHITE);
        for (Object o : dataList) {

        }
    }

    public JPanel createGeneralComponents() {
        JPanel componentsPanel = new JPanel(new GridLayout(4, 1, 0, 6));
        componentsPanel.setBackground(Color.WHITE);
        componentsPanel.setBorder(new EmptyBorder(0, 5, 8, 0));

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

    private void prepareLimitPanel() {
        limitPanel = new JPanel();
        limitPanel.setBackground(Color.WHITE);
        limitPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        limitPanel.add(createLabelPanel("<html>Максимальна кількість варіантів відповідей: </html>",
                "<html>Максимальна кількість запитань у тесті: </html>",
                "<html>Максимальна кількість часу, хв.: </html>",
                "<html>Максимальна кількість спроб: </html>"));
        limitPanel.add(createSpinners());
    }

    public void prepareTestSettingsPanel() {


        prepareSelectionGroupsButton();
        groupsList = createSelectionList(testTask.getStudentGroupsList().toArray(), 5);
        testSettingsPanel.add(new LabelComponentPanel("Групи студентів: ", new BoxPanel(new JScrollPane(groupsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionGroupsButton))
        );


        testSettingsPanel.add(new JSeparator());
        testSettingsPanel.add(new JLabel("Додайте групи запитань, якi не повинні повторюватись у одному тестi"));

        prepareSelectionQuestionsButton();
        questionGroupsList = createSelectionList(testTask.createQuestionGroupsNames().toArray(), 5);
        testSettingsPanel.add(new LabelComponentPanel("Групи запитань: ", new BoxPanel(new JScrollPane(questionGroupsList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        selectionQuestionsButton))
        );
    }

    public JList<Object> createSelectionList(Object[] dataList, int rowCount) {
        JList<Object> selectionList = new JList<>(dataList);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setDragEnabled(false);
        selectionList.setVisibleRowCount(rowCount);
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

    public JPanel createSpinners() {
        JPanel spinners = new JPanel(new GridLayout(4, 1, 0, 6));
        spinners.setBackground(Color.WHITE);

        answersLimit = new JSpinner(new SpinnerNumberModel(testTask.getAnswersLimit(), 3, 7, 1));
        spinners.add(answersLimit);

        questionsLimit = new JSpinner(new SpinnerNumberModel(testTask.getQuestionsLimit(), 10, 50, 1));
        spinners.add(questionsLimit);

        timeLimit = new JSpinner(new SpinnerNumberModel(testTask.getTimeLimit(), 0, 80, 5));
        spinners.add(timeLimit);

        attemptLimit = new JSpinner(new SpinnerNumberModel(testTask.getAttemptsLimit(), 0, 3, 1));
        spinners.add(attemptLimit);

        return spinners;
    }
}
