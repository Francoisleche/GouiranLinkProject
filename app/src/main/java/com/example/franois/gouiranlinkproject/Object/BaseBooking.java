package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class BaseBooking {

    private int id;
    private String created_at;
    private String updated_at;
    private String begin_date;
    private String end_date;

    private double price;
    private String currency;
    private boolean confirmed;
    private boolean cancelled;
    private boolean no_show;
    private String no_show_comment;
    private boolean first_booking;
    private String first_booking_comment;
    private String home_address;
    private String home_post_code;
    private String home_city;
    private String home_country;
    private String observation;
    private String phone;

    private Professional_Product[] products;
    private Resource resource;
    private BaseBooking parent;


    public BaseBooking(int id,String created_at,String updated_at,String begin_date,String end_date,double price,String currency,
                       boolean confirmed,boolean cancelled,boolean no_show,String no_show_comment,boolean first_booking,String first_booking_comment,
                       String home_address,String home_post_code,String home_city,String home_country,String observation,
                       String phone,Professional_Product[] products,Resource resource,BaseBooking parent){

        this.setId(id);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
        this.setBegin_date(begin_date);
        this.setEnd_date(end_date);
        this.setPrice(price);
        this.setCurrency(currency);
        this.setConfirmed(confirmed);
        this.setCancelled(cancelled);
        this.setNo_show(no_show);
        this.setNo_show_comment(no_show_comment);
        this.setFirst_booking(first_booking);
        this.setFirst_booking_comment(first_booking_comment);
        this.setHome_address(home_address);
        this.setHome_post_code(home_post_code);
        this.setHome_city(home_city);
        this.setHome_country(home_country);
        this.setObservation(observation);
        this.setPhone(phone);
        this.setProducts(products);
        this.setResource(resource);
        this.setParent(parent);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isNo_show() {
        return no_show;
    }

    public void setNo_show(boolean no_show) {
        this.no_show = no_show;
    }

    public String getNo_show_comment() {
        return no_show_comment;
    }

    public void setNo_show_comment(String no_show_comment) {
        this.no_show_comment = no_show_comment;
    }

    public boolean isFirst_booking() {
        return first_booking;
    }

    public void setFirst_booking(boolean first_booking) {
        this.first_booking = first_booking;
    }

    public String getFirst_booking_comment() {
        return first_booking_comment;
    }

    public void setFirst_booking_comment(String first_booking_comment) {
        this.first_booking_comment = first_booking_comment;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_post_code() {
        return home_post_code;
    }

    public void setHome_post_code(String home_post_code) {
        this.home_post_code = home_post_code;
    }

    public String getHome_city() {
        return home_city;
    }

    public void setHome_city(String home_city) {
        this.home_city = home_city;
    }

    public String getHome_country() {
        return home_country;
    }

    public void setHome_country(String home_country) {
        this.home_country = home_country;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Professional_Product[] getProducts() {
        return products;
    }

    public void setProducts(Professional_Product[] products) {
        this.products = products;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public BaseBooking getParent() {
        return parent;
    }

    public void setParent(BaseBooking parent) {
        this.parent = parent;
    }
}
