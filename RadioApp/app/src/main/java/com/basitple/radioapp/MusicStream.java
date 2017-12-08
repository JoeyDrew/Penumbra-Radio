package com.basitple.radioapp;

import android.util.Log;
import android.view.ViewDebug;

import java.io.Console;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicStream {
    private List<Song> songs;

    public MusicStream(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SCService scService = retrofit.create(SCService.class);

        scService.getSongs().enqueue(new Callback<SongsList>() {
            @Override
            public void onResponse(Call<SongsList> call, Response<SongsList> response) {
                if(response.isSuccessful()){
                    Log.d("Response", response.body().songs.get(1).getTitle());
                    SongsList tmp = response.body();
                    songs = tmp.songs;

                } else {
                    Log.e("Response Error", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<SongsList> call, Throwable t) {
                Log.e("Response Failed", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
