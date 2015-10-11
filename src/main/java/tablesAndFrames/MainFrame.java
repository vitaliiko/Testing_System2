package tablesAndFrames;

import javax.swing.*;
import java.awt.*;

public abstract class MainFrame extends JFrame {

    private Container container;
    private JPanel toolsPanel;
    private JList<String> tabbedList;
    private String[] tabbedItems = new String[2];

    public MainFrame(String title) throws HeadlessException {
        super(title);
        mainFrameSetup();
    }

    public void setTabbedItems(String item1, String item2) {
        tabbedItems[0] = item1;
        tabbedItems[1] = item2;
    }

    public void addOnContainer(JComponent... components) {
        for (JComponent component : components) {
            container.add(component);
        }
    }

    public void addOnToolsPanel(JComponent northComponent, JComponent southComponent) {
        toolsPanel.add(northComponent, BorderLayout.NORTH);
        toolsPanel.add(southComponent, BorderLayout.SOUTH);
    }

    private void mainFrameSetup() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        prepareContainer();
        getContentPane().add(container, BorderLayout.CENTER);
        prepareToolsPanel();
        getContentPane().add(toolsPanel, BorderLayout.WEST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void prepareContainer() {
        container = new Container();
        container.setLayout(new CardLayout());
    }

    private void prepareToolsPanel() {
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new BorderLayout());

        prepareTabbedList();
        toolsPanel.add(new BoxPanel(tabbedList), BorderLayout.CENTER);
    }

    private void prepareTabbedList() {
        tabbedList = new JList<>(tabbedItems);
        tabbedList.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedList.addListSelectionListener(e -> {
            if (tabbedList.getSelectedIndex() == 0) {
                ((CardLayout) container.getLayout()).first(container);
            } else {
                ((CardLayout) container.getLayout()).last(container);
            }
        });
    }
}
