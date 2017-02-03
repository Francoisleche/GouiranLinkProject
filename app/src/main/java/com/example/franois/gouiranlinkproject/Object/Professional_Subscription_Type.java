package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 03/02/2017.
 */

public class Professional_Subscription_Type {

    private int id;
    private String name;
    private boolean is_default;
    private boolean has_category_tags;
    private boolean has_shop_description;

    private boolean has_shop_image;
    private boolean has_shop_ressources;
    private boolean has_shop_links;
    private boolean has_shop_speciality;
    private boolean has_book;
    private boolean has_products;
    private boolean has_unavalabilities;

    private boolean has_discount;
    private boolean has_articles;
    private boolean has_customers;
    private boolean has_comment;
    private boolean has_booking;
    private boolean has_sms;
    private boolean has_welcome_message;
    private boolean has_statistic;
    private boolean can_delete_account;
    private boolean needs_moderation;



    public Professional_Subscription_Type(int id,String name,boolean is_default,boolean has_category_tags,boolean has_shop_description,
                                          boolean has_shop_image,boolean has_shop_ressources,boolean has_shop_links,boolean has_shop_speciality,
                                          boolean has_book,boolean has_products,boolean has_unavalabilities,boolean has_discount,boolean has_articles,
                                          boolean has_customers,boolean has_comment,boolean has_booking,boolean has_sms,boolean has_welcome_message,
                                          boolean has_statistic,boolean can_delete_account,boolean needs_moderation){

        this.setId(id);
        this.setName(name);
        this.setIs_default(is_default);
        this.setHas_category_tags(has_category_tags);
        this.setHas_shop_description(has_shop_description);
        this.setHas_shop_image(has_shop_image);
        this.setHas_shop_ressources(has_shop_ressources);
        this.setHas_shop_links(has_shop_links);
        this.setHas_shop_speciality(has_shop_speciality);
        this.setHas_book(has_book);
        this.setHas_products(has_products);
        this.setHas_unavalabilities(has_unavalabilities);
        this.setHas_discount(has_discount);
        this.setHas_articles(has_articles);
        this.setHas_customers(has_customers);
        this.setHas_comment(has_comment);
        this.setHas_booking(has_booking);
        this.setHas_sms(has_sms);
        this.setHas_welcome_message(has_welcome_message);
        this.setHas_statistic(has_statistic);
        this.setCan_delete_account(can_delete_account);
        this.setNeeds_moderation(needs_moderation);

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

    public boolean is_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public boolean isHas_category_tags() {
        return has_category_tags;
    }

    public void setHas_category_tags(boolean has_category_tags) {
        this.has_category_tags = has_category_tags;
    }

    public boolean isHas_shop_description() {
        return has_shop_description;
    }

    public void setHas_shop_description(boolean has_shop_description) {
        this.has_shop_description = has_shop_description;
    }

    public boolean isHas_shop_image() {
        return has_shop_image;
    }

    public void setHas_shop_image(boolean has_shop_image) {
        this.has_shop_image = has_shop_image;
    }

    public boolean isHas_shop_ressources() {
        return has_shop_ressources;
    }

    public void setHas_shop_ressources(boolean has_shop_ressources) {
        this.has_shop_ressources = has_shop_ressources;
    }

    public boolean isHas_shop_links() {
        return has_shop_links;
    }

    public void setHas_shop_links(boolean has_shop_links) {
        this.has_shop_links = has_shop_links;
    }

    public boolean isHas_shop_speciality() {
        return has_shop_speciality;
    }

    public void setHas_shop_speciality(boolean has_shop_speciality) {
        this.has_shop_speciality = has_shop_speciality;
    }

    public boolean isHas_book() {
        return has_book;
    }

    public void setHas_book(boolean has_book) {
        this.has_book = has_book;
    }

    public boolean isHas_products() {
        return has_products;
    }

    public void setHas_products(boolean has_products) {
        this.has_products = has_products;
    }

    public boolean isHas_unavalabilities() {
        return has_unavalabilities;
    }

    public void setHas_unavalabilities(boolean has_unavalabilities) {
        this.has_unavalabilities = has_unavalabilities;
    }

    public boolean isHas_discount() {
        return has_discount;
    }

    public void setHas_discount(boolean has_discount) {
        this.has_discount = has_discount;
    }

    public boolean isHas_articles() {
        return has_articles;
    }

    public void setHas_articles(boolean has_articles) {
        this.has_articles = has_articles;
    }

    public boolean isHas_customers() {
        return has_customers;
    }

    public void setHas_customers(boolean has_customers) {
        this.has_customers = has_customers;
    }

    public boolean isHas_comment() {
        return has_comment;
    }

    public void setHas_comment(boolean has_comment) {
        this.has_comment = has_comment;
    }

    public boolean isHas_booking() {
        return has_booking;
    }

    public void setHas_booking(boolean has_booking) {
        this.has_booking = has_booking;
    }

    public boolean isHas_sms() {
        return has_sms;
    }

    public void setHas_sms(boolean has_sms) {
        this.has_sms = has_sms;
    }

    public boolean isHas_welcome_message() {
        return has_welcome_message;
    }

    public void setHas_welcome_message(boolean has_welcome_message) {
        this.has_welcome_message = has_welcome_message;
    }

    public boolean isHas_statistic() {
        return has_statistic;
    }

    public void setHas_statistic(boolean has_statistic) {
        this.has_statistic = has_statistic;
    }

    public boolean isCan_delete_account() {
        return can_delete_account;
    }

    public void setCan_delete_account(boolean can_delete_account) {
        this.can_delete_account = can_delete_account;
    }

    public boolean isNeeds_moderation() {
        return needs_moderation;
    }

    public void setNeeds_moderation(boolean needs_moderation) {
        this.needs_moderation = needs_moderation;
    }
}
