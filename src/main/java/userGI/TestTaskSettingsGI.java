package userGI;

import panelsAndFrames.BoxPanel;
import testingClasses.TestTask;
import testingClasses.TestTaskManager;
import usersClasses.StudentManager;
import usersClasses.Teacher;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 35;

    private TestTask testTask;
    private TeacherManager teacherManager;
    private StudentManager studentManager;

    private JTabbedPane tabbedPane;
    private JPanel testSettingsPanel;
    private JPanel generalTabPanel;
    private JPanel limitTabPanel;
    private JPanel studentsTabPanel;
    private JPanel questionsTabPanel;
    private JPanel authorsPanel;
    private JPanel studentsGroupPanel;
    private JPanel questionsGroupPanel;
    private JPanel notAllowedStudentsPanel;
    private JTextField nameField;
    private JTextField disciplineField;
    private JComboBox<Object> attributeBox;
    private JSpinner answersLimit;
    private JSpinner questionsLimit;
    private JSpinner timeLimit;
    private JSpinner attemptLimit;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton removeButton;

    public TestTaskSettingsGI(JFrame owner, TestTaskManager testTaskManager, TeacherManager teacherManager,
                              StudentManager studentManager) {
        super(owner, "Налаштування тесту");
        this.testTask = testTaskManager.getCurrentTest();
        this.teacherManager = teacherManager;
        this.studentManager = studentManager;

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
        tabbedPane.setFocusable(false);

        prepareGeneralTabPanel();
        tabbedPane.addTab("Загальні", generalTabPanel);

        prepareLimitTabPanel();
        tabbedPane.addTab("Обмеження", limitTabPanel);

        prepareStudentsTabPanel();
        tabbedPane.addTab("Студенти", studentsTabPanel);
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

    public void prepareGeneralTabPanel() {
        generalTabPanel = new JPanel(new BorderLayout());
        generalTabPanel.setBackground(Color.WHITE);
        generalTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        generalTabPanel.add(createLabelPanel("Назва:", "Дисципліна:", "Створив:", "Режим доступу:"), BorderLayout.WEST);
        generalTabPanel.add(createGeneralTabComponents(), BorderLayout.CENTER);

        authorsPanel = createCheckBoxPanel(
                new ArrayList<>(teacherManager.getTeacherSet()), testTask.getAuthorsList());
        generalTabPanel.add(createScrollPane(authorsPanel, "Автори"), BorderLayout.SOUTH);
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

    private <T> JPanel createCheckBoxPanel(ArrayList<T> dataList, ArrayList<String> markedList) {
        JPanel checkBoxPanel = new BoxPanel(BoxLayout.Y_AXIS);
        checkBoxPanel.setBackground(Color.WHITE);

        JCheckBox checkAll = new JCheckBox("Відмітити усіх");
        checkAll.setBackground(Color.WHITE);
        checkAll.setFocusable(false);
        checkAll.addItemListener(e -> {
            checkAll.setText(checkAll.isSelected() ? "Зняти усіх" : "Відмітити усіх");
            for (int i = 2; i < checkBoxPanel.getComponentCount(); i++) {
                ((JCheckBox) checkBoxPanel.getComponent(i)).setSelected(checkAll.isSelected());
            }
        });
        checkBoxPanel.add(checkAll);
        checkBoxPanel.add(new JSeparator());

        for (T o : dataList) {
            JCheckBox checkBox = new JCheckBox(o.toString(), markedList.contains(o.toString()));
            checkBox.setBackground(Color.WHITE);
            checkBox.setFocusable(false);
            if (o instanceof Teacher) {
                checkBox.setSelected(testTask.getAuthorsList().contains(o.toString()));
            }
            checkBoxPanel.add(checkBox);
        }
        return checkBoxPanel;
    }

    private JScrollPane createScrollPane(JPanel panel, String title) {
        JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(100, 188));
        scrollPane.setBorder(new TitledBorder(title));
        scrollPane.setBackground(Color.WHITE);
        return scrollPane;
    }

    public JPanel createGeneralTabComponents() {
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

    private void prepareLimitTabPanel() {
        limitTabPanel = new JPanel();
        limitTabPanel.setBackground(Color.WHITE);
        limitTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel limitPanel = new JPanel(new BorderLayout());
        limitPanel.add(createLabelPanel("<html>Максимальна кількість<br>варіантів відповідей:</html>",
                "<html>Максимальна кількість<br>запитань у тесті:</html>",
                "<html>Максимальна кількість<br>часу, хв.:</html>",
                "<html>Максимальна кількість<br>спроб:</html>"), BorderLayout.WEST);
        limitPanel.add(createSpinners(),BorderLayout.CENTER);
        limitPanel.setBorder(new TitledBorder("Обмеження"));
        limitPanel.setBackground(Color.WHITE);
        limitTabPanel.add(limitPanel);
    }

    public JPanel createSpinners() {
        JPanel spinners = new JPanel(new GridLayout(4, 1, 0, 6));
        spinners.setBackground(Color.WHITE);
        spinners.setBorder(new EmptyBorder(0, 5, 8, 0));

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

    private void prepareStudentsTabPanel() {
        studentsTabPanel = new BoxPanel(BoxLayout.Y_AXIS);
        studentsTabPanel.setBackground(Color.WHITE);
        studentsTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        studentsGroupPanel = createCheckBoxPanel(new ArrayList<>(studentManager.getStudentsGroupSet()),
                testTask.getStudentGroupsList());
        studentsTabPanel.add(createScrollPane(studentsGroupPanel, "Групи студентів"), BorderLayout.SOUTH);

        JButton notAllowedButton = createButtonAsLink("Вибрати студентів");
        notAllowedButton.setHorizontalAlignment(SwingConstants.CENTER);


        studentsTabPanel.add(notAllowedButton);
    }

    private JButton createButtonAsLink(String title) {
        JButton button = new JButton("<html><u>" + title + "<html>");
        button.setForeground(new Color(0, 144, 255));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
