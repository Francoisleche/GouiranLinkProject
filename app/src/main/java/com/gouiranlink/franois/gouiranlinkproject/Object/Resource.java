package com.gouiranlink.franois.gouiranlinkproject.Object;

import java.io.Serializable;

/**
 * Created by François on 20/01/2017.
 */

public class Resource implements Serializable {

    private int id;
    private Resource_Type type;
    private String name;
    private String surname;
    private Image_N image;

    private String color;
    private int digit;
    private boolean is_visible_front;
    private boolean is_manager;

    private Professional_Product[] products;
    private Resource_Unavailability[] unavailabilities;
    private Resource_Schedule[] schedule;

    public Resource(){

    }


    public Resource(int id, Resource_Type type, String name, String surname, Image_N image, String color, int digit, boolean is_visible_front,
                    boolean is_manager, Professional_Product[] products, Resource_Unavailability[] unavailabilities, Resource_Schedule[] schedule){

        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setSurname(surname);
        this.setImage(image);
        this.setColor(color);
        this.setDigit(digit);
        this.setIs_visible_front(is_visible_front);
        this.setIs_manager(is_manager);
        this.setProducts(products);
        this.setUnavailabilities(unavailabilities);
        this.setSchedule(schedule);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resource_Type getType() {
        return type;
    }

    public void setType(Resource_Type type) {
        this.type = type;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public boolean is_visible_front() {
        return is_visible_front;
    }

    public void setIs_visible_front(boolean is_visible_front) {
        this.is_visible_front = is_visible_front;
    }

    public boolean is_manager() {
        return is_manager;
    }

    public void setIs_manager(boolean is_manager) {
        this.is_manager = is_manager;
    }

    public Professional_Product[] getProducts() {
        return products;
    }

    public void setProducts(Professional_Product[] products) {
        this.products = products;
    }

    public Resource_Unavailability[] getUnavailabilities() {
        return unavailabilities;
    }

    public void setUnavailabilities(Resource_Unavailability[] unavailabilities) {
        this.unavailabilities = unavailabilities;
    }

    public Resource_Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Resource_Schedule[] schedule) {
        this.schedule = schedule;
    }
}
