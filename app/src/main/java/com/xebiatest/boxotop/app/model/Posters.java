package com.xebiatest.boxotop.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pierrecastex on 22/09/2014.
 */
public class Posters implements Parcelable{

    private String thumbnail;
    private String profile;
    private String detailed;
    private String original;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Posters() {
    }

    public Posters(String thumbnail, String profile, String detailed, String original) {

        this.thumbnail = thumbnail;
        this.profile = profile;
        this.detailed = detailed;
        this.original = original;
    }
    public static final Parcelable.Creator<Posters> CREATOR
            = new Parcelable.Creator<Posters>() {
        public Posters createFromParcel(Parcel in) {
            return new Posters(in);
        }

        public Posters[] newArray(int size) {
            return new Posters[size];
        }
    };
    private Posters(Parcel in) {
        thumbnail = in.readString();
        profile = in.readString();
        detailed = in.readString();
        original = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbnail);
        parcel.writeString(profile);
        parcel.writeString(detailed);
        parcel.writeString(original);

    }
}
