package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 24/03/2017.
 */

public class CustomerBooking extends BaseBooking{

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

    private Professional professional;
    private Comment comment;

    public CustomerBooking(){

    }

    public CustomerBooking(int id, String created_at,String updated_at,String begin_date,String end_date,double price,String currency,
                           boolean confirmed,boolean cancelled,boolean no_show,String no_show_comment,boolean first_booking,String first_booking_comment,
                           String home_address,String home_post_code,String home_city,String home_country,String observation,String phone,
                           Professional_Product[] products,Resource resource, BaseBooking parent,Professional professional,Comment comment){

        super(id,created_at,updated_at,begin_date,end_date,price,currency,confirmed,cancelled,no_show,no_show_comment,first_booking,first_booking_comment,
                home_address,home_post_code,home_city,home_country,observation,phone,products,resource,parent);
        this.setProfessional(professional);
        this.setComment(comment);
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getCreated_at() {
        return created_at;
    }

    @Override
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String getBegin_date() {
        return begin_date;
    }

    @Override
    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    @Override
    public String getEnd_date() {
        return end_date;
    }

    @Override
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isNo_show() {
        return no_show;
    }

    @Override
    public void setNo_show(boolean no_show) {
        this.no_show = no_show;
    }

    @Override
    public String getNo_show_comment() {
        return no_show_comment;
    }

    @Override
    public void setNo_show_comment(String no_show_comment) {
        this.no_show_comment = no_show_comment;
    }

    @Override
    public boolean isFirst_booking() {
        return first_booking;
    }

    @Override
    public void setFirst_booking(boolean first_booking) {
        this.first_booking = first_booking;
    }

    @Override
    public String getFirst_booking_comment() {
        return first_booking_comment;
    }

    @Override
    public void setFirst_booking_comment(String first_booking_comment) {
        this.first_booking_comment = first_booking_comment;
    }

    @Override
    public String getHome_address() {
        return home_address;
    }

    @Override
    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    @Override
    public String getHome_post_code() {
        return home_post_code;
    }

    @Override
    public void setHome_post_code(String home_post_code) {
        this.home_post_code = home_post_code;
    }

    @Override
    public String getHome_city() {
        return home_city;
    }

    @Override
    public void setHome_city(String home_city) {
        this.home_city = home_city;
    }

    @Override
    public String getHome_country() {
        return home_country;
    }

    @Override
    public void setHome_country(String home_country) {
        this.home_country = home_country;
    }

    @Override
    public String getObservation() {
        return observation;
    }

    @Override
    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Professional_Product[] getProducts() {
        return products;
    }

    @Override
    public void setProducts(Professional_Product[] products) {
        this.products = products;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public BaseBooking getParent() {
        return parent;
    }

    @Override
    public void setParent(BaseBooking parent) {
        this.parent = parent;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
