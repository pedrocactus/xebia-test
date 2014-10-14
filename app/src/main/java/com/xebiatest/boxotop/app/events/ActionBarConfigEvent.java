package com.xebiatest.boxotop.app.events;

/**
 * Created by pierrecastex on 01/10/2014.
 */
public class ActionBarConfigEvent {

    private String title;
    private boolean enableHomeButton;

    public String getTitle() {
        return title;
    }

    public boolean isEnableHomeButton() {
        return enableHomeButton;
    }

    public ActionBarConfigEvent(String title, boolean enableHomeButton) {

        this.title = title;
        this.enableHomeButton = enableHomeButton;
    }
}
