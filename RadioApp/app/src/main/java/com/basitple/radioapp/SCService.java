package com.basitple.radioapp;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SCService {

    @GET("/songs/")
    Call<SongsList> getSongs();

    @GET("/users/{id}")
    Call<UsersList> getUsers(@Path("id") String id);

    @GET("/songs/{id}/listen")
    Call<ResponseBody> getSongFile(@Path("id") long id);
}
