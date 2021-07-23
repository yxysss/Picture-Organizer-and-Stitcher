package sample;

import javafx.scene.image.Image;

public class ImageList {

    private Image[] images;
    private Image[] previews;
    private String[] names;
    private String[] paths;

    public ImageList(Image[] images, Image[] previews, String[] names, String[] paths) {
        this.images = images;
        this.previews = previews;
        this.names = names;
        this.paths = paths;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public void setPreviews(Image[] previews) {
        this.previews = previews;
    }

    public void  setNames(String[] names) {
        this.names = names;
    }

    public void  setPaths(String[] paths) {
        this.paths = paths;
    }

    public Image[] getImages() {
        return images;
    }

    public Image[] getPreviews() {
        return previews;
    }

    public String[] getNames() {
        return names;
    }

    public String[] getPaths() {
        return paths;
    }

}
