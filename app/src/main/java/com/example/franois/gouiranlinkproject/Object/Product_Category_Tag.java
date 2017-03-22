package com.example.franois.gouiranlinkproject.Object;

import java.io.Serializable;

/**
 * Created by François on 20/01/2017.
 */

public class Product_Category_Tag implements Serializable{

    private int id;
    private String label;

    public Product_Category_Tag(){
    }

    public Product_Category_Tag(int id,String label){
        this.setId(id);
        this.setLabel(label);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
