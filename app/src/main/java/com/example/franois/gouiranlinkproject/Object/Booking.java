package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Booking extends BaseBooking{

    private Professional_Customer professional_customer;
    private Comment comment;

    public Booking(int id,String created_at,String updated_at,String begin_date,String end_date,double price,String currency,
                   boolean confirmed,boolean cancelled,boolean no_show,String no_show_comment,boolean first_booking,String first_booking_comment,
                   String home_address,String home_post_code,String home_city,String home_country,String observation,
                   String phone,Professional_Product[] products,Resource resource,BaseBooking parent,Professional_Customer professional_customer
                    ,Comment comment){
        super(id,created_at,updated_at,begin_date,end_date,price,currency,confirmed,cancelled,no_show,no_show_comment,first_booking,
                first_booking_comment,home_address,home_post_code,home_city,home_country,observation,phone,products,resource,parent);
        this.setProfessional_customer(professional_customer);
        this.setComment(comment);

    }

    public Professional_Customer getProfessional_customer() {
        return professional_customer;
    }

    public void setProfessional_customer(Professional_Customer professional_customer) {
        this.professional_customer = professional_customer;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
