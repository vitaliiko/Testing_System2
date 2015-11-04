package panelsAndFrames;

import usersClasses.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UserInfoPanel extends JPanel {

    private JComboBox<Object> rightsBox;
    private User user;

    public UserInfoPanel(User user) {
        this.user = user;
        initPanel();
    }

    private void initPanel() {
        setLayout(new GridLayout(6, 2));
        setVisible(false);
        setBorder(new EmptyBorder(10, 0, 10, 0));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        add(new JLabel("Name: "));
        add(new JLabel(user.getName()));

        add(new JLabel("Surname: "));
        add(new JLabel(user.getSurname()));

        add(new JLabel("Username: "));
        add(new JLabel(user.getUserName()));

        add(new JLabel("Telephone: "));
        add(new JLabel(user.getTelephoneNum()));

        add(new JLabel("E-mail address: "));
        add(new JLabel(user.getMailAddress()));
    }
}

