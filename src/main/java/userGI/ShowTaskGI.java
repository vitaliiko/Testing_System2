package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.MainFrame;
import supporting.IOFileHandling;
import supporting.QuestionTableParameters;
import testingClasses.Question;
import testingClasses.TestTask;
import usersClasses.Teacher;
import usersClasses.TeacherController;

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
    private ArrayList<JPanel> questionPanelList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();

    private JPanel browsePanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton completeButton;
    private JButton settingsButton;
    private JLabel questionsCountLabel;
    private JTable questionsTable;
    private QuestionTableParameters questionTableParameters;

    public ShowTaskGI(Teacher teacher, TeacherController teacherController) {
        super("Створення тесту", teacher, teacherController);
        launchDialog();
        frameSetup();
    }

    public ShowTaskGI(TestTask theTestTask, Teacher teacher, TeacherController teacherController) {
        super("Редагування тесту", teacher, teacherController);
        this.theTestTask = theTestTask;
        questionsList = theTestTask.getQuestionsList();
        frameSetup();
    }

    public void frameSetup() {
        fillContainer();
        fillTollsPanel();
        setTabbedItems("Редагування", "Перегляд");
        addListenerToTabbedList(new SelectionListener());
        setMinimumSize(new Dimension(700, 400));
        setSize(new Dimension(924, 520));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void launchDialog() {
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

    public void fillTollsPanel() {
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

    public void fillContainer() {
        prepareQuestionsTable();
        prepareBrowsePanel();
        addOnContainer(new JScrollPane(questionsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        addOnContainer(new JScrollPane(browsePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    public void prepareBrowsePanel() {
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

    public JPanel createQuestionPanel(int index, Question theQuestion) {
        JPanel questionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionPanel.setFont(new Font("Arial", Font.PLAIN, 12));
        questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        questionPanel.add(prepareTextArea(index + ". " + theQuestion.getTask()));
        for (int i = 0; i < theQuestion.getAnswersList().size(); i++) {
            String s = theQuestion.getAnswersList().get(i);
            JTextArea answerArea = prepareTextArea("\t" + ((char) (65 + i)) + ". " + s);
            if (theQuestion.getRightAnswersList().contains(s)) {
                answerArea.setForeground(Color.GREEN);
            }
            questionPanel.add(answerArea);
        }
        questionPanel.add(new JSeparator());
        questionPanelList.add(questionPanel);
        return questionPanel;
    }

    public void prepareAddButton() {
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

    public void prepareRemoveButton() {
        removeButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "remove.png"));
        removeButton.setToolTipText("Видалити");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> {
            questionsList.remove(questionsTable.getSelectedRow());
            questionsCountLabel.setText(String.valueOf(questionsList.size()));
        });
    }

    public void prepareEditButton() {
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
                        questionTableParameters = new QuestionTableParameters(questionsList);
                    }
                }
            });
        });
    }

    public void prepareSetupButton() {
        settingsButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "settings.png"));
        settingsButton.setToolTipText("Налаштування тесту");
        settingsButton.addActionListener(e -> new TestTaskSettingsGI(this, theTestTask, teacherController, studentController));
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setAlignmentX(RIGHT_ALIGNMENT);
        completeButton.addActionListener(e -> {
            theTestTask.setQuestionsList(questionsList);
            IOFileHandling.saveTestTask(theTestTask, theTestTask.getTaskName());
        });
    }

    public void prepareQuestionsTable() {
        questionTableParameters = new QuestionTableParameters(questionsList);
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

    public class SelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (tabbedList.getSelectedIndex() == 0) {
                if (questionsTable.getSelectedRow() != -1) {
                    removeButton.setEnabled(true);
                    editButton.setEnabled(true);
                }
            } else {
                removeButton.setEnabled(false);
                editButton.setEnabled(false);
            }
        }
    }
}