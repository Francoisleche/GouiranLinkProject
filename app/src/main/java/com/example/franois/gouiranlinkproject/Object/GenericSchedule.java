package com.example.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class GenericSchedule {

    private int weekday;
    private String begin_time;
    private String end_time;

    public GenericSchedule(int weekday,String begin_time,String end_time){
        this.setWeekday(weekday);
        this.setBegin_time(begin_time);
        this.setEnd_time(end_time);
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
