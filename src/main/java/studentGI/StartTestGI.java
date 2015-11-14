package studentGI;

import components.BoxPanel;
import components.FrameUtils;
import components.LabelComponentPanel;
import testingClasses.TestTaskWrapper;

import javax.swing.*;
import java.awt.*;

public class StartTestGI extends JDialog {

    private TestTaskWrapper testTaskWrapper;

    private JPanel labelsPanel;
    private JButton startButton;
    private JButton cancelButton;

    public StartTestGI(Frame frame, TestTaskWrapper testTaskWrapper) {
        super(frame);
        this.testTaskWrapper = testTaskWrapper;

        FrameUtils.setLookAndFill();

        prepareLabelsPanel();
        getContentPane().add(labelsPanel, BorderLayout.EAST);

        prepareButtons();
        getContentPane().add(new BoxPanel(startButton, cancelButton), BorderLayout.SOUTH);

        dialogSetup();
    }

    private void dialogSetup() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(370, 220));
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareLabelsPanel() {
        labelsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        labelsPanel.add(new LabelComponentPanel("Назва тесту: ",
                new JLabel(testTaskWrapper.getTestTask().getTaskName())));
        labelsPanel.add(new LabelComponentPanel("Дисципліна: ",
                new JLabel(testTaskWrapper.getTestTask().getDisciplineName())));
        labelsPanel.add(new LabelComponentPanel("Кількість запитань: ",
                new JLabel(String.valueOf(testTaskWrapper.getTestTask().getQuestionsLimit()))));
        int time = testTaskWrapper.getTestTask().getTimeLimit();
        labelsPanel.add(new LabelComponentPanel("Кількість часу: ",
                new JLabel(time == 0 ? "Без обмежень" : String.valueOf(time) + " хв.")));
        labelsPanel.add(new LabelComponentPanel("Мінімальна кількість балів: ",
                new JLabel(String.valueOf(testTaskWrapper.getTestTask().getMinPoint()))));
        labelsPanel.add(new LabelComponentPanel("Спроб залишилось: ",
                new JLabel(String.valueOf(testTaskWrapper.getAttemptsLeft()))));
    }

    private void prepareButtons() {
        startButton = new JButton("Старт");
        startButton.addActionListener(e -> {
            new PassingTheTestGI(testTaskWrapper);
            dispose();
        });

        cancelButton = new JButton("відмінити");
        cancelButton.addActionListener(e -> dispose());
    }
}
