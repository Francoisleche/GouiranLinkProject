package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Alert {

    private int id;
    private String name;
    private String text;
    private String link;
    private String begin_date;
    private String end_date;
    private Professional[] professionals;
    private String created_at;
    private String updated_at;

    public Alert(int id, String name, String text, String link, String begin_date, String end_date, Professional[] professionals, String created_at, String updated_at){
        this.setId(id);
        this.setName(name);
        this.setText(text);
        this.setLink(link);
        this.setBegin_date(begin_date);
        this.setEnd_date(end_date);
        this.setProfessionals(professionals);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Professional[] getProfessionals() {
        return professionals;
    }

    public void setProfessionals(Professional[] professionals) {
        this.professionals = professionals;
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
