package com.gouiranlink.franois.gouiranlinkproject.Object;

import java.io.Serializable;

/**
 * Created by François on 20/01/2017.
 */

public class Customer_Profession implements Serializable {

    private int id;
    private String name;

    public Customer_Profession(){

    }

    public Customer_Profession(int id,String name){
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
