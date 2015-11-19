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
        return fileName.toLowerCase().endsWith(ImageUtils.tiff)
                || fileName.toLowerCase().endsWith(ImageUtils.tif)
                || fileName.toLowerCase().endsWith(ImageUtils.gif)
                || fileName.toLowerCase().endsWith(ImageUtils.jpeg)
                || fileName.toLowerCase().endsWith(ImageUtils.jpg)
                || fileName.toLowerCase().endsWith(ImageUtils.png);
    }

    @Override
    public String getDescription() {
        return null;
    }
}