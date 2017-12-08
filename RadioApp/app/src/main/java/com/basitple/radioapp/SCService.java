package com.basitple.radioapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SCService {

    @GET("/songs/{id}")
    Call<List<Song>> getSongs(@Path("id") String id);

    @GET("/users/{id}")
    Call<List<User>> getUsers(@Path("id") String id);
}
