package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by François on 13/06/2017.
 */

public class Reservation implements Serializable {
    public String id;
    String picture;
    String institute;
    List<String> type;
    ArrayList<String> products = new ArrayList<>();
    String date;
    String hour;
    String adress;
    String products_json;
    private Customer customer;
    Boolean boolean_commentaires_date_ok;

    public Reservation() {
        picture = "";
        institute = "";
        type = new ArrayList<String>();
        date = "";
        hour = "";
        adress = "";
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getProducts_json() {
        return products_json;
    }

    public void setProducts_json(String products_json) {
        this.products_json = products_json;
    }

    public Boolean getBoolean_commentaires_date_ok() {
        return boolean_commentaires_date_ok;
    }

    public void setBoolean_commentaires_date_ok(Boolean boolean_commentaires_date_ok) {
        this.boolean_commentaires_date_ok = boolean_commentaires_date_ok;
    }
}