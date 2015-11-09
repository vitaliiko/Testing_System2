package teacherGI;

import components.BoxPanel;
import components.ImagePanel;
import supporting.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class ImageBrowserGI extends JFrame {

    public ImageBrowserGI(Image image) {
        super("Перегляд");

        ImagePanel imagePanel = new ImagePanel(image);

        getContentPane().add(new BoxPanel(imagePanel), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(ImageUtils.MAX_IMAGE_WIDTH, ImageUtils.MAX_IMAGE_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
