package userGI;

import supporting.IOFileHandling;
import supporting.QuestionTableParameters;
import testingClasses.Question;
import testingClasses.TestTask;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ShowTaskGI extends JFrame {

    private TestTask theTestTask;
    private ArrayList<JPanel> questionPanelList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();
    private JPanel browsePanel;
    private JPanel eastPanel;
    private JPanel buttonPanel;
    private JPanel browseModePanel;
    private JPanel westPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton completeButton;
    private JButton setupButton;
    private JComboBox<String> browseModeBox;
    private JTable questionTable;
    private QuestionTableParameters questionTableParameters;


    public ShowTaskGI() {
        super("Створення тесту");
        frameSetup();
        dialogLaunch();
    }

    public ShowTaskGI(TestTask theTestTask) {
        super("Редагування тесту");
        this.theTestTask = theTestTask;
        questionsList = theTestTask.getQuestionsList();
        frameSetup();
    }

    public void frameSetup() {
        prepareQuestionsTable();
        getContentPane().add(new JScrollPane(questionTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        prepareEastPanel();
        getContentPane().add(eastPanel, BorderLayout.EAST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        setMinimumSize(new Dimension(500, 250));
    }

    public void dialogLaunch() {
        final TestTaskNameGI testTaskNameGI = new TestTaskNameGI(this);
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

    public void prepareBrowsePanel() {
        browsePanel = new JPanel();
        browsePanel.setLayout(new BoxLayout(browsePanel, BoxLayout.Y_AXIS));
        browsePanel.setBackground(Color.DARK_GRAY);

    }

    private JTextArea prepareTextArea(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setAutoscrolls(false);
        return textArea;
    }

    public JPanel createQuestionPanel(Question theQuestion) {
        final JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        questionPanel.add(prepareTextArea(theQuestion.getTask()));
        for (String s : theQuestion.getAnswersList()) {
            JPanel answerPanel = new JPanel();
            JLabel answer = new JLabel();
            if (theQuestion.getRightAnswersList().contains(s)) {
                answer.setIcon(new ImageIcon("resources/right.png"));
            } else {
                answer.setIcon(new ImageIcon("resources/wrong.png"));
            }
            answerPanel.add(answer);
            answerPanel.setAlignmentX(RIGHT_ALIGNMENT);
            answerPanel.add(prepareTextArea(s));
            questionPanel.add(answerPanel);
        }
        questionPanelList.add(questionPanel);
        return questionPanel;
    }

    public void prepareAddButton() {
        addButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "add.png"));
        addButton.setToolTipText("Додати");
        addButton.addActionListener(e -> {
            AddQuestionGI addQuestionGI = new AddQuestionGI(this, theTestTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.add(addQuestionGI.getQuestion());
                    }
                }
            });
        });
    }

    public void prepareRemoveButton() {
        removeButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "remove.png"));
        removeButton.setToolTipText("Видалити");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> questionsList.remove(questionTable.getSelectedRow()));
    }

    public void prepareEditButton() {
        editButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "edit.png"));
        editButton.setToolTipText("Редагувати");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            int index = questionTable.getSelectedRow();
            AddQuestionGI addQuestionGI = new AddQuestionGI(this, questionsList.get(index), theTestTask.getAnswersLimit());
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
        setupButton = new JButton(new ImageIcon(IOFileHandling.RESOURCES + "setup.png"));
        setupButton.setToolTipText("Налаштування");
        setupButton.setEnabled(false);
    }

    public void prepareButtonPanel() {
        buttonPanel = new JPanel();
        prepareAddButton();
        buttonPanel.add(addButton);
        prepareEditButton();
        buttonPanel.add(editButton);
        prepareRemoveButton();
        buttonPanel.add(removeButton);
        prepareSetupButton();
        buttonPanel.add(setupButton);
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setAlignmentX(RIGHT_ALIGNMENT);
        completeButton.addActionListener(e -> {
            theTestTask.setQuestionsList(questionsList);
            IOFileHandling.saveTestTask(theTestTask, theTestTask.getTaskName());
        });
    }

    public void prepareBrowseModeBox() {
        String[] items = {"Повністю", "Частково"};
        browseModeBox = new JComboBox<>(items);
    }

    public void prepareBrowseModePanel() {
        browseModePanel = new JPanel();
        browseModePanel.setLayout(new BoxLayout(browseModePanel, BoxLayout.Y_AXIS));
        browseModePanel.add(new JLabel("Кількість запитань: " + questionsList.size()));
        browseModePanel.add(new JLabel("Режим відображення:"));
        prepareBrowseModeBox();
        browseModePanel.add(browseModeBox);
    }

    public void prepareEastPanel() {
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        prepareButtonPanel();
        eastPanel.add(buttonPanel);
        prepareBrowseModePanel();
        eastPanel.add(browseModePanel);
        prepareCompleteButton();
        JPanel panel = new JPanel();
        panel.add(completeButton);
        eastPanel.add(panel);
    }

    public void prepareQuestionsTable() {
        questionTableParameters = new QuestionTableParameters(questionsList);
        questionTable = new JTable(questionTableParameters);
        questionTable.setDefaultRenderer(Object.class, questionTableParameters);
        questionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionTable.setShowHorizontalLines(false);
        questionTable.setShowVerticalLines(false);
        questionTable.getColumnModel().getColumn(0).setPreferredWidth(8);
        questionTable.getTableHeader().setReorderingAllowed(false);
        questionTable.setTableHeader(null);
        questionTable.getSelectionModel().addListSelectionListener(e -> {
            removeButton.setEnabled(true);
            editButton.setEnabled(true);
        });

        //TableColumn payFormColumn = questionTable.getColumnModel().getColumn(5);
        //payFormColumn.setCellEditor(new DefaultCellEditor(payFormBox));
    }
}