package panelsAndFrames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoxPanel extends JPanel {

    public BoxPanel(int axis) {
        checkAxis(axis);
        setLayout(new BoxLayout(this, axis));
    }

    public BoxPanel(JComponent... components) {
        for (JComponent component : components) {
            add(component);
        }
    }

    public BoxPanel(int axis, JComponent... components) {
        checkAxis(axis);
        setLayout(new BoxLayout(this, axis));
        for (JComponent component : components) {
            add(component);
        }
    }

    public BoxPanel(JComponent component, String constraint) {
        checkConstraint(constraint);
        setLayout(new BorderLayout());
        add(component, constraint);
    }

    public void add(JComponent... components) {
        for (JComponent component : components) {
            add(component);
        }
    }

    private void checkAxis(int axis) {
        try {
            if (axis != BoxLayout.Y_AXIS && axis != BoxLayout.X_AXIS &&
                    axis != BoxLayout.LINE_AXIS && axis != BoxLayout.PAGE_AXIS) {
                throw new IOException("Axis mast be equal 1, 2, 3 or 4");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkConstraint(String constraint) {
        try {
            if (!constraint.equals(BorderLayout.SOUTH) && !constraint.equals(BorderLayout.NORTH)
                    && !constraint.equals(BorderLayout.WEST) && !constraint.equals(BorderLayout.EAST)
                    && !constraint.equals(BorderLayout.CENTER)) {
                throw new IOException("Constraint mast be equal Center, North, East, South or West");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
