package teacherGI;

import components.BoxPanel;
import components.AnswerBoxPanel;
import components.FrameUtils;
import supporting.ImageUtils;
import testingClasses.Question;
import testingClasses.TestParameters;
import testingClasses.TestTask;

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
import java.nio.file.Paths;
import java.util.*;

import static components.SingleMessage.*;

public class AddQuestionGI extends JFrame {

    private Question question = null;
    private ArrayList<AnswerBoxPanel> answersBoxList;
    private JPanel answersPanel;
    private JPanel questionPanel;
    private JPanel imagePanel;
    private JPanel buttonsPanel;
    private JTextField imageNameField;
    private JTextArea questionArea;
    private JButton openImageButton;
    private JButton browseImageButton;
    private JButton completeButton;
    private JButton cancelButton;

    private TestTask testTask;

    public AddQuestionGI(TestTask testTask) {
        super("Додати питання");

        FrameUtils.setLookAndFill();
        this.testTask = testTask;

        getContentPane().add(getMessageInstance("Заповніть порожні поля"), BorderLayout.NORTH);
        prepareQuestionPanel();
        getContentPane().add(questionPanel, BorderLayout.CENTER);
        prepareButtonsPanel();
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        setupWindow();
    }

    public AddQuestionGI(Question question, TestTask testTask) {
        this(testTask);
        setTitle("Редагування");
        this.question = question;
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
        setDefaultMessage("Редагування запитання");
    }

    public Question getQuestion() {
        return question;
    }

    private void setupWindow() {
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

    private void prepareOpenButton() {
        openImageButton = new JButton(new ImageIcon("resources/folder.png"));
        openImageButton.setToolTipText("Відкрити");
        openImageButton.addActionListener(e -> {
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
                setWarningMessage("Виникла помилка при завантаженні зображення");
            } catch (IOException e1) {
                setWarningMessage(e1.getMessage());
            }
        });
        openImageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void prepareBrowseImageButton() {
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

    private void prepareImagePanel() {
        prepareImageNameField();
        prepareOpenButton();
        prepareBrowseImageButton();
        imagePanel = new BoxPanel(new JLabel("Оберіть зображення: "), imageNameField,
                openImageButton, browseImageButton);
        imagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void prepareImageNameField() {
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

    private void prepareQuestionPanel() {
        questionPanel = new BoxPanel(BoxLayout.Y_AXIS);
        questionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        prepareImagePanel();
        questionPanel.add(imagePanel);

        JScrollPane scrollPane = FrameUtils.createScroll(questionArea = createTextArea());
        scrollPane.setBorder(new TitledBorder("Текст запитання:"));
        questionPanel.add(scrollPane);

        prepareAnswersPanel();
        questionPanel.add(answersPanel);
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(5, 40);
        textArea.setFont(FrameUtils.MAIN_FONT);
        textArea.setLineWrap(true);
        textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                completeButton.setEnabled(!textArea.getText().isEmpty());
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
        return textArea;
    }

    private void prepareAnswersPanel() {
        answersPanel = new BoxPanel(BoxLayout.Y_AXIS);
        answersPanel.setBorder(new TitledBorder("Варіанти відповідей"));
        answersBoxList = new ArrayList<>();
        for (int i = 0; i < testTask.getAnswersLimit(); i++) {
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
                        if (index != answersBoxList.size() - 1 && answersBoxList.get(index + 1).getText().isEmpty()) {
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

    private void prepareCompleteButton() {
        completeButton = new JButton("Готово");
        completeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        completeButton.setEnabled(false);
        completeButton.addActionListener(e -> {
            try {
                question = createQuestion();
                if (question != null) {
                    dispose();
                }
            } catch (IOException e1) {
                JOptionPane.showConfirmDialog(null, e1.getMessage(), "Попередження", JOptionPane.DEFAULT_OPTION);
            }
        });
    }

    private void prepareCancelButton() {
        cancelButton = new JButton("Скасувати");
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

    private void prepareButtonsPanel() {
        buttonsPanel = new JPanel();
        prepareCompleteButton();
        buttonsPanel.add(completeButton);
        prepareCancelButton();
        buttonsPanel.add(cancelButton);
    }

    private Question createQuestion() throws IOException {
        String task;
        String imageName = null;
        byte[] imageInByte = null;
        ArrayList<String> answersList = new ArrayList<>();
        ArrayList<String> rightAnswersList = new ArrayList<>();

        if (!imageNameField.getText().isEmpty() && question != null && question.getImageName() == null) {
            imageName = Paths.get(imageNameField.getText()).getFileName().toString();
            imageInByte = ImageUtils.imageInByteArr(imageName);
        } else if (question != null) {
            imageName = question.getImageName();
            imageInByte= question.getImageInByte();
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
                    setWarningMessage("Два чи більше однакових варіанта відповідей");
                    return null;
                }
            }
        }

        if (answersList.size() < 3) {
            setWarningMessage("Кількість варіантів відповідей повинна бути не меншою 3");
            return null;
        }
        if (testTask.getAllowWithoutRightAnswers() != TestParameters.ALLOW && rightAnswersList.size() < 1) {
            setWarningMessage("Повинна бути одна або більше правильних відповідей");
            return null;
        }

        int answersCount = rightAnswersList.size();
        if (testTask.getAllowAllRightAnswers() != TestParameters.ALLOW
                && answersCount != 0 && answersCount == answersList.size()) {
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