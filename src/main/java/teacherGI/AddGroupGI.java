package teacherGI;

import components.FrameUtils;
import usersClasses.StudentsGroup;

import javax.swing.*;
import java.awt.*;

public class AddGroupGI extends JDialog {

    private StudentsGroup studentsGroup;

    public AddGroupGI(Frame owner) {
        super(owner, "Нова група студентів");

        FrameUtils.setLookAndFill();

        setupDialog();
    }

    private void setupDialog() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(415, 500));
        setIconImage(new ImageIcon("resources/add_group.png").getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
