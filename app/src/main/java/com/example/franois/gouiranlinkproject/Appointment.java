package com.example.franois.gouiranlinkproject;

import java.util.Date;

/**
 * Created by pyram_m on 02/12/16.
 */

public class Appointment {
    String providerName;
    String serviceName;
    Date date;
    String image;

    public Appointment(String providerName, String serviceName, Date date, String image) {
        this.providerName = providerName;
        this.serviceName = serviceName;
        this.date = date;
        this.image = image;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
