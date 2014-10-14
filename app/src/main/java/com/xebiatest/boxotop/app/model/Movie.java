package com.xebiatest.boxotop.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrecastex on 18/09/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Parcelable{

    private long id;
    private String title;
    private int year;
    private String synopsis;
    @JsonProperty("abridged_cast")
    private List<Actor> actors;
    @JsonProperty("critics_consensus")
    private String critics;
    private Posters posters;
    @JsonProperty("release_dates")
    private ReleaseDates releaseDates;
    private Ratings ratings;
    private List<Movie> similarMovies;


    public Posters getPosters() {
        return posters;
    }

    public void setPosters(Posters posters) {
        this.posters = posters;
    }



    public Movie() {
    }
    private Movie(Parcel in) {
        id = in.readLong();

        title = in.readString();

        year = in.readInt();

        synopsis = in.readString();

        actors = new ArrayList<Actor>();
        in.readTypedList(actors,Actor.CREATOR);

        critics = in.readString();

        posters = in.readParcelable(Posters.class.getClassLoader());

        releaseDates = in.readParcelable(ReleaseDates.class.getClassLoader());

        similarMovies = new ArrayList<Movie>();
        in.readTypedList(similarMovies,Movie.CREATOR);

    }
    public Movie(long id, String title, int year, String synopsis, List<Actor> actors, String critics, Posters posters, ReleaseDates releaseDates, Ratings ratings,List<Movie> similarMovies) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.synopsis = synopsis;
        this.actors = actors;
        this.critics = critics;
        this.posters = posters;
        this.releaseDates = releaseDates;
        this.ratings = ratings;
        this.similarMovies = similarMovies;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };



    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(List<Movie> similarMovies) {
        this.similarMovies = similarMovies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getCritics() {
        return critics;
    }

    public void setCritics(String critics) {
        this.critics = critics;
    }

    public ReleaseDates getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(ReleaseDates releaseDates) {
        this.releaseDates = releaseDates;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    @Override
    public int describeContents() {
        return 0;
    }



}
