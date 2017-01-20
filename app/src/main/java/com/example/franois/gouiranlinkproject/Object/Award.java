package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Award {

    private int id;
    private String name;
    private Image_N image;
    private String description;

    public Award(int id,String name,Image_N image,String description){
        this.setId(id);
        this.setName(name);
        this.setImage(image);
        this.setDescription(description);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image_N getImage() {
        return image;
    }

    public void setImage(Image_N image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
