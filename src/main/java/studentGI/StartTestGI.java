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
        labelsPanel = new JPanel(new BorderLayout());

        labelsPanel.add(FrameUtils.createLabelGridPanel(JLabel.RIGHT, "Назва тесту: ", "Дисципліна: ",
                "Кількість запитань: ", "Кількість часу: ","Мінімальна кількість балів: ", "Спроб залишилось: "),
                BorderLayout.WEST);

        int time = testTaskWrapper.getTestTask().getTimeLimit();
        JPanel infoPanel = FrameUtils.createLabelGridPanel(JLabel.LEFT, testTaskWrapper.getTestTask().getTaskName(),
                testTaskWrapper.getTestTask().getDisciplineName(),
                String.valueOf(testTaskWrapper.getTestTask().getQuestionsLimit()),
                time == 0 ? "Без обмежень" : String.valueOf(time) + " хв.",
                String.valueOf(testTaskWrapper.getTestTask().getMinPoint()),
                String.valueOf(testTaskWrapper.getAttemptsLeft()));
        infoPanel.setPreferredSize(new Dimension(220, 80));
        labelsPanel.add(infoPanel, BorderLayout.CENTER);
    }

    private void prepareButtons() {
        startButton = new JButton("Старт");
        startButton.addActionListener(e -> {
            testTaskWrapper.decAttemptsLeft();
            new PassingTheTestGI(testTaskWrapper);
            dispose();
        });

        cancelButton = new JButton("відмінити");
        cancelButton.addActionListener(e -> dispose());
    }
}