package userGI;

import supporting.IOFileHandling;
import table.QuestionTableModel;
import testingClasses.Question;
import testingClasses.TestTask;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShowTaskGI extends JFrame {

    private TestTask theTestTask;
    private ArrayList<JPanel> questionsPanelsList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();
    private JPanel browsePanel;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JList<JPanel> browseQuestionList;
    private DefaultListModel<JPanel> browseListModel;
    private JTable questionsTable;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton completeButton;
    private JButton setupButton;
    private JComboBox<String> browseModeBox;

    public ShowTaskGI() {
        super("Створення тесту");
        frameSetup();
        launchDialog();
    }

    public ShowTaskGI(TestTask theTestTask) {
        super("Редагування тесту");
        this.theTestTask = theTestTask;
        questionsList = theTestTask.getQuestionsList();
        frameSetup();
    }

    public void frameSetup() {
        prepareQuestionsTable();
        getContentPane().add(questionsTable, BorderLayout.CENTER);
        prepareEastPanel();
        getContentPane().add(eastPanel, BorderLayout.EAST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        setMinimumSize(new Dimension(500, 250));
    }

    public void launchDialog() {
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

    public void prepareQuestionsTable() {
        questionsTable = new JTable(new QuestionTableModel(questionsList));
        questionsTable.setDefaultRenderer(Question.class, new QuestionCellRenderer());
        questionsTable.getRowHeight(100);
    }

    public void prepareBrowsePanel() {
        browsePanel = new JPanel();
        browsePanel.setBackground(Color.DARK_GRAY);
        browseListModel = new DefaultListModel<>();
        browseQuestionList = new JList<>(browseListModel);
        if (questionsList.size() != 0) {
            for (Question question : questionsList) {
                browseListModel.addElement(createQuestionPanel(question));
            }
            browseQuestionList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
                JPanel renderer = value;
                renderer.setBackground(isSelected ? Color.lightGray : list.getBackground());
                return renderer;
            });
        } else {
            browsePanel.add(new JLabel("Додайте запитання"));
        }
        JScrollPane scrollPane = new JScrollPane(browseQuestionList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
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
//        questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        questionPanel.add(prepareTextArea(theQuestion.getTask()));
        for (String s : theQuestion.getAnswersList()) {
//            JPanel answerPanel = new JPanel();
//            JLabel answer = new JLabel();
//            if (theQuestion.getRightAnswersList().contains(s)) {
//                answer.setIcon(new ImageIcon("resources/right.png"));
//            } else {
//                answer.setIcon(new ImageIcon("resources/wrong.png"));
//            }
//            answerPanel.add(answer);
//            answerPanel.setAlignmentX(RIGHT_ALIGNMENT);
//            answerPanel.add(prepareTextArea(s));
            questionPanel.add(prepareTextArea(s));
        }
        questionsPanelsList.add(questionPanel);
        return questionPanel;
    }

    public void prepareAddButton() {
        addButton = new JButton("Додати");
        addButton.addActionListener(e -> {
            final AddQuestionGI addQuestionGI = new AddQuestionGI(theTestTask.getAnswersLimit());
            addQuestionGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (addQuestionGI.getQuestion() != null) {
                        questionsList.add(addQuestionGI.getQuestion());
                        prepareBrowsePanel();
                    }
                }
            });
        });
    }

    public void prepareRemoveButton() {
        removeButton = new JButton("Видалити");
        removeButton.addActionListener(e -> {

        });
    }

    public void prepareEditButton() {
        editButton = new JButton("Редагувати");
        editButton.addActionListener(e -> {

        });
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.addActionListener(e -> {
            theTestTask.setQuestionsList(questionsList);
            IOFileHandling.saveTestTask(theTestTask, theTestTask.getTaskName());
        });
    }

    public void prepareBrowseModeBox() {
        String[] items = {"Повністю", "Частково"};
        browseModeBox = new JComboBox<>(items);
    }

    public void prepareEastPanel() {
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        prepareAddButton();
        eastPanel.add(addButton);
        prepareRemoveButton();
        eastPanel.add(removeButton);
        prepareEditButton();
        eastPanel.add(editButton);
        eastPanel.add(new JLabel("Кількість запитань: " + questionsPanelsList.size()));
        eastPanel.add(new JLabel("Режим відображення:"));
        prepareBrowseModeBox();
        eastPanel.add(browseModeBox);
        prepareCompleteButton();
        eastPanel.add(completeButton);
    }

    public class QuestionCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Question question = (Question) value;
            JPanel panel = createQuestionPanel(question);
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }
            return panel;
        }
    }
}