package com.xebiatest.boxotop.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by pierrecastex on 02/10/2014.
 *
 * Review class is corresponding to a REST API call that is not used already in this application
 * It is therefore a preventive creation for future developments.
 *
 * Currently used and stored in sharedpreferences only for the user personal review.
 *
 *
 */
public class Review {


    @JsonProperty("critic")
    private String criticName;
    private Date date;
    @JsonProperty("original_score")
    private float rating;
    @JsonProperty("quote")
    private String review;

    public String getCriticName() {
        return criticName;
    }

    public void setCriticName(String criticName) {
        this.criticName = criticName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Review() {

    }

    public Review(String critic, Date date, float rating, String review) {
        this.criticName = critic;
        this.date = date;
        this.rating = rating;
        this.review = review;
    }
}
