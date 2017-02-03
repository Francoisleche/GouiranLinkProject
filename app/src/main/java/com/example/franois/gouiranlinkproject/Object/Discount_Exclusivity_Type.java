package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 03/02/2017.
 */

public class Discount_Exclusivity_Type {

    private int id;
    private String name;

    public Discount_Exclusivity_Type(int id,String name){

        this.setId(id);
        this.setName(name);

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
}
