package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Professional_Book {

    private int id;
    private Image_N image;
    private String video;
    private boolean show_book;
    private boolean show_professional_customer;
    private String created_at;
    private String updated_at;


    public Professional_Book(int id,Image_N image,String video,boolean show_book,boolean show_professional_customer,String created_at,
                             String updated_at){
        this.setId(id);
        this.setImage(image);
        this.setVideo(video);
        this.setShow_book(show_book);
        this.setShow_professional_customer(show_professional_customer);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image_N getImage() {
        return image;
    }

    public void setImage(Image_N image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isShow_book() {
        return show_book;
    }

    public void setShow_book(boolean show_book) {
        this.show_book = show_book;
    }

    public boolean isShow_professional_customer() {
        return show_professional_customer;
    }

    public void setShow_professional_customer(boolean show_professional_customer) {
        this.show_professional_customer = show_professional_customer;
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
