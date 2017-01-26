package com.example.franois.gouiranlinkproject.Object;

import android.media.Image;

/**
 * Created by François on 20/01/2017.
 */

public class PublicCustomer {

    protected int id;
    protected String name;
    protected String surname;
    protected Image image;

    public PublicCustomer(int id,String name,String surname,Image image){
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
