package teacherGI;

import components.*;
import supporting.IOFileHandling;
import supporting.ImageUtils;
import testingClasses.Question;
import testingClasses.TestTask;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ShowTaskGI extends MainFrame {

    private TestTask testTask;
    private ArrayList<Question> questionsList = new ArrayList<>();

    private JPanel browsePanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton completeButton;
    private JButton settingsButton;
    private JLabel questionsCountLabel;
    private JTable questionsTable;
    private TableParameters<Question> questionTableParameters;

    public ShowTaskGI(TeacherManager teacherManager, int currentTest) throws HeadlessException {
        super("Редагування тесту", teacherManager);

        testTaskManager.setCurrentTest(currentTest);
        testTask = testTaskManager.getCurrentTest();
        questionsList = testTaskManager.getCurrentTest().getQuestionsList();
        frameSetup();
    }

    @Override
    public void frameSetup() {
        fillContainer();
        fillToolsPanel();
        setTabbedItems("Редагування", "Перегляд");
        if (testTask.canReadOnly(teacherManager.getCurrentUser())) {
            tabbedList.setSelectedIndex(1);
            tabbedList.setEnabled(false);
            addButton.setEnabled(false);
        }
        addListenerToTabbedList(e -> {
            if (tabbedList.getSelectedIndex() == 0) {
                if (questionsTable.getSelectedRow() != -1) {
                    removeButton.setEnabled(testTask.isCreator(teacherManager.getCurrentUser()));
                    editButton.setEnabled(testTask.isAuthor(teacherManager.getCurrentUser()));
                }
            } else {
                removeButton.setEnabled(false);
                editButton.setEnabled(false);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new TeacherWorkspaceGI(teacherManager);
                dispose();
            }
        });
        super.frameSetup();
    }

    @Override
    public void fillToolsPanel() {
        prepareAddButton();
        prepareEditButton();
        prepareRemoveButton();
        prepareSetupButton();
        questionsCountLabel = new JLabel(String.valueOf(questionsList.size()));

        BoxPanel box = new BoxPanel(BoxLayout.Y_AXIS);
        box.add(new BoxPanel(addButton, editButton, removeButton, settingsButton));
        box.add(new BoxPanel(new JLabel("Кількість запитань: "), questionsCountLabel));

        prepareCompleteButton();
        addOnToolsPanel(box, new BoxPanel(completeButton));
    }

    @Override
    public void fillContainer() {
        prepareQuestionsTable();

        browsePanel = new JPanel();
        browsePanel.setLayout(new BoxLayout(browsePanel, BoxLayout.Y_AXIS));
        repaintBrowsePanel();

        addOnContainer(new JScrollPane(questionsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
                new JScrollPane(browsePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    private void repaintBrowsePanel() {
        browsePanel.removeAll();
        for (int i = 0; i < questionsList.size(); i++) {
            browsePanel.add(createQuestionPanel(i + 1, questionsList.get(i)));
        }
    }

    private JPanel createQuestionPanel(int index, Question question) {
        JPanel questionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        questionPanel.add(FrameUtils.createTextArea(index + ". " + question.getTask()));
        if (question.getImageInByte() != null) {
            questionPanel.add(new ImagePanel(ImageUtils.imageFromByteArr(question.getImageInByte())));
        }
        for (String s : question.getAnswersList()) {
            JTextArea answerArea = FrameUtils.createTextArea(s);
            answerArea.setBorder(new EmptyBorder(5, 40, 3, 0));
            if (question.getRightAnswersList().contains(s)) {
                answerArea.setForeground(Color.GREEN);
            }
            questionPanel.add(answerArea);
        }
        questionPanel.add(new JSeparator());
        return questionPanel;
    }

    private void prepareAddButton() {
        addButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "add.png"));
        addButton.setToolTipText("Додати");
        addButton.addActionListener(e -> {
            AddQuestionGI addQuestionGI = new AddQuestionGI(testTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.add(addQuestionGI.getQuestion());
                        questionsCountLabel.setText(String.valueOf(questionsList.size()));
                        browsePanel.add(createQuestionPanel(questionsList.size(),
                                questionsList.get(questionsList.size() - 1)));
                    }
                }
            });
        });
    }

    private void prepareRemoveButton() {
        removeButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "remove.png"));
        removeButton.setToolTipText("Видалити");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> {
            questionsList.remove(questionsTable.getSelectedRow());
            questionsCountLabel.setText(String.valueOf(questionsList.size()));
            repaintBrowsePanel();
        });
    }

    private void prepareEditButton() {
        editButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "edit.png"));
        editButton.setToolTipText("Редагувати");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            int index = questionsTable.getSelectedRow();
            AddQuestionGI addQuestionGI = new AddQuestionGI(questionsList.get(index), testTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.set(index, addQuestionGI.getQuestion());
                        repaintBrowsePanel();
                    }
                }
            });
        });
    }

    private void prepareSetupButton() {
        settingsButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "settings.png"));
        settingsButton.setToolTipText("Налаштування тесту");
        settingsButton.setEnabled(testTask.isCreator(teacherManager.getCurrentUser()));
        settingsButton.addActionListener(e ->
                new TestTaskSettingsGI(this, testTaskManager, teacherManager, studentManager));
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setAlignmentX(RIGHT_ALIGNMENT);
        completeButton.addActionListener(e -> {
            //testTask.setQuestionsList(questionsList);
            testTaskManager.saveTests();
            new TeacherWorkspaceGI(teacherManager);
            dispose();
        });
    }

    private void prepareQuestionsTable() {
        questionTableParameters = new TableParameters<>(questionsList);
        questionsTable = createTable(questionTableParameters);
        questionsTable.getSelectionModel().addListSelectionListener(e -> {
            removeButton.setEnabled(testTask.isCreator(teacherManager.getCurrentUser()));
            editButton.setEnabled(testTask.isAuthor(teacherManager.getCurrentUser()));
        });
        questionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && editButton.isEnabled()) {
                    editButton.doClick();
                }
            }
        });
    }
}