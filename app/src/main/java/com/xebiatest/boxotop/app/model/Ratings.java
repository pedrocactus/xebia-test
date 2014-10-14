package com.xebiatest.boxotop.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pierrecastex on 30/09/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratings implements Parcelable{

    @JsonProperty("critics_rating")
    private String criticsRating;
    @JsonProperty("critics_score")
    private int criticsScrore;
    @JsonProperty("audience_rating")
    private String audienceRating;
    @JsonProperty("audience_score")
    private int audienceScore;
    private int userScore;
    private String userRating;

    public Ratings() {
    }

    public Ratings(String criticsRating, int criticsScrore, String audienceRating, int audienceScore) {

        this.criticsRating = criticsRating;
        this.criticsScrore = criticsScrore;
        this.audienceRating = audienceRating;
        this.audienceScore = audienceScore;
    }
    public static final Parcelable.Creator<Ratings> CREATOR
            = new Parcelable.Creator<Ratings>() {
        public Ratings createFromParcel(Parcel in) {
            return new Ratings(in);
        }

        public Ratings[] newArray(int size) {
            return new Ratings[size];
        }
    };
    private Ratings(Parcel in) {
        criticsRating = in.readString();
        criticsScrore = in.readInt();
        audienceRating = in.readString();
        audienceScore = in.readInt();
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    public int getCriticsScrore() {
        return criticsScrore;
    }

    public void setCriticsScrore(int criticsScrore) {
        this.criticsScrore = criticsScrore;
    }

    public String getAudienceRating() {
        return audienceRating;
    }

    public void setAudienceRating(String audienceRating) {
        this.audienceRating = audienceRating;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
