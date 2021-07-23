package sample;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class util {

    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static String removeExtension(String fileName) {
        String name = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            name = fileName.substring(0, i);
        }
        return name;
    }

    public static String getName(String filePath) {
        String temp[] = filePath.split(File.separator);
        String fileName = temp[temp.length - 1];
        System.out.println(fileName);
        return fileName;
    }

    public static void deleteImage(String filePath) {
        try {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("File has been delete");
            } else {
                System.out.println("Failed to delete file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean imageTypeCheck(String extension) {
        String[] extensions = {"jpg", "jpeg", "bmp", "png"};
        for (int i = 0; i < extensions.length; i ++) {
            if (extension.equals(extensions[i])) {
                return true;
            }
        }
        return false;
    }

    public static ImageList getImages(String path) {

        System.out.println(path);
        ArrayList<Image> images = new ArrayList<>();
        ArrayList<Image> previews = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        File file = new File(path);
        File[] tmpList = file.listFiles();

        for (int i = 0; i < tmpList.length; i ++) {
            if (tmpList[i].isFile()) {
                String fileName = getName(tmpList[i].toString());
                if (imageTypeCheck(getExtension(fileName))) {
                    System.out.println(tmpList[i].toString());
                    names.add(fileName);
                    paths.add(tmpList[i].toString());
                    images.add(new Image(tmpList[i].toURI().toString(), 800, 600, false, true));
                    previews.add(new Image(tmpList[i].toURI().toString(), 80, 60, false, true));
                }
            }
        }
        return new ImageList(images.toArray(new Image[0]), previews.toArray(new Image[0]), names.toArray(new String[0]), paths.toArray(new String[0]));
    }

    public static BufferedImage resize(BufferedImage image, int newW, int newH) {
        java.awt.Image tmp = image.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }

    public static void jointPic(String path1, String path2, String newPath) throws IOException {
        BufferedImage[] images = new BufferedImage[2];
        File file1 = new File(path1);
        File file2 = new File(path2);
        int h = 0;
        images[0] = ImageIO.read(file1);
        int width = images[0].getWidth();
        int height = images[0].getHeight();
        images[1] = resize(ImageIO.read(file2), width, height);
        int[] imgArray = new int[width * height];
        h = images[0].getHeight() + images[1].getHeight();
        System.out.println(images[0].getWidth() + ", " + images[0].getHeight());
        System.out.println(images[1].getWidth() + ", " + images[1].getHeight());
        BufferedImage newImage = new BufferedImage(width, h, BufferedImage.TYPE_INT_RGB);
        int _height = 0;
        for (int i = 0; i < images.length; i ++) {
            images[i].getRGB(0, 0, width, height, imgArray, 0, width);
            newImage.setRGB(0, _height, width, height, imgArray, 0, width);
            _height += images[i].getHeight();
        }
        System.out.println("jointPic : " + newPath);
        File outFile = new File(newPath);
        ImageIO.write(newImage, "jpg", outFile);
    }
}
