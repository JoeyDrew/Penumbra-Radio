package com.basitple.radioapp;

import java.io.File;
import com.google.gson.annotations.SerializedName;




public class Song {
    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("artist")
    private String artist;
    @SerializedName("publisher")
    private String publisher;

    @SerializedName("year")
    private long year;

    @SerializedName("audioname")
    private String audioname;

    @SerializedName("artname")
    private String artname;

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public File getSong(){
        return null;
    }


}
