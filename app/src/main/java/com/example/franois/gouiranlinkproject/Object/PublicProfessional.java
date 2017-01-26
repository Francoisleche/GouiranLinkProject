package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class PublicProfessional {

    private String shop_name;
    private Product_Category_Tag[] tags;
    private String shop_description;
    private Product_Category_Specialty specialty;
    private String address;
    private String post_code;
    private String city;
    private String country;
    private Professional_Type type;
    private String shop_phone;
    private String shop_email;
    private String website_link;
    private String facebook_link;
    private String instagram_link;
    private String pinterest_link;

    private Product_Category_WithoutTree[] product_categories;
    private Professional_Article[] articles;
    private Brand[] brands;
    private Award[] awards;
    private Alert[] alerts;
    private Professional_Unavailability[] unavailabilities;
    private Professional_Schedule[] schedule;

    public PublicProfessional(String shop_name, Product_Category_Tag[] tags, String shop_description, Product_Category_Specialty specialty,
                              String address, String post_code, String city, String country, Professional_Type type, String shop_phone,
                              String shop_email, String website_link, String facebook_link, String instagram_link, String pinterest_link,
                              Product_Category_WithoutTree[] product_categories, Professional_Article[] articles, Brand[] brands,
                              Award[] awards, Alert[] alerts, Professional_Unavailability[] unavailabilities, Professional_Schedule[] schedule){
        this.setShop_name(shop_name);
        this.setTags(tags);
        this.setShop_description(shop_description);
        this.setSpecialty(specialty);
        this.setAddress(address);
        this.setPost_code(post_code);
        this.setCity(city);
        this.setCountry(country);
        this.setType(type);
        this.setShop_phone(shop_phone);
        this.setShop_email(shop_email);
        this.setWebsite_link(website_link);
        this.setFacebook_link(facebook_link);
        this.setInstagram_link(instagram_link);
        this.setPinterest_link(pinterest_link);

        this.setProduct_categories(product_categories);
        this.setArticles(articles);
        this.setBrands(brands);
        this.setAwards(awards);
        this.setAlerts(alerts);
        this.setUnavailabilities(unavailabilities);
        this.setSchedule(schedule);
    }


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Product_Category_Tag[] getTags() {
        return tags;
    }

    public void setTags(Product_Category_Tag[] tags) {
        this.tags = tags;
    }

    public String getShop_description() {
        return shop_description;
    }

    public void setShop_description(String shop_description) {
        this.shop_description = shop_description;
    }

    public Product_Category_Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Product_Category_Specialty specialty) {
        this.specialty = specialty;
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

    public Professional_Type getType() {
        return type;
    }

    public void setType(Professional_Type type) {
        this.type = type;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_email() {
        return shop_email;
    }

    public void setShop_email(String shop_email) {
        this.shop_email = shop_email;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    public String getPinterest_link() {
        return pinterest_link;
    }

    public void setPinterest_link(String pinterest_link) {
        this.pinterest_link = pinterest_link;
    }

    public Product_Category_WithoutTree[] getProduct_categories() {
        return product_categories;
    }

    public void setProduct_categories(Product_Category_WithoutTree[] product_categories) {
        this.product_categories = product_categories;
    }

    public Professional_Article[] getArticles() {
        return articles;
    }

    public void setArticles(Professional_Article[] articles) {
        this.articles = articles;
    }

    public Brand[] getBrands() {
        return brands;
    }

    public void setBrands(Brand[] brands) {
        this.brands = brands;
    }

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    public Alert[] getAlerts() {
        return alerts;
    }

    public void setAlerts(Alert[] alerts) {
        this.alerts = alerts;
    }

    public Professional_Unavailability[] getUnavailabilities() {
        return unavailabilities;
    }

    public void setUnavailabilities(Professional_Unavailability[] unavailabilities) {
        this.unavailabilities = unavailabilities;
    }

    public Professional_Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Professional_Schedule[] schedule) {
        this.schedule = schedule;
    }
}
