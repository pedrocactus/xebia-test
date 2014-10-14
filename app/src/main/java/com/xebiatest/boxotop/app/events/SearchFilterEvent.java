package com.xebiatest.boxotop.app.events;

/**
 * Created by pierrecastex on 27/09/2014.
 */
public class SearchFilterEvent {

    public String getAction() {
        return action;
    }

    public String getText() {
        return text;
    }

    public static final String CLEAR = "clear";
    public static final String FILTER = "filter";
    private String action;
    private String text;

    public SearchFilterEvent(String action, String text) {
        this.action = action;
        this.text = text;
    }
}
