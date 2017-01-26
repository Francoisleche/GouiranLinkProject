package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Professional_Product {

    private int id;
    private String name;
    private double price;
    private String currency;

    private String duration;
    private String description;

    private String deleted_at;
    private Product product;

    private String created_at;
    private String updated_at;

    private Discount[] discount;

    public Professional_Product(int id, String name, double price, String currency, String duration, String description, String deleted_at,
                                Product product, String created_at, String updated_at, Discount[] discount){

        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setCurrency(currency);
        this.setDuration(duration);
        this.setDescription(description);
        this.setDeleted_at(deleted_at);
        this.setProduct(product);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
        this.setDiscount(discount);


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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Discount[] getDiscount() {
        return discount;
    }

    public void setDiscount(Discount[] discount) {
        this.discount = discount;
    }
}
