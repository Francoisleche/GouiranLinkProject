package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Product_Category_WithoutTree {

    private int id;
    private String name;
    private Product_Category_Tag[] tags;
    private String created_at;
    private String updated_at;

    public Product_Category_WithoutTree(int id,String name,Product_Category_Tag[] tags,String created_at,String updated_at){
        this.setId(id);
        this.setName(name);
        this.setTags(tags);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
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

    public Product_Category_Tag[] getTags() {
        return tags;
    }

    public void setTags(Product_Category_Tag[] tags) {
        this.tags = tags;
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
}
