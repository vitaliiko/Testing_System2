package supporting;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = ImageUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(ImageUtils.tiff) ||
                    extension.equals(ImageUtils.tif) ||
                    extension.equals(ImageUtils.gif) ||
                    extension.equals(ImageUtils.jpeg) ||
                    extension.equals(ImageUtils.jpg) ||
                    extension.equals(ImageUtils.png)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }
}