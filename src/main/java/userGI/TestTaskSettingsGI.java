package userGI;

import panelsAndFrames.BoxPanel;
import panelsAndFrames.LabelComponentPanel;
import testingClasses.TestTask;
import usersClasses.TeacherController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 15;

    private TestTask testTask;
    private TeacherController teacherController;

    private JPanel testSettingsPanel;
    private JPanel authorsPanel;
    private JTextField nameField;
    private JTextField disciplineNameField;
    private JComboBox<Object> attributeBox;
    private JSpinner ansversLimit;
    private JSpinner questionsLimit;
    private JSpinner timeLimit;
    private JList<Object> authorsList;
    private JList<Object> groupsList;
    private JList<Object> questionGroupsList;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JButton selectionAuthorsButton;

    public TestTaskSettingsGI(JFrame owner, TestTask testTask, TeacherController teacherController) {
        super(owner, "Налаштування тесту");
        this.testTask = testTask;
        this.teacherController = teacherController;

        prepareTestSettingsPanel();
        getContentPane().add(testSettingsPanel, BorderLayout.EAST);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(400, 430));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void prepareTestSettingsPanel() {
        testSettingsPanel = new BoxPanel(BoxLayout.Y_AXIS);

        nameField = new JTextField(testTask.getTaskName(), COLUMNS_COUNT);
        testSettingsPanel.add(new LabelComponentPanel("Назва тесту: ", nameField));

        disciplineNameField = new JTextField(testTask.getDisciplineName(), COLUMNS_COUNT);
        testSettingsPanel.add(new LabelComponentPanel("Назва дисципліни: ", disciplineNameField));

        testSettingsPanel.add(new LabelComponentPanel("Створив: ", new JLabel(testTask.getCreatorName())));

        String[] attributeItems = {"Загальнодоступний", "З обмеженим доступом", "Лише перегляд"};
        attributeBox = new JComboBox<>(attributeItems);
        attributeBox.setSelectedIndex(testTask.getAttribute());
        testSettingsPanel.add(new LabelComponentPanel("Режим доступу: ", attributeBox), new JSeparator());

        authorsList = createSelectionList(testTask.getAuthorsList().toArray());
        testSettingsPanel.add(new LabelComponentPanel("Автори: ", new JScrollPane(authorsList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED)
        ));


        prepareSelectionAuthorsButton();
        testSettingsPanel.add(selectionAuthorsButton);
    }

    public JList<Object> createSelectionList(Object[] dataList) {
        JList<Object> selectionList = new JList<>(dataList);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setDragEnabled(false);
        selectionList.setVisibleRowCount(5);
        return selectionList;
    }

    public void prepareSelectionAuthorsButton() {
        selectionAuthorsButton = new JButton();
        selectionAuthorsButton.addActionListener(e -> {
            SelectionTableGI selectionTableGI = new SelectionTableGI("Вибір групи авторів",
                    new ArrayList<>(teacherController.getTeachersNamesList()), testTask.getAuthorsList());
            selectionTableGI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    authorsList.setListData(selectionTableGI.getMarkedNamesList().toArray());
                }
            });
        });
    }
}
