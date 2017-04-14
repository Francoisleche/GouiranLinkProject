package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

/**
 * Created by François on 20/03/2017.
 */

public class Item {
    private String title = null;
    private String description = null;

    Item() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}