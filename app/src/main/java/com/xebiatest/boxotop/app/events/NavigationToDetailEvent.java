package com.xebiatest.boxotop.app.events;

/**
 * Created by pierrecastex on 27/09/2014.
 */
public class NavigationToDetailEvent {
    private long id;

    public long getId() {
        return id;
    }

    public NavigationToDetailEvent(long id) {
        this.id = id;
    }
}
