package studentGI;

import components.AnswerRadioBoxPanel;
import components.BoxPanel;
import components.QuestionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class PassingTheTestGI extends JWindow {

    private JPanel progressPanel;
    private JPanel emptyPanel;
    private JPanel questionsPanel;
    private JProgressBar progressBar;
    private JButton completeButton;
    private JLabel progressLabel;
    private JLabel timeLabel;
    private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private int timeLimit;

    public PassingTheTestGI(JPanel questionsPanel, int timeLimit) {
        this.questionsPanel = questionsPanel;
        this.timeLimit = timeLimit * 60;
        gd.setFullScreenWindow(this);
        JScrollPane scrollPane = new JScrollPane(questionsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        addListenersToQuestionPanel();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        prepareProgressPanel();
        getContentPane().add(progressPanel, BorderLayout.WEST);
        prepareEmptyPanel();
        getContentPane().add(emptyPanel, BorderLayout.EAST);

        setVisible(true);
        scrollPane.getViewport().setViewPosition(new Point(0, 0));

    }

    private void prepareProgressPanel() {
        progressPanel = new BoxPanel(BoxLayout.Y_AXIS);
        progressPanel.setPreferredSize(new Dimension(gd.getFullScreenWindow().getWidth() / 5,
                gd.getFullScreenWindow().getHeight()));

        progressBar = new JProgressBar(0, questionsPanel.getComponentCount());
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);

        progressLabel = new JLabel("0/" + questionsPanel.getComponentCount());
        progressPanel.add(progressLabel);

        if (timeLimit != 0) {
            prepareTimer();
            progressPanel.add(timeLabel);
        }

        prepareCompleteButton();
        progressPanel.add(completeButton);
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

    private void prepareTimer() {
        timeLabel = new JLabel();
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
