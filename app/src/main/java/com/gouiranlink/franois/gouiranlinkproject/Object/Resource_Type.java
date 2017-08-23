package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Resource_Type {

    private int id;
    private String name;

    public Resource_Type(int id,String name){
        this.setId(id);
        this.setName(name);
    }

    public Resource_Type(){}

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
