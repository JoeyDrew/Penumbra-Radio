package com.basitple.radioapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



public class User {
    @SerializedName("id")
    private long id;

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("ratings")
    private ArrayList<Rating> ratings;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

}
