package teacherGI;

import components.BoxPanel;
import components.FrameUtils;
import testingClasses.Question;
import testingClasses.TestTask;
import testingClasses.TestTaskManager;
import usersClasses.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 35;

    private TestTask testTask;
    private TeacherManager teacherManager;
    private StudentManager studentManager;
    private TestTaskManager testTaskManager;

    private JTabbedPane tabbedPane;
    private JPanel generalTabPanel;
    private JPanel descriptionPanel;
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
    private JTextArea descriptionArea;
    private JSpinner answersLimit;
    private JSpinner questionsLimit;
    private JSpinner timeLimit;
    private JSpinner attemptLimit;
    private JSpinner pointLimit;
    private JList<String> questionJList;
    private DefaultListModel<String> listModel;
    private JButton addQuestionsButton;
    private JButton removeGroupButton;
    private JButton saveButton;
    private JButton applyButton;
    private JButton cancelButton;

    private List<Question> questionsGroupList = new ArrayList<>();

    public TestTaskSettingsGI(JFrame owner, TestTaskManager testTaskManager, TeacherManager teacherManager,
                              StudentManager studentManager) {
        super(owner, "Налаштування тесту", true);
        this.teacherManager = teacherManager;
        this.studentManager = studentManager;
        this.testTaskManager = testTaskManager;
        testTask = testTaskManager.getCurrentTest();

        prepareTabbedPanel();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        prepareSaveButton();
        prepareCancelButton();
        prepareApplyButton();
        JPanel buttonsPanel = new BoxPanel(saveButton, cancelButton, applyButton);
        buttonsPanel.setAlignmentX(RIGHT_ALIGNMENT);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        setupDialog();
    }

    private void setupDialog() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(415, 420));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareTabbedPanel() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new EmptyBorder(5, 5, 0, 5));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setFocusable(false);

        if (testTask.isCreator(teacherManager.getCurrentUser())) {
            prepareGeneralTabPanel();
            tabbedPane.addTab("Загальні", generalTabPanel);
        }

        prepareDescriptionPanel();
        tabbedPane.addTab("Опис", descriptionPanel);

        prepareLimitTabPanel();
        tabbedPane.addTab("Обмеження", limitTabPanel);

        prepareStudentsTabPanel();
        tabbedPane.addTab("Студенти", studentsTabPanel);

        prepareQuestionsTabPanel();
        tabbedPane.addTab("Групи запитань", questionsTabPanel);
    }

    private void prepareSaveButton(){
        saveButton = new JButton("OK");
        saveButton.addActionListener(e -> {
            saveSettings();
            dispose();
        });
    }

    private void prepareApplyButton() {
        applyButton = new JButton("Застосувати");
        //applyButton.setEnabled(false);
        applyButton.addActionListener(e -> {
            saveSettings();
            applyButton.setEnabled(false);
        });
    }

    private void saveSettings() {
        if (testTask.isCreator(teacherManager.getCurrentUser())) {
            testTask.setTaskName(nameField.getText());
            testTask.setDisciplineName(disciplineField.getText());
            testTask.setAttribute(attributeBox.getSelectedIndex());
            testTask.setAuthorsList(makeDataListFromCheckBoxPanel(authorsPanel));
            testTask.setDescription(descriptionArea.getText());
        }

        testTask.setAnswersLimit((Integer) answersLimit.getValue());
        testTask.setQuestionsLimit((Integer) questionsLimit.getValue());
        testTask.setTimeLimit((Integer) timeLimit.getValue());
        testTask.setAttemptsLimit((Integer) attemptLimit.getValue());
        testTask.setMinPoint((Integer) pointLimit.getValue());

        testTask.setStudentGroupsList(makeDataListFromCheckBoxPanel(studentsGroupPanel));

        testTaskManager.saveTests();
    }

    private List<String> makeDataListFromCheckBoxPanel(JPanel panel) {
        List<String> dataList = new ArrayList<>();
        for (int i = 2; i < panel.getComponentCount(); i++) {
            JCheckBox checkBox = (JCheckBox) panel.getComponent(i);
            if (!checkBox.isEnabled()) {
                dataList.add(0, checkBox.getText());
            } else if (checkBox.isSelected()) {
                dataList.add(checkBox.getText());
            }
        }
        return dataList;
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> dispose());
    }

    private void prepareGeneralTabPanel() {
        generalTabPanel = new JPanel(new BorderLayout());
        generalTabPanel.setBackground(Color.WHITE);
        generalTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        generalTabPanel.add(FrameUtils.createLabelGridPanel(JLabel.RIGHT,
                "Назва:", "Дисципліна:", "Створив:", "Режим доступу:"), BorderLayout.WEST);
        generalTabPanel.add(createGeneralTabComponents(), BorderLayout.CENTER);

        authorsPanel = createCheckBoxPanel(
                new ArrayList<>(teacherManager.getUserSet()));
        generalTabPanel.add(createScrollPaneWithBorder(authorsPanel, "Автори"), BorderLayout.SOUTH);
    }

    private void prepareDescriptionPanel() {
        descriptionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        descriptionPanel.setBorder(new EmptyBorder(5, 7, 7, 7));

        descriptionArea = new JTextArea(testTask.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(FrameUtils.MAIN_FONT);
        descriptionArea.setEnabled(testTask.isCreator(teacherManager.getCurrentUser()));

        descriptionPanel.add(new JLabel("Опис тестововго завдання"));
        descriptionPanel.add(FrameUtils.createScroll(descriptionArea));
    }

    private JCheckBox createCheckAllBox(JPanel checkBoxPanel) {
        JCheckBox checkAll = new JCheckBox("Відмітити усіх");
        checkAll.setBackground(Color.WHITE);
        checkAll.setFocusable(false);
        checkAll.addItemListener(e -> {
            checkAll.setText(checkAll.isSelected() ? "Зняти усіх" : "Відмітити усіх");
            for (int i = 2; i < checkBoxPanel.getComponentCount(); i++) {
                JCheckBox checkBox = (JCheckBox) checkBoxPanel.getComponent(i);
                if (checkBox.isEnabled()) {
                    checkBox.setSelected(checkAll.isSelected());
                }
            }
        });
        return checkAll;
    }

    private <T extends DataList> JPanel createCheckBoxPanel(List<T> dataList) {
        JPanel checkBoxPanel = new BoxPanel(BoxLayout.Y_AXIS);
        checkBoxPanel.setBackground(Color.WHITE);
        checkBoxPanel.setOpaque(true);

        JCheckBox checkAllBox = createCheckAllBox(checkBoxPanel);
        checkBoxPanel.add(checkAllBox);
        checkBoxPanel.add(new JSeparator());

        int selectedCount = 0;
        for (T o : dataList) {
            JCheckBox checkBox =
                    new JCheckBox(o instanceof User ? ((User) o).getUserName() : ((StudentsGroup) o).getName());
            checkBox.setBackground(Color.WHITE);
            checkBox.setFocusable(false);
            if (o instanceof Teacher) {
                String teacherName = ((Teacher) o).getUserName();
                checkBox.setSelected(testTask.getAuthorsList().contains(teacherName));
                checkBox.setEnabled(testTask.getAuthorsList().indexOf(teacherName) != 0);
            }
            if (o instanceof StudentsGroup) {
                checkBox.setSelected(testTask.getStudentGroupsList().contains(((StudentsGroup) o).getName()));
            }
            if (checkBox.isSelected()) {
                selectedCount++;
            }
            checkBoxPanel.add(checkBox);
        }
        if (selectedCount == dataList.size() && checkAllBox != null) {
            checkAllBox.setSelected(true);
        }
        return checkBoxPanel;
    }

    private JPanel createQuestionCheckBoxPanel(List<Question> questions) {
        JPanel checkBoxPanel = new BoxPanel(BoxLayout.Y_AXIS);
        checkBoxPanel.setBackground(Color.WHITE);
        checkBoxPanel.setOpaque(true);

        int componentsCount = 1;
        for (Question question : questions) {
            JCheckBox checkBox = new JCheckBox(componentsCount + ". " + question.getTask());
            componentsCount++;
            checkBox.setBackground(Color.WHITE);
            checkBox.setFocusable(false);
            checkBoxPanel.add(checkBox);
        }
        return checkBoxPanel;
    }

    private JScrollPane createScrollPaneWithBorder(JPanel panel, String title) {
        JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(100, 188));
        scrollPane.setMaximumSize(new Dimension(100500, 188));
        scrollPane.setBorder(new TitledBorder(title));
        scrollPane.setBackground(Color.WHITE);
        return scrollPane;
    }

    private JPanel createGeneralTabComponents() {
        nameField = new JTextField(testTask.getTaskName(), COLUMNS_COUNT);

        disciplineField = new JTextField(testTask.getDisciplineName(), COLUMNS_COUNT);

        JLabel creatorLabel = new JLabel(testTask.getCreatorName());

        String[] attributeItems = {"Загальнодоступний", "З обмеженим доступом", "Лише перегляд"};
        attributeBox = new JComboBox<>(attributeItems);
        attributeBox.setSelectedIndex(testTask.getAttribute());

        return FrameUtils.createComponentsGridPanel(nameField, disciplineField, creatorLabel, attributeBox);
    }

    private void prepareLimitTabPanel() {
        limitTabPanel = new JPanel(new BorderLayout());
        limitTabPanel.setBackground(Color.WHITE);
        limitTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel limitPanel = new JPanel(new BorderLayout());
        limitPanel.add(FrameUtils.createLabelGridPanel(JLabel.RIGHT, "Максимальна кількість варіантів відповідей:",
                "Максимальна кількість запитань у тесті:",
                "Максимальна кількість часу, хв.:",
                "Максимальна кількість спроб:",
                "Мінімальна кількість балів для сдачі тесту:"), BorderLayout.WEST);
        limitPanel.add(createSpinners(), BorderLayout.CENTER);
        limitPanel.setBorder(new TitledBorder("Обмеження"));
        limitPanel.setBackground(Color.WHITE);
        limitTabPanel.add(limitPanel, BorderLayout.CENTER);
    }

    private JPanel createSpinners() {
        answersLimit = new JSpinner(new SpinnerNumberModel(testTask.getAnswersLimit(), 3, 7, 1));

        questionsLimit = new JSpinner(new SpinnerNumberModel(testTask.getQuestionsLimit(), 10, 50, 1));

        timeLimit = new JSpinner(new SpinnerNumberModel(testTask.getTimeLimit(), 0, 80, 5));

        attemptLimit = new JSpinner(new SpinnerNumberModel(testTask.getAttemptsLimit(), 0, 3, 1));

        pointLimit = new JSpinner(new SpinnerNumberModel(testTask.getMinPoint(), 50, 80, 5));

        return FrameUtils.createComponentsGridPanel(answersLimit, questionsLimit, timeLimit, attemptLimit, pointLimit);
    }

    private void prepareStudentsTabPanel() {
        studentsTabPanel = new BoxPanel(BoxLayout.Y_AXIS);
        studentsTabPanel.setBackground(Color.WHITE);
        studentsTabPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("<html>Оберіть групи студентів, які будуть<br>" +
                "мати доступ до складання даного тесту</html>");
        label.setHorizontalAlignment(JLabel.LEFT);
        studentsTabPanel.add(label, BorderLayout.NORTH);

        studentsGroupPanel = createCheckBoxPanel(new ArrayList<>(studentManager.getStudentsGroupSet()));
        studentsTabPanel.add(createScrollPaneWithBorder(studentsGroupPanel, "Групи студентів"), BorderLayout.CENTER);
    }

    private void prepareQuestionsTabPanel() {
        questionsTabPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionsTabPanel.setBorder(new EmptyBorder(5, 7, 5, 7));

        questionsGroupPanel = createQuestionCheckBoxPanel(testTask.getQuestionsList());
        for (Component c : questionsGroupPanel.getComponents()) {
            ((JCheckBox) c).addActionListener(e -> addQuestionsButton.setEnabled(areTwoBoxesSelected()));
        }

        JScrollPane scrollPane = createScrollPaneWithBorder(questionsGroupPanel, "Оберіть запитання");
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        questionsTabPanel.add(scrollPane);

        prepareAddQuestionButton();
        questionsTabPanel.add(addQuestionsButton);

        prepareQuestionJList();
        questionsTabPanel.add(FrameUtils.createScroll(questionJList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS));

        prepareRemoveGroupButton();
        questionsTabPanel.add(removeGroupButton);
    }

    private void prepareQuestionJList() {
        listModel = new DefaultListModel<>();
        for (ArrayList<Question> list : testTask.getQuestionGroupsList()) {
            String groupName = "";
            for (Question question : list) {
                groupName += testTask.getQuestionsList().indexOf(question) + ", ";
            }
            groupName = groupName.substring(0, groupName.length() - 2);
            listModel.addElement(groupName);
        }
        questionJList = new JList<>(listModel);
        questionJList.setVisibleRowCount(6);
        questionJList.addListSelectionListener(e -> removeGroupButton.setEnabled(true));
    }

    private boolean areTwoBoxesSelected() {
        int selectionCount = 0;
        for (Component c : questionsGroupPanel.getComponents()) {
            if (((JCheckBox) c).isSelected()) {
                selectionCount++;
            }
        }
        return selectionCount > 1;
    }

    private void prepareRemoveGroupButton() {
        removeGroupButton = new JButton("Видалити");
        removeGroupButton.setEnabled(false);
        removeGroupButton.addActionListener(e -> {
            questionsGroupList.remove(questionJList.getSelectedIndex());
            listModel.remove(questionJList.getSelectedIndex());
            removeGroupButton.setEnabled(false);
        });
    }

    private void prepareAddQuestionButton() {
        addQuestionsButton = new JButton("Додати групу");
        addQuestionsButton.setEnabled(false);
        addQuestionsButton.addActionListener(e -> {
            String groupName = "";
            for (int i = 0; i < questionsGroupPanel.getComponentCount(); i++) {
                JCheckBox checkBox = (JCheckBox) questionsGroupPanel.getComponent(i);
                if (checkBox.isSelected()) {
                    questionsGroupList.add(testTask.getQuestionsList().get(i));
                    groupName += i + ", ";
                    checkBox.setSelected(false);
                }
            }
            groupName = groupName.substring(0, groupName.length() - 2);
            listModel.addElement(groupName);
        });
    }
}
