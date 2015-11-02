package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.ImagePanel;
import panelsAndFrames.MainFrame;
import supporting.IOFileHandling;
import supporting.ImageUtils;
import supporting.TableParameters;
import testingClasses.Question;
import testingClasses.TestTask;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ShowTaskGI extends MainFrame {

    private TestTask theTestTask;
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

    public ShowTaskGI(TeacherManager teacherManager, int currentTestTaskIndex) {
        super("Редагування тесту", teacherManager);
        theTestTask = testTaskManager.getCurrentTest();
        questionsList = testTaskManager.getCurrentTest().getQuestionsList();
        testTaskManager.setCurrentTestIndex(currentTestTaskIndex);
        frameSetup();
    }

    public ShowTaskGI(TeacherManager teacherManager) {
        super("Редагування тесту", teacherManager);
        theTestTask = testTaskManager.getCurrentTest();
        questionsList = testTaskManager.getCurrentTest().getQuestionsList();
        frameSetup();
        launchDialog();
    }

    @Override
    public void frameSetup() {
        fillContainer();
        fillToolsPanel();
        setTabbedItems("Редагування", "Перегляд");
        addListenerToTabbedList(e -> {
            if (tabbedList.getSelectedIndex() == 0) {
                if (questionsTable.getSelectedRow() != -1) {
                    removeButton.setEnabled(true);
                    editButton.setEnabled(true);
                }
            } else {
                removeButton.setEnabled(false);
                editButton.setEnabled(false);
            }
        });
        setMinimumSize(new Dimension(700, 400));
        setSize(new Dimension(924, 520));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void launchDialog() {
        TestTaskNameGI testTaskNameGI = new TestTaskNameGI(this);
        testTaskNameGI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (testTaskNameGI.getTestTask() == null) {
                    System.exit(0);
                } else {
                    theTestTask = testTaskNameGI.getTestTask();
                }
            }
        });
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
        addOnContainer(new JScrollPane(questionsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        prepareBrowsePanel();
        addOnContainer(new JScrollPane(browsePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    private void prepareBrowsePanel() {
        browsePanel = new JPanel();
        browsePanel.setLayout(new BoxLayout(browsePanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < questionsList.size(); i++) {
            browsePanel.add(createQuestionPanel(i + 1, questionsList.get(i)));
        }
    }

    private JTextArea prepareTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setAutoscrolls(false);
        return textArea;
    }

    private JPanel createQuestionPanel(int index, Question theQuestion) {
        JPanel questionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        questionPanel.add(prepareTextArea(index + ". " + theQuestion.getTask()));
        if (theQuestion.getImageInByte() != null) {
            questionPanel.add(new ImagePanel(ImageUtils.imageFromByteArr(theQuestion.getImageInByte())));
        }
        for (int i = 0; i < theQuestion.getAnswersList().size(); i++) {
            String s = theQuestion.getAnswersList().get(i);
            JTextArea answerArea = prepareTextArea("\t" + ((char) (65 + i)) + ". " + s);
            if (theQuestion.getRightAnswersList().contains(s)) {
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
            AddQuestionGI addQuestionGI = new AddQuestionGI(theTestTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.add(addQuestionGI.getQuestion());
                        questionsCountLabel.setText(String.valueOf(questionsList.size()));
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
        });
    }

    private void prepareEditButton() {
        editButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "edit.png"));
        editButton.setToolTipText("Редагувати");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            int index = questionsTable.getSelectedRow();
            AddQuestionGI addQuestionGI = new AddQuestionGI(questionsList.get(index), theTestTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.set(index, addQuestionGI.getQuestion());
                    }
                }
            });
        });
    }

    private void prepareSetupButton() {
        settingsButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "settings.png"));
        settingsButton.setToolTipText("Налаштування тесту");
        settingsButton.addActionListener(e ->
                new TestTaskSettingsGI(this, testTaskManager, teacherManager, studentManager));
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setAlignmentX(RIGHT_ALIGNMENT);
        completeButton.addActionListener(e -> {
            theTestTask.setQuestionsList(questionsList);
            testTaskManager.saveTests();
        });
    }

    private void prepareQuestionsTable() {
        questionTableParameters = new TableParameters<>(questionsList);
        questionsTable = createTable(questionTableParameters);
        questionsTable.getSelectionModel().addListSelectionListener(e -> {
            removeButton.setEnabled(true);
            editButton.setEnabled(true);
        });
        questionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editButton.doClick();
                }
            }
        });
    }
}