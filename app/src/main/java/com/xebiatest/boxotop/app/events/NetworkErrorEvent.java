package com.xebiatest.boxotop.app.events;

/**
 * Created by pierrecastex on 28/09/2014.
 */
public class NetworkErrorEvent {
    public static int CACHE=0;
    public static int OTHER=1;
    private int errorType;

    public int getErrorType() {
        return errorType;
    }

    public NetworkErrorEvent(int errorType) {
        this.errorType = errorType;

    }
}
