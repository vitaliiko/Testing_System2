package userGI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ImageBrowserGI extends JFrame {

    private Image image;

    public ImageBrowserGI(Image image) {
        super("Перегляд");
        this.image = image;
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        getContentPane().add(imagePanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 500));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
