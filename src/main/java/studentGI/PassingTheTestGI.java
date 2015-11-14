package studentGI;

import components.BoxPanel;
import components.LabelComponentPanel;
import components.QuestionPanel;
import testingClasses.TestTaskWrapper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassingTheTestGI extends JWindow {


    private JPanel toolsPanel;
    private JPanel emptyPanel;
    private JPanel questionsPanel;
    private JPanel progressPanel;
    private JProgressBar progressBar;
    private JButton completeButton;
    private JLabel progressLabel;
    private JLabel timeLabel;
    private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private int timeLimit;

    public PassingTheTestGI(TestTaskWrapper testTaskWrapper) {
        questionsPanel = testTaskWrapper.createCard();
        timeLimit = testTaskWrapper.getTestTask().getTimeLimit() * 60;
        gd.setFullScreenWindow(this);
        JScrollPane scrollPane = new JScrollPane(questionsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        addListenersToQuestionPanel();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        prepareToolsPanel();
        getContentPane().add(toolsPanel, BorderLayout.WEST);
        prepareEmptyPanel();
        getContentPane().add(emptyPanel, BorderLayout.EAST);

        setVisible(true);
        scrollPane.getViewport().setViewPosition(new Point(0, 0));

    }

    private void prepareToolsPanel() {
        toolsPanel = new JPanel(new BorderLayout());
        toolsPanel.setPreferredSize(new Dimension(gd.getFullScreenWindow().getWidth() / 5,
                gd.getFullScreenWindow().getHeight()));

        prepareProgressPanel();
        toolsPanel.add(progressPanel, BorderLayout.NORTH);

        prepareCompleteButton();
        toolsPanel.add(new BoxPanel(completeButton), BorderLayout.SOUTH);
    }

    private void prepareProgressPanel() {
        progressPanel = new BoxPanel(BoxLayout.Y_AXIS);
        progressPanel.setBorder(new EmptyBorder(15, 5, 0, 5));

        if (timeLimit != 0) {
            timeLabel = new JLabel(timeLimit / 60 + " хв. " + timeLimit % 60 + " сек.");
            timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            timeLabel.setHorizontalAlignment(JLabel.CENTER);
            JLabel label = new JLabel("Залишилось часу: ");
            label.setFont(timeLabel.getFont());
            label.setHorizontalAlignment(JLabel.CENTER);
            progressPanel.add(label);
            progressPanel.add(timeLabel);
            startTimer();
        }

        progressLabel = new JLabel("0/" + questionsPanel.getComponentCount());
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        progressPanel.add(new LabelComponentPanel("Дано відповідей: ", progressLabel));

        progressBar = new JProgressBar(0, questionsPanel.getComponentCount());
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);
    }

    private void prepareEmptyPanel() {
        emptyPanel = new BoxPanel();
        emptyPanel.setPreferredSize(new Dimension(gd.getFullScreenWindow().getWidth() / 5,
                gd.getFullScreenWindow().getHeight()));
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Завершити");
        completeButton.addActionListener(e -> {
            for (Component component : questionsPanel.getComponents()) {
                ((QuestionPanel) component).saveState();
            }
            dispose();
        });
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            timeLimit--;
            timeLabel.setText(String.valueOf(timeLimit / 60 + " хв. " + timeLimit % 60 + " сек."));
            if (timeLimit == 0) {
                completeButton.doClick();
            }
            if (timeLimit == 120) {
                timeLabel.setForeground(Color.RED);
            }
            if (timeLimit == 119) {
                timeLabel.setForeground(Color.BLACK);
            }
            if (timeLimit <= 29) {
                timeLabel.setForeground(timeLabel.getForeground() == Color.BLACK ? Color.RED : Color.BLACK);
            }
        });
        timer.start();
    }

    private void addListenersToQuestionPanel() {
        GetAnswerListener getAnswerListener = new GetAnswerListener();
        for (Component component : questionsPanel.getComponents()) {
            ((QuestionPanel) component).addListeners(getAnswerListener);
        }
    }

    private class GetAnswerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int progress = 0;
            for (Component component : questionsPanel.getComponents()) {
                if (((QuestionPanel) component).isChoiceMade()) {
                    progress++;
                }
            }
            progressLabel.setText(progress + "/" + questionsPanel.getComponentCount());
            progressBar.setValue(progress);
        }
    }
}
