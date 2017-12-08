package com.basitple.radioapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class User {
    @SerializedName("id")
    private long id;

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("ratings")
    private List<Rating> ratings;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Rating> getRatings() {
        return ratings;
    }
}
