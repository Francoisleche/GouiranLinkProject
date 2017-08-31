package com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by François on 24/08/2017.
 */

public class Object2 {
    public String title; // use getters and setters instead
    public List<com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.Object2> children; // same as above

    public Object2() {
        children = new ArrayList<com.gouiranlink.franois.gouiranlinkproject.ToolsClasses4.Object2>();
    }
}