package com.basitple.radioapp;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildMusicStream extends AsyncTask<MainActivity, Void, Void> {
    private ArrayList<Song> songs;
    public AsyncResponse<ArrayList<Song>> delegate = null;
    public BuildMusicStream(AsyncResponse<ArrayList<Song>> delegate){
        this.delegate = delegate;
    }
    @Override
    protected Void doInBackground(MainActivity... Params){
        BuildMusicList();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void BuildMusicList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SCService scService = retrofit.create(SCService.class);
        Call<SongsList> call = scService.getSongs();
        try {
            songs = call.execute().body().songs;
            delegate.processFinish(songs);
        } catch (Throwable t){
            t.printStackTrace();
        }
    }
}
