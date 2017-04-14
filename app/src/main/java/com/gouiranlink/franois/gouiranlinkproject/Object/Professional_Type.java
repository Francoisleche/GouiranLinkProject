package com.gouiranlink.franois.gouiranlinkproject.Object;

/**
 * Created by François on 20/01/2017.
 */

public class Professional_Type {

    private int id;
    private String name;
    private boolean has_distance_limit;
    private boolean show_on_googlemap;

    public Professional_Type(int id,String name,boolean has_distance_limit,boolean show_on_googlemap){
        this.setId(id);
        this.setName(name);
        this.setHas_distance_limit(has_distance_limit);
        this.setShow_on_googlemap(show_on_googlemap);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHas_distance_limit() {
        return has_distance_limit;
    }

    public void setHas_distance_limit(boolean has_distance_limit) {
        this.has_distance_limit = has_distance_limit;
    }

    public boolean isShow_on_googlemap() {
        return show_on_googlemap;
    }

    public void setShow_on_googlemap(boolean show_on_googlemap) {
        this.show_on_googlemap = show_on_googlemap;
    }
}
