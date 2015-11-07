package components;

import supporting.TableParameters;
import testingClasses.TestTaskManager;
import userGI.AccountSettingsGI;
import userGI.AuthenticationGI;
import usersClasses.Student;
import usersClasses.StudentManager;
import usersClasses.StudentsGroup;
import usersClasses.TeacherManager;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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
        studentManager = new StudentManager(initStudents());
        mainFrameSetup();
    }

    public Set<StudentsGroup> initStudents() {
        ArrayList<StudentsGroup> studentsGroupsList = new ArrayList<>();

        studentsGroupsList.add(new StudentsGroup("CGC-1466", "", ""));
        studentsGroupsList.add(new StudentsGroup("CGC-1566", "", ""));
        studentsGroupsList.add(new StudentsGroup("CGC-1366", "", ""));
        studentsGroupsList.add(new StudentsGroup("CG-126", "", ""));
        studentsGroupsList.add(new StudentsGroup("RV-125", "", ""));

        new Student("Іванов", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Іваненко", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Петренко", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Петров", "Іван", "Іванович", studentsGroupsList.get(0));
        new Student("Іванов", "Петро", "Іванович", studentsGroupsList.get(1));
        new Student("Іванов", "Іван", "Петрович", studentsGroupsList.get(1));
        new Student("Іванов", "Федір", "Петрович", studentsGroupsList.get(1));

        return new TreeSet<>(studentsGroupsList);
    }

    public TeacherManager getTeacherManager() {
        return teacherManager;
    }

    public TestTaskManager getTestTaskManager() {
        return testTaskManager;
    }

    public abstract void frameSetup();

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
        //tabbedList.setSelectedIndex(0);
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
            new AuthenticationGI();
            dispose();
        });
        fileMenu.add(logoutItem);

        JMenuItem accountSettingsItem = new JMenuItem("Налаштування облікового запису");
        accountSettingsItem.setIcon(new ImageIcon("resources/account.png"));
        accountSettingsItem.addActionListener(e -> new AccountSettingsGI(this, teacherManager));
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
