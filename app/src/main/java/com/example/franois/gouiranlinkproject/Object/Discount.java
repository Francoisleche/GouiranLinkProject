package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Discount {

    private int id;
    private String begin_date;
    private String end_date;
    private String description;
    private boolean is_percentage;

    private double amount;
    private String currency;
    private String created_at;
    private String updated_at;


    public Discount(int id,String begin_date,String end_date,String description,boolean is_percentage,double amount,String currency,
                    String created_at,String updated_at){
        this.setId(id);
        this.setBegin_date(begin_date);
        this.setEnd_date(end_date);
        this.setDescription(description);
        this.setIs_percentage(is_percentage);

        this.setAmount(amount);
        this.setCurrency(currency);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean is_percentage() {
        return is_percentage;
    }

    public void setIs_percentage(boolean is_percentage) {
        this.is_percentage = is_percentage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
