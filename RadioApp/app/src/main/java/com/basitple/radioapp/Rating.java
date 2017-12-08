package com.basitple.radioapp;

import com.google.gson.annotations.SerializedName;

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

    public long getId() {
        return id;
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
