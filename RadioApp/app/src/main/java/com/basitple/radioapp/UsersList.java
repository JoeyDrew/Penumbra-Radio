package com.basitple.radioapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BadAp on 12/8/2017.
 */

public class UsersList {
    @SerializedName("users")
    public List<User> users;
}
