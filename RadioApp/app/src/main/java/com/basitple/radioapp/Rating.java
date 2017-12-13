package com.basitple.radioapp;

import android.os.Environment;
import android.support.v4.util.TimeUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;


public class Rating {
    @SerializedName("id")
    private long id;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("song_id")
    private long songId;

    @SerializedName("rating")
    private long rating;

    @SerializedName("created_at")
    private Date dateCreated;

    @SerializedName("updated_at")
    private Date lastUpdated;
    public Rating (Long id){
        this.id = -1;
        this.userId = id;
        this.songId = -1;
        this.rating = -1;
        this.dateCreated = Calendar.getInstance().getTime();
    }
    public long getId() {
        return id;
    }

    public void setUserId(long id){
        userId = id;
    }

    public long getUserId() {
        return userId;
    }

    public long getSongId() {
        return songId;
    }

    public long getRating() {
        return rating;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}
