package userGI;

import panelsAndFrames.MainFrame;
import supporting.QuestionTableParameters;
import usersClasses.Teacher;
import usersClasses.TeacherController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeacherWorkspaceGI extends MainFrame {

    private JTable testTaskTable;
    private QuestionTableParameters testTaskTableParameters;

    public TeacherWorkspaceGI(Teacher teacher, TeacherController teacherController) {
        super("Робоче середовище", teacher, teacherController);

    }

    public void frameSetup() {
        fillContainer();
        fillTollsPanel();
        setTabbedItems("Список тестів", "Список студентів");
        addListenerToTabbedList(new SelectionListener());
        setMinimumSize(new Dimension(700, 400));
        setSize(new Dimension(924, 520));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fillContainer() {

    }

    public void fillTollsPanel() {

    }

    public void prepareQuestionsTable() {
        testTaskTableParameters = new QuestionTableParameters(null);
        testTaskTable = createTable(testTaskTableParameters);
        testTaskTable.getSelectionModel().addListSelectionListener(e -> {
//            removeButton.setEnabled(true);
//            editButton.setEnabled(true);
        });
        testTaskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
//                    editButton.doClick();
                }
            }
        });
    }

    public class SelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

        }
    }
}
