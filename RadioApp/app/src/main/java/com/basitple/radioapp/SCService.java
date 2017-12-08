package com.basitple.radioapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SCService {

    @GET("/songs/")
    Call<SongsList> getSongs();

    @GET("/songs/{id}")
    Call<Song> get1Song(@Path("id") int id);

    @GET("/users/{id}")
    Call<List<User>> getUsers(@Path("id") String id);
}
