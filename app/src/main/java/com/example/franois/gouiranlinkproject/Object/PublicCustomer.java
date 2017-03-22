package com.example.franois.gouiranlinkproject.Object;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by François on 20/01/2017.
 */

public class PublicCustomer implements Serializable {

    protected int id;
    protected String name;
    protected String surname;
    protected Image_N image;

    public PublicCustomer(int id,String name,String surname,Image_N image){
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

    public Image_N getImage() {
        return image;
    }

    public void setImage(Image_N image) {
        this.image = image;
    }
}
