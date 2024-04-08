/**
 * This class represents a Card object with attributes such as id, state, location on the field, image.
 * It provides methods for setting and retrieving the information.
 */

package Memo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Card {
    private final int id;
    private int state;
    private int location;
    private ImageView image;

    private String soundFile;

    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public int getState() {
        return state;
    }

    public int getID() {
        return id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Card(String path, int id)  throws IOException{
        image = createImageView(path);
        if (image == null) {
            throw new IOException();
        }
        this.id = id;
        this.location = 0;
        this.state = 0;
    }

    public Card(int id, ImageView image) {
        this.id = id;
        this.state = 0;
        this.location = 0;
        this.image = image;
    }

    /**
     * The method creates an imageView from the file (the path parameter shows the path to the jpg file
     */
    private static ImageView createImageView(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            ImageView imageView = new ImageView(new Image(inputStream));
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
