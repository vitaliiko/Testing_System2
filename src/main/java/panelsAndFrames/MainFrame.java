package panelsAndFrames;

import supporting.QuestionTableParameters;
import userGI.AccountSettingsGI;
import usersClasses.StudentController;
import usersClasses.Teacher;
import usersClasses.TeacherController;

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

    protected Teacher teacher;
    protected TeacherController teacherController;
    protected StudentController studentController;

    public MainFrame(String title, Teacher teacher, TeacherController teacherController,
                     StudentController studentController) throws HeadlessException {
        super(title);
        this.teacher = teacher;
        this.teacherController = teacherController;
        this.studentController = studentController;
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
        prepareMenuBar();
        setJMenuBar(menuBar);
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

    public JTable createTable(QuestionTableParameters parameters) {
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
        logoutItem.addActionListener(e -> dispose());
        fileMenu.add(logoutItem);

        JMenuItem accountSettingsItem = new JMenuItem("Налаштування облікового запису");
        accountSettingsItem.setIcon(new ImageIcon("resources/account.png"));
        accountSettingsItem.addActionListener(e -> new AccountSettingsGI(this, teacher, teacherController));
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
