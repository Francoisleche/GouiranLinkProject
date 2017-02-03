package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */


public class Professional extends PublicProfessional{

    private int id;
    private Professional_Acquisition professional_acquisition;
    private double geoloc_latitude;
    private double geoloc_longitude;
    private int max_intervention_distance;
    private Image_N logo_image;

    private boolean automatic_booking_confirmation;
    private boolean customer_can_choose_resource_booking;
    private String created_at;
    private String updated_at;
    private Professional_Subscription_Type professional_subscription_type;
    private String notification_preferences_sms;
    private boolean sms_happybirthday_enabled;
    private String sms_happybirthday_sender;

    private String sms_happybirthday_content;
    private boolean sms_fidelity_enabled;
    private String sms_fidelity_sender;
    private String sms_fidelity_content;
    private boolean sms_remember_booking_enabled;
    private Discount_Exclusivity discount_exclusivity;
    private Resource_Type preference_resource_type;
    private String sponsoring_key;


    public Professional(String shop_name, Product_Category_Tag[] tags, String shop_description, Product_Category_Specialty specialty,
                        String address, String post_code, String city, String country, Professional_Type type, String shop_phone,
                        String shop_email, String website_link, String facebook_link, String instagram_link, String pinterest_link,
                        Product_Category_WithoutTree[] product_categories, Professional_Article[] articles, Brand[] brands,
                        Award[] awards, Alert[] alerts, Professional_Unavailability[] unavailabilities, Professional_Schedule[] schedule,


                        int id,Professional_Acquisition professional_acquisition,double geoloc_latitude,double geoloc_longitude,
                        int max_intervention_distance,Image_N logo_image,boolean automatic_booking_confirmation,boolean customer_can_choose_resource_booking,
                        String created_at,String updated_at,Professional_Subscription_Type professional_subscription_type,
                        String notification_preferences_sms,boolean sms_happybirthday_enabled,String sms_happybirthday_sender,
                        String sms_happybirthday_content,boolean sms_fidelity_enabled,String sms_fidelity_sender,String sms_fidelity_content,
                        boolean sms_remember_booking_enabled,Discount_Exclusivity discount_exclusivity,Resource_Type preference_resource_type,
                        String sponsoring_key){

        super(shop_name,tags,shop_description,specialty,address,post_code,city,country,type,shop_phone,shop_email,website_link,facebook_link,instagram_link,pinterest_link,
                product_categories,articles,brands,awards,alerts,unavailabilities,schedule);

        this.setId(id);
        this.setProfessional_acquisition(professional_acquisition);
        this.setGeoloc_latitude(geoloc_latitude);
        this.setGeoloc_longitude(geoloc_longitude);
        this.setMax_intervention_distance(max_intervention_distance);
        this.setLogo_image(logo_image);
        this.setAutomatic_booking_confirmation(automatic_booking_confirmation);
        this.setCustomer_can_choose_resource_booking(customer_can_choose_resource_booking);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
        this.setProfessional_subscription_type(professional_subscription_type);
        this.setNotification_preferences_sms(notification_preferences_sms);
        this.setSms_happybirthday_enabled(sms_happybirthday_enabled);
        this.setSms_happybirthday_sender(sms_happybirthday_sender);
        this.setSms_happybirthday_content(sms_happybirthday_content);
        this.setSms_fidelity_enabled(sms_fidelity_enabled);
        this.setSms_fidelity_sender(sms_fidelity_sender);
        this.setSms_fidelity_content(sms_fidelity_content);
        this.setSms_remember_booking_enabled(sms_remember_booking_enabled);
        this.setDiscount_exclusivity(discount_exclusivity);
        this.setPreference_resource_type(preference_resource_type);
        this.setSponsoring_key(sponsoring_key);



    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professional_Acquisition getProfessional_acquisition() {
        return professional_acquisition;
    }

    public void setProfessional_acquisition(Professional_Acquisition professional_acquisition) {
        this.professional_acquisition = professional_acquisition;
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

    public int getMax_intervention_distance() {
        return max_intervention_distance;
    }

    public void setMax_intervention_distance(int max_intervention_distance) {
        this.max_intervention_distance = max_intervention_distance;
    }

    public Image_N getLogo_image() {
        return logo_image;
    }

    public void setLogo_image(Image_N logo_image) {
        this.logo_image = logo_image;
    }

    public boolean isAutomatic_booking_confirmation() {
        return automatic_booking_confirmation;
    }

    public void setAutomatic_booking_confirmation(boolean automatic_booking_confirmation) {
        this.automatic_booking_confirmation = automatic_booking_confirmation;
    }

    public boolean isCustomer_can_choose_resource_booking() {
        return customer_can_choose_resource_booking;
    }

    public void setCustomer_can_choose_resource_booking(boolean customer_can_choose_resource_booking) {
        this.customer_can_choose_resource_booking = customer_can_choose_resource_booking;
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

    public Professional_Subscription_Type getProfessional_subscription_type() {
        return professional_subscription_type;
    }

    public void setProfessional_subscription_type(Professional_Subscription_Type professional_subscription_type) {
        this.professional_subscription_type = professional_subscription_type;
    }

    public String getNotification_preferences_sms() {
        return notification_preferences_sms;
    }

    public void setNotification_preferences_sms(String notification_preferences_sms) {
        this.notification_preferences_sms = notification_preferences_sms;
    }

    public boolean isSms_happybirthday_enabled() {
        return sms_happybirthday_enabled;
    }

    public void setSms_happybirthday_enabled(boolean sms_happybirthday_enabled) {
        this.sms_happybirthday_enabled = sms_happybirthday_enabled;
    }

    public String getSms_happybirthday_sender() {
        return sms_happybirthday_sender;
    }

    public void setSms_happybirthday_sender(String sms_happybirthday_sender) {
        this.sms_happybirthday_sender = sms_happybirthday_sender;
    }

    public String getSms_happybirthday_content() {
        return sms_happybirthday_content;
    }

    public void setSms_happybirthday_content(String sms_happybirthday_content) {
        this.sms_happybirthday_content = sms_happybirthday_content;
    }

    public boolean isSms_fidelity_enabled() {
        return sms_fidelity_enabled;
    }

    public void setSms_fidelity_enabled(boolean sms_fidelity_enabled) {
        this.sms_fidelity_enabled = sms_fidelity_enabled;
    }

    public String getSms_fidelity_sender() {
        return sms_fidelity_sender;
    }

    public void setSms_fidelity_sender(String sms_fidelity_sender) {
        this.sms_fidelity_sender = sms_fidelity_sender;
    }

    public String getSms_fidelity_content() {
        return sms_fidelity_content;
    }

    public void setSms_fidelity_content(String sms_fidelity_content) {
        this.sms_fidelity_content = sms_fidelity_content;
    }

    public boolean isSms_remember_booking_enabled() {
        return sms_remember_booking_enabled;
    }

    public void setSms_remember_booking_enabled(boolean sms_remember_booking_enabled) {
        this.sms_remember_booking_enabled = sms_remember_booking_enabled;
    }

    public Discount_Exclusivity getDiscount_exclusivity() {
        return discount_exclusivity;
    }

    public void setDiscount_exclusivity(Discount_Exclusivity discount_exclusivity) {
        this.discount_exclusivity = discount_exclusivity;
    }

    public Resource_Type getPreference_resource_type() {
        return preference_resource_type;
    }

    public void setPreference_resource_type(Resource_Type preference_resource_type) {
        this.preference_resource_type = preference_resource_type;
    }

    public String getSponsoring_key() {
        return sponsoring_key;
    }

    public void setSponsoring_key(String sponsoring_key) {
        this.sponsoring_key = sponsoring_key;
    }
}
