package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Resource_Schedule extends GenericSchedule {

    private int id;

    public Resource_Schedule(int weekday,String begin_time,String end_time,int id){
        super(weekday,begin_time,end_time);
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
