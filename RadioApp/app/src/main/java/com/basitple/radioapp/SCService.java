package com.basitple.radioapp;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SCService {

    @GET("/songs/")
    Call<SongsList> getSongs();

    @GET("/users/{id}")
    Call<UsersList> getUsers(@Path("id") String id);

    @GET("/songs/{id}/listen")
    Call<ResponseBody> getSongFile(@Path("id") long id);

    @POST("/users")
    @FormUrlEncoded
    Call<ResponseBody> registerUser(@Path("id") long userId,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("ratings")List<Rating> ratings);
}
