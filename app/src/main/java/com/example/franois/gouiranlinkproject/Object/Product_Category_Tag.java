package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Product_Category_Tag {

    private int id;
    private String label;

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
