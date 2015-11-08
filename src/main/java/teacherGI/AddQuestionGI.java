package teacherGI;

import components.BoxPanel;
import components.AnswerBoxPanel;
import supporting.ImageUtils;
import testingClasses.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddQuestionGI extends JFrame {

    private Question question = null;
    private ArrayList<AnswerBoxPanel> answersBoxList;
    private JPanel answersPanel;
    private JPanel questionPanel;
    private JPanel imagePanel;
    private JPanel buttonsPanel;
    private JTextField imageNameField;
    private JTextArea questionArea;
    private JButton openButton;
    private JButton browseImageButton;
    private JButton completeButton;
    private JButton cancelButton;

    private int answersLimit;

    public AddQuestionGI(int answersLimit) {
        super("Додати питання");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.answersLimit = answersLimit;
        prepareImagePanel();
        getContentPane().add(imagePanel, BorderLayout.NORTH);
        prepareQuestionPanel();
        getContentPane().add(questionPanel, BorderLayout.CENTER);
        prepareButtonsPanel();
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        setupWindow();
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

    public void setupWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

    public void prepareOpenButton() {
        openButton = new JButton(new ImageIcon("resources/folder.png"));
        openButton.setToolTipText("Відкрити");
        openButton.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new supporting.ImageFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    ImageUtils.checkImageSize(imagePath);
                    imageNameField.setText(imagePath);
                }
            } catch (IndexOutOfBoundsException e1) {
                JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні зображення",
                        "Попередження", JOptionPane.DEFAULT_OPTION);
            } catch (IOException e1) {
                JOptionPane.showConfirmDialog(null, e1.getMessage(), "Попередження", JOptionPane.DEFAULT_OPTION);
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
                if (question != null) {
                    new ImageBrowserGI(ImageUtils.imageFromByteArr(question.getImageInByte()));
                } else {
                    new ImageBrowserGI(ImageIO.read(new File(imageNameField.getText())));
                }
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
        this.imagePanel = new BoxPanel(BoxLayout.Y_AXIS, new JLabel("Оберіть зображення:"), imagePanel);
    }

    public void prepareImageNameField() {
        imageNameField = new JTextField(35);
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
        questionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        questionArea = new JTextArea(5, 40);
        questionArea.setFont(new Font("Arial", Font.PLAIN, 12));
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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionPanel.add(new JLabel("Текст запитання:"));
        questionPanel.add(scrollPane);
        prepareAnswersPanel();
        questionPanel.add(answersPanel);
    }

    public void prepareAnswersPanel() {
        answersPanel = new BoxPanel(BoxLayout.Y_AXIS);
        answersPanel.setBorder(new TitledBorder("Варіанти відповідей"));
        JScrollPane scrollPane = new JScrollPane(answersPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        answersBoxList = new ArrayList<>();
        for (int i = 0; i < answersLimit; i++) {
            AnswerBoxPanel answerBoxPanel = new AnswerBoxPanel();
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
            if (!questionArea.getText().isEmpty()) {
                int option = JOptionPane.showConfirmDialog(null, "Ви бажаєте зберегти зміни?",
                        null, JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    completeButton.doClick();
                }
                if (option == JOptionPane.NO_OPTION) {
                    dispose();
                }
            } else {
                dispose();
            }
        });
    }

    public void prepareButtonsPanel() {
        buttonsPanel = new JPanel();
        prepareCompleteButton();
        buttonsPanel.add(completeButton);
        prepareCancelButton();
        buttonsPanel.add(cancelButton);
    }

    public Question createQuestion() throws IOException {
        String task;
        String imageName = null;
        byte[] imageInByte = null;
        ArrayList<String> answersList = new ArrayList<>();
        ArrayList<String> rightAnswersList = new ArrayList<>();

        if (!imageNameField.getText().isEmpty()) {
            imageName = imageNameField.getText();
            imageInByte = ImageUtils.imageInByteArr(imageName);
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
                    null, JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new Question(imageName, imageInByte, task, answersList, rightAnswersList);
            }
            if (option == JOptionPane.NO_OPTION) {

            }
        }
        return new Question(imageName, imageInByte, task, answersList, rightAnswersList);
    }
}