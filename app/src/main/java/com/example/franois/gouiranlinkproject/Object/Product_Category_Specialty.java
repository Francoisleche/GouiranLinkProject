package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Product_Category_Specialty {

    private int id;
    private String label;
    private Product_Category_WithoutTree category;

    public Product_Category_Specialty(int id,String label,Product_Category_WithoutTree category){
        this.setId(id);
        this.setLabel(label);
        this.setCategory(category);
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

    public Product_Category_WithoutTree getCategory() {
        return category;
    }

    public void setCategory(Product_Category_WithoutTree category) {
        this.category = category;
    }
}
