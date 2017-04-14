package com.gouiranlink.franois.gouiranlinkproject.Object;

import java.io.Serializable;

/**
 * Created by François on 20/01/2017.
 */

public class Product implements Serializable{

    private int id;
    private String name;
    private Product_Category_WithoutTree category;
    private double price;
    private String currency;
    private String color;

    private String duration;
    private String description;
    private boolean enabled;
    private String created_at;
    private String updated_at;

    public Product(){

    }

    public Product(int id, String name, Product_Category_WithoutTree category, double price, String currency, String color, String duration,
                   String description, boolean enabled, String created_at, String updated_at){
        this.setId(id);
        this.setName(name);
        this.setCategory(category);
        this.setPrice(price);
        this.setCurrency(currency);
        this.setColor(color);
        this.setDuration(duration);
        this.setDescription(description);
        this.setEnabled(enabled);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);

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

    public Product_Category_WithoutTree getCategory() {
        return category;
    }

    public void setCategory(Product_Category_WithoutTree category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
