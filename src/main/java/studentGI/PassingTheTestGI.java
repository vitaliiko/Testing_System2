package studentGI;

import components.BoxPanel;

import javax.swing.*;
import java.awt.*;

public class PassingTheTestGI extends JWindow {

    private JPanel progressPanel;
    private JPanel emptyPanel;
    private JButton completeButton;

    public PassingTheTestGI(JPanel panel) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        getContentPane().add(new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

        prepareProgressPanel();
        getContentPane().add(progressPanel, BorderLayout.WEST);
        prepareEmptyPanel();
        getContentPane().add(emptyPanel, BorderLayout.EAST);

        setVisible(true);

    }

    private void prepareProgressPanel() {
        progressPanel = new BoxPanel(BoxLayout.Y_AXIS);
        progressPanel.add(new JLabel("dddddddddddddddddddddgggggdddddddd"));
        prepareCompleteButton();
        progressPanel.add(completeButton);
    }

    private void prepareEmptyPanel() {
        emptyPanel = new BoxPanel();
        emptyPanel.add(new JLabel("dddddddddddddddddddddgggggdddddddd"));
    }

    private void prepareCompleteButton() {
        completeButton = new JButton("Завершити");
        completeButton.addActionListener(e -> dispose());
    }
}
