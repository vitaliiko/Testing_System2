package supporting;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String fileName = f.getName();
        return fileName.endsWith(ImageUtils.tiff) ||
                fileName.endsWith(ImageUtils.tif) ||
                fileName.endsWith(ImageUtils.gif) ||
                fileName.endsWith(ImageUtils.jpeg) ||
                fileName.endsWith(ImageUtils.jpg) ||
                fileName.endsWith(ImageUtils.png);
    }

    @Override
    public String getDescription() {
        return null;
    }
}