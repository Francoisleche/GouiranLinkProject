package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class GenericUnavailability {

    private String begin_date;
    private String end_date;

    public GenericUnavailability(String begin_date,String end_date){
        this.setBegin_date(begin_date);
        this.setEnd_date(end_date);
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
}
