package components;

import studentGI.StudentAuthGI;
import teacherGI.TeacherAuthGI;
import testingClasses.TestTaskManager;
import usersClasses.StudentManager;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public abstract class MainFrame extends JFrame {

    protected JList<String> tabbedList;
    private Container container;
    private JPanel toolsPanel;
    private String[] tabbedItems = new String[2];
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;

    protected TeacherManager teacherManager;
    protected StudentManager studentManager;
    protected TestTaskManager testTaskManager;

    public MainFrame(String title, TeacherManager teacherManager) throws HeadlessException {
        super(title);
        this.teacherManager = teacherManager;
        testTaskManager = new TestTaskManager();
        studentManager = new StudentManager();
        prepareComponents();
    }

    public MainFrame(String title, StudentManager studentManager) throws HeadlessException {
        super(title);
        this.studentManager = studentManager;
        testTaskManager = new TestTaskManager();
        teacherManager = new TeacherManager();
        prepareComponents();
    }

    private void prepareComponents() {
        FrameUtils.setLookAndFill();
        prepareMenuBar();
        setJMenuBar(menuBar);
        prepareContainer();
        getContentPane().add(container, BorderLayout.CENTER);
        prepareToolsPanel();
        getContentPane().add(toolsPanel, BorderLayout.WEST);
    }

    public void frameSetup() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setSize(new Dimension(924, 520));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
        tabbedList.setSelectedIndex(0);
    }

    public abstract void fillToolsPanel();

    public abstract void fillContainer();

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

    public void addOnToolsPanel(JComponent northComponent) {
        toolsPanel.add(northComponent, BorderLayout.NORTH);
    }

    private void prepareContainer() {
        container = new Container();
        container.setLayout(new CardLayout());
        container.setBackground(Color.WHITE);
    }

    private void prepareToolsPanel() {
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new BorderLayout());

        prepareTabbedList();
        toolsPanel.add(tabbedList, BorderLayout.CENTER);
    }

    private void prepareTabbedList() {
        tabbedList = new JList<>(tabbedItems);
        tabbedList.setFont(FrameUtils.MAIN_FONT);
        tabbedList.setFixedCellHeight(50);
        tabbedList.setBackground(toolsPanel.getBackground());
        tabbedList.setSelectionBackground(Color.WHITE);
        tabbedList.setSelectionForeground(Color.BLACK);
        tabbedList.setFocusable(false);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) tabbedList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        tabbedList.addListSelectionListener(e -> {
            if (tabbedList.getSelectedIndex() == 0) {
                ((CardLayout) container.getLayout()).first(container);
            } else {
                ((CardLayout) container.getLayout()).last(container);
            }
        });
    }

    public JTable createTable(TableParameters parameters) {
        JTable table = new JTable(parameters);
        table.setDefaultRenderer(Object.class, parameters);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setTableHeader(null);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(0).setMinWidth(25);
        columnModel.getColumn(0).setPreferredWidth(8);
        return table;
    }

    public void addListenerToTabbedList(ListSelectionListener listSelectionListener) {
        tabbedList.addListSelectionListener(listSelectionListener);
    }

    private void prepareMenuBar() {
        menuBar = new JMenuBar();
        prepareFileMenu();
        menuBar.add(fileMenu);
        prepareHelpMenu();
        menuBar.add(helpMenu);
    }

    private void prepareFileMenu() {
        fileMenu = new JMenu("Файл");

        JMenuItem logoutItem = new JMenuItem("Вихід");
        logoutItem.setIcon(new ImageIcon("resources/logout.png"));
        logoutItem.addActionListener(e -> {
            if (teacherManager.getCurrentUser() == null) {
                new StudentAuthGI();
            } else {
                new TeacherAuthGI();
            }
            dispose();
        });
        fileMenu.add(logoutItem);

        JMenuItem accountSettingsItem = new JMenuItem("Налаштування облікового запису");
        accountSettingsItem.setIcon(new ImageIcon("resources/account.png"));
        accountSettingsItem.addActionListener(e -> {
            if (teacherManager.getCurrentUser() == null) {
                new AccountSettingsGI<>(this, studentManager);
            } else {
                new AccountSettingsGI<>(this, teacherManager);
            }
        });
        fileMenu.add(accountSettingsItem);

        fileMenu.addSeparator();

        JMenuItem closeItem = new JMenuItem("Закрити");
        closeItem.addActionListener(e -> System.exit(0));
        fileMenu.add(closeItem);
    }

    private void prepareHelpMenu() {
        helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("Про програму");
        aboutItem.addActionListener(e -> JOptionPane.showConfirmDialog(null,
                "<html></html>", "Про програму",
                JOptionPane.DEFAULT_OPTION));
        helpMenu.add(aboutItem);
    }
}
