package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Professional_Customer {

    private int id;
    private String created_at;
    private int fidelity;
    private String gender;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String mobilephone;

    private String address;
    private String post_code;
    private String city;

    private String country;
    private double geoloc_latitude;
    private double geoloc_longitude;

    private String comment;
    private int distance;
    private String run_time;
    private Customer customer;

    private Resource favorite_resource;
    private String birthday_date;
    private boolean sms;
    private String customer_since_date;

    private Professional_Book[] photos;
    private boolean is_blocked;
    private boolean professional_visibility;


    public Professional_Customer(int id,String created_at,int fidelity,String gender,String name,String surname,String email,String phone,
                                 String mobilephone,String address,String post_code,String city,String country,double geoloc_latitude,
                                 double geoloc_longitude,String comment,int distance,String run_time,Customer customer,Resource favorite_resource,
                                 String birthday_date,boolean sms,String customer_since_date,Professional_Book[] photos,boolean is_blocked,
                                 boolean professional_visibility){
        this.setId(id);
        this.setCreated_at(created_at);
        this.setFidelity(fidelity);
        this.setGender(gender);
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setPhone(phone);
        this.setMobilephone(mobilephone);
        this.setAddress(address);
        this.setPost_code(post_code);
        this.setCity(city);
        this.setCountry(country);
        this.setGeoloc_latitude(geoloc_latitude);
        this.setGeoloc_longitude(geoloc_longitude);
        this.setComment(comment);
        this.setDistance(distance);
        this.setRun_time(run_time);
        this.setCustomer(customer);
        this.setFavorite_resource(favorite_resource);
        this.setBirthday_date(birthday_date);
        this.setSms(sms);
        this.setCustomer_since_date(customer_since_date);
        this.setPhotos(photos);
        this.setIs_blocked(is_blocked);
        this.setProfessional_visibility(professional_visibility);

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

    public int getFidelity() {
        return fidelity;
    }

    public void setFidelity(int fidelity) {
        this.fidelity = fidelity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getGeoloc_latitude() {
        return geoloc_latitude;
    }

    public void setGeoloc_latitude(double geoloc_latitude) {
        this.geoloc_latitude = geoloc_latitude;
    }

    public double getGeoloc_longitude() {
        return geoloc_longitude;
    }

    public void setGeoloc_longitude(double geoloc_longitude) {
        this.geoloc_longitude = geoloc_longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Resource getFavorite_resource() {
        return favorite_resource;
    }

    public void setFavorite_resource(Resource favorite_resource) {
        this.favorite_resource = favorite_resource;
    }

    public String getBirthday_date() {
        return birthday_date;
    }

    public void setBirthday_date(String birthday_date) {
        this.birthday_date = birthday_date;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public String getCustomer_since_date() {
        return customer_since_date;
    }

    public void setCustomer_since_date(String customer_since_date) {
        this.customer_since_date = customer_since_date;
    }

    public Professional_Book[] getPhotos() {
        return photos;
    }

    public void setPhotos(Professional_Book[] photos) {
        this.photos = photos;
    }

    public boolean is_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isProfessional_visibility() {
        return professional_visibility;
    }

    public void setProfessional_visibility(boolean professional_visibility) {
        this.professional_visibility = professional_visibility;
    }
}
