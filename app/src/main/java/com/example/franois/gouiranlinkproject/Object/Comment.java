package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Comment {

    private int id;
    private int grade;
    private String text;
    private boolean published;
    private String professional_response;
    private PublicCustomer customer;
    private PublicProfessional professional;
    private String created_at;
    private String updated_at;

    public Comment(){

    }

    public Comment(int id, int grade, String text, boolean published, String professional_response, PublicCustomer customer,
                   PublicProfessional professional, String created_at, String updated_at){
        this.setId(id);
        this.setGrade(grade);
        this.setText(text);
        this.setPublished(published);
        this.setProfessional_response(professional_response);
        this.setCustomer(customer);
        this.setProfessional(professional);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getProfessional_response() {
        return professional_response;
    }

    public void setProfessional_response(String professional_response) {
        this.professional_response = professional_response;
    }

    public PublicCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(PublicCustomer customer) {
        this.customer = customer;
    }

    public PublicProfessional getProfessional() {
        return professional;
    }

    public void setProfessional(PublicProfessional professional) {
        this.professional = professional;
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
