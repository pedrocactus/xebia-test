package com.xebiatest.boxotop.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by pierrecastex on 21/09/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor implements Parcelable{

    private String name;
    private List<String> characters;

    public Actor(String name, List<String> characters) {
        this.name = name;
        this.characters = characters;
    }
    public static final Parcelable.Creator<Actor> CREATOR
            = new Parcelable.Creator<Actor>() {
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };
    private Actor(Parcel in) {
        name = in.readString();
        characters = in.readArrayList(String.class.getClassLoader());
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public Actor() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
