package sample;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller class to handle button actions
 */
public class Controller {

    @FXML
    ImageView imageView;
    @FXML
    ListView<String> listView;
    @FXML
    ImageView selectImage1;
    @FXML
    ImageView selectImage2;
    @FXML
    TextField textField;
    private static ImageList imageList;
    private static int id;
    private static boolean select_mode = false;
    private static int top = -1, bottom = -1;
    private static Image select_image_1;
    private static Image select_image_2;
    private static String directory = "/Users/xingyuyan/resources/";

    /** Initialize array of images */
    public static void init() {
        id = 0;
        select_image_1 = new Image("selectImage1.jpg", 80, 60, false, true);
        select_image_2 = new Image("selectImage2.jpg", 80, 60, false, true);
    }

    public static void reset() {
        select_mode = false;
        top = bottom = -1;
    }

    @FXML
    /** Button to load image list */
    private void handleLoad(ActionEvent event) {

        directory = textField.getText();
        imageList = util.getImages(directory);
        listView.setItems(FXCollections.observableArrayList(imageList.getNames()));
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            private String name;
            {
                setOnMouseClicked(e -> listView.getSelectionModel().select(name));
            }
            @Override
            public void updateItem(String name, boolean empty) {
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    this.name = name;
                    for (int i = 0; i < imageList.getNames().length; i ++) {
                        if (name.equals(imageList.getNames()[i])) {
                            imageView.setImage(imageList.getPreviews()[i]);
                        }
                    }
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedIndices());
                id = listView.getSelectionModel().getSelectedIndices().get(0);
                imageView.setImage(imageList.getImages()[id]);
                imageView.setRotate(0.0);
                imageView.setScaleX(1.0);
                imageView.setScaleY(1.0);
                if (select_mode) {
                    if (top < 0) {
                        selectImage1.setImage(imageList.getImages()[id]);
                        top = id;
                    } else {
                        if (bottom < 0) {
                            selectImage2.setImage(imageList.getImages()[id]);
                            bottom = id;
                        }
                    }
                }
            }
        });
        imageView.setImage(imageList.getImages()[0]);
        imageView.setRotate(0.0);
        imageView.setScaleX(1.0);
        imageView.setScaleY(1.0);
        selectImage1.setImage(select_image_1);
        selectImage2.setImage(select_image_2);
        // reset();
    }

    @FXML
    /** Button to show prev image */
    private void handlePrevImage(ActionEvent event) {

        if(id == 0) {
            imageView.setImage(imageList.getImages()[imageList.getImages().length - 1]);
            id = imageList.getImages().length - 1;
        }
        else {
            imageView.setImage(imageList.getImages()[id - 1]);
            id = id - 1;
        }

    }

    @FXML
    /** Button to show next image */
    private void handleNextImage(ActionEvent event) {

        if(id == imageList.getImages().length - 1) {
            imageView.setImage(imageList.getImages()[0]);
            id = 0;
        }
        else {
            imageView.setImage(imageList.getImages()[id + 1]);
            id = id + 1;
        }

    }

    @FXML
    /** Button to select images */
    private void handleSelect(ActionEvent event) {

        select_mode = true;
        selectImage1.setImage(select_image_1);
        selectImage2.setImage(select_image_2);
        top = bottom = -1;

    }

    @FXML
    /** Button to stitch */
    private void handleStitch(ActionEvent event) throws IOException {

        if (select_mode == true) {
            if (top > -1 && bottom > -1) {
                String newPath = directory + util.removeExtension(imageList.getNames()[top])
                        + util.removeExtension(imageList.getNames()[bottom]) + "." + util.getExtension(imageList.getNames()[top]);
                util.jointPic(imageList.getPaths()[top], imageList.getPaths()[bottom], newPath);
            }
        }

        handleLoad(event);
    }

    @FXML
    /** Button to stitch */
    private void handleDelete(ActionEvent event) {
        util.deleteImage(imageList.getPaths()[id]);
        handleLoad(event);
    }

    @FXML
    /** Button to rotate image to the right*/
    private void handleRotateRight(ActionEvent event) {

        System.out.println("rotate right, rotation = " + imageView.getRotate());
        imageView.setRotate(imageView.getRotate() + 90);
    }

    @FXML
    /** Button to rotate image to the left*/
    private void handleRotateLeft(ActionEvent event) {

        System.out.println("rotate left, rotation = " + imageView.getRotate());
        imageView.setRotate(imageView.getRotate() - 90);
    }

    @FXML
    /** Button to scale up image */
    private void handleScaleUp(ActionEvent event) {

        System.out.println("scale up, size = " + imageView.getScaleX());
        imageView.setScaleX(imageView.getScaleX() / 0.75);
        imageView.setScaleY(imageView.getScaleY() / 0.75);
    }

    @FXML
    /** Button to scale down image */
    private void handleScaleDown(ActionEvent event) {

        System.out.println("scale down, size = " + imageView.getScaleX());
        imageView.setScaleX(imageView.getScaleX() * 0.75);
        imageView.setScaleY(imageView.getScaleY() * 0.75);
    }

}
