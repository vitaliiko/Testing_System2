package supporting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static final int MAX_IMAGE_WIDTH = 500;
    public static final int MAX_IMAGE_HEIGHT = 400;

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    public static void checkImageSize(String imagePath) throws IOException {
        Image image = new ImageIcon(imagePath).getImage();

        if (image.getWidth(null) > MAX_IMAGE_WIDTH || image.getHeight(null) > MAX_IMAGE_HEIGHT) {
            throw new IOException("Зображення занадто велике");
        }
    }

    public static byte[] imageInByteArr(String imagePath) {
        File image;
        byte[] imageInByte = null;
        try {
            image = new File(imagePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bufferedImage = ImageIO.read(image);
            ImageIO.write(bufferedImage, ImageUtils.getExtension(image), baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні зображення",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return imageInByte;
    }

    public static Image imageFromByteArr(byte[] imageInByte) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageInByte));
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Виникла помилка при завантаженні зображення",
                    "Попередження", JOptionPane.DEFAULT_OPTION);
        }
        return null;
    }
}
