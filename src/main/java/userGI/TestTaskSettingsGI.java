package userGI;

import testingClasses.TestTask;
import usersClasses.Teacher;
import usersClasses.TeacherController;

import javax.swing.*;
import java.awt.*;

public class TestTaskSettingsGI extends JDialog {

    private static final int COLUMNS_COUNT = 26;

    private TestTask testTask;
    private JFrame frame;

    private JPanel fieldsPanel;
    private JTextField nameField;
    private JTextField disciplineName;
    private JComboBox<Object> attributeBox;
    private JSpinner ansversLimit;
    private JSpinner questionsLimit;
    private JSpinner timeLimit;
    private JList<String> authorsList;
    private JList<String> groupsList;
    private JList<String> questionGroupsList;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton removeButton;

    public TestTaskSettingsGI(Dialog owner, TestTask testTask, JFrame frame) {
        super(owner, "Налаштування тесту");
        this.testTask = testTask;
        this.frame = frame;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(335, 430));
        setIconImage(new ImageIcon("resources/settings.png").getImage());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
