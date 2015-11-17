package studentGI;

import components.BoxPanel;
import components.FrameUtils;
import components.QuestionPanel;
import testingClasses.TestTaskWrapper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultViewerGI extends JDialog {

    private JPanel resultPanel;
    private JPanel statPanel;
    private TestTaskWrapper testTaskWrapper;

    public ResultViewerGI(JFrame frame, TestTaskWrapper testTaskWrapper) {
        super(frame, "Результати", true);
        this.testTaskWrapper = testTaskWrapper;
        this.resultPanel = testTaskWrapper.getResultPanel();
        FrameUtils.setLookAndFill();

        prepareStatPanel();
        JPanel mainPanel = new BoxPanel(BoxLayout.Y_AXIS, statPanel, new JSeparator(), resultPanel);
        getContentPane().add(new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        dialogSetup();
    }

    private void dialogSetup() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));
        setResizable(false);
        setIconImage(new ImageIcon("resources/result.png").getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareStatPanel() {
        statPanel = new JPanel(new BorderLayout());
        statPanel.setPreferredSize(new Dimension(220, 130));
        statPanel.setBackground(testTaskWrapper.getStatus() < 3 ? QuestionPanel.RED_COLOR : QuestionPanel.GREEN_COLOR);
        statPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Font font = new Font("Arial", Font.PLAIN, 13);

        statPanel.add(FrameUtils.createLabelGridPanel(JLabel.RIGHT, font, 20, "Назва тесту: ", "Дисципліна: ",
                "Дано правильних відповідей: ", "Кількість балів: ", "Статус: ", "Спроб залишилось: "),
                BorderLayout.WEST);

        JPanel infoPanel = FrameUtils.createLabelGridPanel(JLabel.LEFT, font, 10,
                testTaskWrapper.getTestTask().getTaskName(),
                testTaskWrapper.getTestTask().getDisciplineName(),
                String.valueOf(testTaskWrapper.getRightAnswersCount() +
                        " / " + testTaskWrapper.getTestTask().getQuestionsLimit()),
                String.valueOf(testTaskWrapper.getPoints()),
                String.valueOf(testTaskWrapper.getStatusName()),
                String.valueOf(testTaskWrapper.getAttemptsLeft()));
        infoPanel.setPreferredSize(new Dimension(220, 80));
        statPanel.add(infoPanel, BorderLayout.CENTER);
    }
}
