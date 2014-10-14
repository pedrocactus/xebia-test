package com.xebiatest.boxotop.app.events;

/**
 * Created by pierrecastex on 28/09/2014.
 */
public class SaveCommentEvent {

    private float ratingStars;
    private  String comment;

    public float getRatingStars() {
        return ratingStars;
    }

    public String getComment() {
        return comment;
    }

    public SaveCommentEvent(float ratingStars, String comment) {

        this.ratingStars = ratingStars;
        this.comment = comment;
    }
}
