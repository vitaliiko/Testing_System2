package studentGI;

import javax.swing.*;
import java.awt.*;

public class PassingTheTestGI extends JWindow {

    public PassingTheTestGI() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        setVisible(true);
    }
}
