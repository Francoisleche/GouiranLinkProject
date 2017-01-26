package com.example.franois.gouiranlinkproject.Object;

import java.util.ArrayList;

/**
 * Created by François on 20/01/2017.
 */

public class Image_N {

    private String url;
    //1er element du tableau : width
    //2eme element du tableau : height
    //3eme element du tableau : url
    private ArrayList<String[]> thumbnails;


    public Image_N(String url,ArrayList<String[]> thumbnails){
        this.setUrl(url);
        this.setThumbnails(thumbnails);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String[]> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ArrayList<String[]> thumbnails) {
        this.thumbnails = thumbnails;
    }
}
