package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by François on 22/08/2017.
 */

public class Object {
    public String title; // use getters and setters instead
    public List<Object> children; // same as above

    public Object() {
        children = new ArrayList<Object>();
    }
}