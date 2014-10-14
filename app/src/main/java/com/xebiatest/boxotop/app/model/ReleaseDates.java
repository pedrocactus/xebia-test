package com.xebiatest.boxotop.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by pierrecastex on 30/09/2014.
 */
public class ReleaseDates implements Parcelable{

    private Date theater;
    private Date dvd;

    public ReleaseDates() {
    }

    public ReleaseDates(Date dvd, Date theater) {
        this.dvd = dvd;
        this.theater = theater;
    }
    public static final Parcelable.Creator<ReleaseDates> CREATOR
            = new Parcelable.Creator<ReleaseDates>() {
        public ReleaseDates createFromParcel(Parcel in) {
            return new ReleaseDates(in);
        }

        public ReleaseDates[] newArray(int size) {
            return new ReleaseDates[size];
        }
    };
    private ReleaseDates(Parcel in) {
        dvd = (Date) in.readSerializable();
        theater= (Date) in.readSerializable();
    }

    public Date getTheater() {
        return theater;
    }

    public void setTheater(Date theater) {
        this.theater = theater;
    }

    public Date getDvd() {
        return dvd;
    }

    public void setDvd(Date dvd) {
        this.dvd = dvd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(dvd);
        parcel.writeSerializable(theater);
    }
}
