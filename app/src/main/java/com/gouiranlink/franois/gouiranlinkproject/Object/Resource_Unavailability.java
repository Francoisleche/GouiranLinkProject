package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Resource_Unavailability extends GenericUnavailability{

    private int id;

    public Resource_Unavailability(String begin_date,String end_date,int id){
        super(begin_date,end_date);
        this.setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
