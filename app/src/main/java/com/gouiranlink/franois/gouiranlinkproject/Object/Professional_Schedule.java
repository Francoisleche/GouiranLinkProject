package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Professional_Schedule extends GenericSchedule{

    private int id;

    public Professional_Schedule(){

    }

    public Professional_Schedule(int weekday,String begin_time,String end_time,int id){
        super(weekday,begin_time,end_time);
        this.setId(id);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
