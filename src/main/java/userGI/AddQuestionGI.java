package userGI;

import supporting.AnswerBoxPanel;
import supporting.IOFileHandling;
import testingClasses.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddQuestionGI extends JFrame {

    private Question question = null;
    private ArrayList<AnswerBoxPanel> answersBoxList;
    private JPanel answersPanel;
    private JTextField imageNameField;
    private JTextArea questionArea;
    private JButton openButton;
    private JButton browseImageButton;
    private JButton completeButton;
    private JButton cancelButton;

    private int answersLimit;

    public AddQuestionGI(int answersLimit) {
        super("Додати питання");
        this.answersLimit = answersLimit;
        IOFileHandling.saveQuestion(null);
        prepareImagePanel();
        prepareQuestionPanel();
        prepareButtonsPanel();
        setupWindow();
    }

    public void setupWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelButton.doClick();
            }
        });
    }

    public AddQuestionGI(Question question, int answersLimit) {
        this(answersLimit);
        setTitle("Редагування");
        imageNameField.setText(question.getImageName());
        questionArea.setText(question.getTask());
        int i = 0;
        for (String s : question.getAnswersList()) {
            answersBoxList.get(i).setText(s);
            answersBoxList.get(i).setEnabledTextArea(true);
            answersBoxList.get(i).setEnabledCheckBox(true);
            answersBoxList.get(i).setSelected(question.getRightAnswersList().contains(s));
            i++;
        }
        if (i <= answersBoxList.size() - 1) {
            answersBoxList.get(i).setEnabledTextArea(true);
        }
    }

    public Question getQuestion() {
        return question;
    }

    public void prepareOpenButton() {
        openButton = new JButton(new ImageIcon("resources/folder.png"));
        openButton.setToolTipText("Відкрити");
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new supporting.ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);
            int returnVal = fileChooser.showOpenDialog(fileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                imageNameField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        openButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void prepareBrowseImageButton() {
        browseImageButton = new JButton(new ImageIcon("resources/image.png"));
        browseImageButton.setToolTipText("Перегляд");
        browseImageButton.setEnabled(false);
        browseImageButton.addActionListener(e -> {
            try {
                new ImageBrowserGI(ImageIO.read(new File(imageNameField.getText())));
            } catch (IOException e1) {
                JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні зображення",
                        "Попередження", JOptionPane.DEFAULT_OPTION);
            }
        });
    }

    public void prepareImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        prepareImageNameField();
        imagePanel.add(imageNameField);
        prepareOpenButton();
        imagePanel.add(openButton);
        prepareBrowseImageButton();
        imagePanel.add(browseImageButton);
        getContentPane().add(imagePanel, BorderLayout.NORTH);
    }

    public void prepareImageNameField() {
        imageNameField = new JTextField(35);
        imageNameField.setBorder(new EmptyBorder(5, 5, 5, 5));
        imageNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                browseImageButton.setEnabled(!imageNameField.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
        });
    }

    public void prepareQuestionPanel() {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        questionArea = new JTextArea(5, 40);
        questionArea.setLineWrap(true);
        questionArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        questionArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (questionArea.getText().isEmpty()) {
                    completeButton.setEnabled(false);
                } else {
                    completeButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
        });
        JScrollPane scrollPane = new JScrollPane(questionArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionPanel.add(scrollPane);
        prepareAnswersPanel();
        questionPanel.add(answersPanel);
        getContentPane().add(questionPanel, BorderLayout.CENTER);
    }

    public void prepareAnswersPanel() {
        answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(answersPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        answersBoxList = new ArrayList<>();
        for (int i = 0; i < answersLimit; i++) {
            final AnswerBoxPanel answerBoxPanel = new AnswerBoxPanel();
            answerBoxPanel.setEnabledTextArea(i == 0);
            answerBoxPanel.addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    int index = answersBoxList.indexOf(answerBoxPanel);
                    if (!answerBoxPanel.getText().isEmpty()) {
                        answerBoxPanel.setEnabledCheckBox(true);
                        if (index != answersBoxList.size() - 1) {
                            answersBoxList.get(index + 1).setEnabledTextArea(true);
                        }
                    } else {
                        answerBoxPanel.setEnabledCheckBox(false);
                        if (index != answersBoxList.size() - 1 &&
                                answersBoxList.get(index + 1).getText().isEmpty()) {
                            answersBoxList.get(index + 1).setEnabledTextArea(false);
                        }
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    insertUpdate(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    insertUpdate(e);
                }
            });

            answersBoxList.add(answerBoxPanel);
            answersPanel.add(answerBoxPanel);
        }
    }

    public void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            try {
                question = createQuestion();
                dispose();
            } catch (IOException e1) {
                JOptionPane.showConfirmDialog(null, e1.getMessage(), "Попередження", JOptionPane.DEFAULT_OPTION);
            }
        });
    }

    public void prepareCancelButton() {
        cancelButton = new JButton("Відмінити");
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Ви бажаєте зберегти зміни?",
                    null, JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                completeButton.doClick();
            }
            if (option == JOptionPane.NO_OPTION) {
                dispose();
            }
        });
    }

    public void prepareButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        prepareCompleteButton();
        buttonsPanel.add(completeButton);
        prepareCancelButton();
        buttonsPanel.add(cancelButton);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    public Question createQuestion() throws IOException {
        String task;
        String imageName = null;
        byte[] imageInByte = null;
        ArrayList<String> answersList = new ArrayList<>();
        ArrayList<String> rightAnswersList = new ArrayList<>();

        if (!imageNameField.getText().isEmpty()) {
            imageName = imageNameField.getText();
            imageInByte = IOFileHandling.imageInByteArr(imageName);
        }
        task = questionArea.getText();
        for (AnswerBoxPanel answer : answersBoxList) {
            if (!answer.getText().isEmpty()) {
                if (!answersList.contains(answer.getText())) {
                    answersList.add(answer.getText());
                    if (answer.isSelected()) {
                        rightAnswersList.add(answer.getText());
                    }
                } else {
                    throw new IOException("Два чи більше однакових варіанта відповідей");
                }
            }
        }
        if (answersList.size() < 3) {
            throw new IOException("Кількість варіантів відповідей повинна бути не меншою 3");
        }
        if (rightAnswersList.size() < 1) {
            throw new IOException("Повинна бути одна або більше правильних відповідей");
        }
        if (rightAnswersList.size() == answersList.size()) {
            int option = JOptionPane.showConfirmDialog(
                    null, "Ви відмітили всі відповіді як правильні. Бажаєте продовжити?",
                    null, JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.NO_OPTION || option == JOptionPane.CANCEL_OPTION) {
                return null;
            }
        }
        return new Question(imageName, imageInByte, task, answersList, rightAnswersList);
    }
}