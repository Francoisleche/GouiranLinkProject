package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Brand {

    private int id;
    private String name;
    private Image_N image;

    public Brand(int id,String name,Image_N image){
        this.setId(id);
        this.setName(name);
        this.setImage(image);
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
}
