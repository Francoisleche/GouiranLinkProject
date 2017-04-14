package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 03/02/2017.
 */

public class Discount_Exclusivity {

    private int id;
    private boolean is_active;
    private String description;
    private String created_at;
    private String updated_at;

    private Discount show_discount;
    private Discount_Exclusivity_Type exclusivity_type;

    public Discount_Exclusivity(int id,boolean is_active,String description,String created_at,String updated_at,Discount show_discount,
                                Discount_Exclusivity_Type exclusivity_type){

        this.setId(id);
        this.setIs_active(is_active);
        this.setDescription(description);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
        this.setShow_discount(show_discount);
        this.setExclusivity_type(exclusivity_type);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Discount getShow_discount() {
        return show_discount;
    }

    public void setShow_discount(Discount show_discount) {
        this.show_discount = show_discount;
    }

    public Discount_Exclusivity_Type getExclusivity_type() {
        return exclusivity_type;
    }

    public void setExclusivity_type(Discount_Exclusivity_Type exclusivity_type) {
        this.exclusivity_type = exclusivity_type;
    }
}
