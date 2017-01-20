package com.example.franois.gouiranlinkproject.Object;

import android.media.Image;

/**
 * Created by François on 20/01/2017.
 */

public class Customer extends PublicCustomer{



    private String created_at;
    private String updated_at;
    private boolean has_subscribed;
    private boolean share_with_professional;
    private boolean blocked;
    //F or M
    private String gender;
    private String phone;
    private String mobilephone;
    private String birthday_date;

    private String address;
    private String post_code;
    private String city;
    private String country;

    private double geoloc_latitude;
    private double geoloc_longitude;

    private boolean sms;
    private boolean newsletter;

    private Customer_Profession profession;
    private String profession_other;
    private String language;
    private Image image_customer;

    private Product_Category_WithoutTree[] product_categories;

    private String email;



    public Customer(int id, String name, String surname, Image image, String created_at, String updated_at, boolean has_subscribed, boolean share_with_professional, boolean blocked,
                    String gender, String phone, String mobilephone, String birthday_date, String address, String post_code, String city,
                    String country, double geoloc_latitude, double geoloc_longitude, boolean sms, boolean newsletter, Customer_Profession profession,
                    String profession_other, String language, Image image_customer, Product_Category_WithoutTree[] product_categories, String email){
        super(id,name,surname,image);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
        this.setHas_subscribed(has_subscribed);
        this.setShare_with_professional(share_with_professional);
        this.setBlocked(blocked);
        this.setGender(gender);
        this.setPhone(phone);
        this.setMobilephone(mobilephone);
        this.setBirthday_date(birthday_date);
        this.setAddress(address);
        this.setPost_code(post_code);
        this.setCity(city);
        this.setCountry(country);
        this.setGeoloc_latitude(geoloc_latitude);
        this.setGeoloc_longitude(geoloc_longitude);
        this.setSms(sms);
        this.setNewsletter(newsletter);
        this.setProfession(profession);
        this.setProfession_other(profession_other);
        this.setLanguage(language);
        this.setImage(image_customer);
        this.setProduct_categories(product_categories);
        this.setEmail(email);



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

    public boolean isHas_subscribed() {
        return has_subscribed;
    }

    public void setHas_subscribed(boolean has_subscribed) {
        this.has_subscribed = has_subscribed;
    }

    public boolean isShare_with_professional() {
        return share_with_professional;
    }

    public void setShare_with_professional(boolean share_with_professional) {
        this.share_with_professional = share_with_professional;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getBirthday_date() {
        return birthday_date;
    }

    public void setBirthday_date(String birthday_date) {
        this.birthday_date = birthday_date;
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

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public Customer_Profession getProfession() {
        return profession;
    }

    public void setProfession(Customer_Profession profession) {
        this.profession = profession;
    }

    public String getProfession_other() {
        return profession_other;
    }

    public void setProfession_other(String profession_other) {
        this.profession_other = profession_other;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Image getImage() {
        return image_customer;
    }

    public void setImage(Image image) {
        this.image_customer = image;
    }

    public Product_Category_WithoutTree[] getProduct_categories() {
        return product_categories;
    }

    public void setProduct_categories(Product_Category_WithoutTree[] product_categories) {
        this.product_categories = product_categories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
