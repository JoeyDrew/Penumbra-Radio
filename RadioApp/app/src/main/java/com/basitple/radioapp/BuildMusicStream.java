package com.basitple.radioapp;

import android.media.AsyncPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewDebug;

import java.io.Console;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildMusicStream extends AsyncTask<Void, Void, List<Song>> {
    static private List<Song> songs;

    public AsyncResponse<List<Song>> delegate = null;
    public BuildMusicStream(AsyncResponse<List<Song>> delegate){
        this.delegate = delegate;
    }
    @Override
    protected List<Song> doInBackground(Void... Params){
        BuildMusicList();
        return songs;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
    }

    private void BuildMusicList(){
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
                    SongsList songsOBJ = response.body();
                    songs = songsOBJ.songs;
                    delegate.processFinish(songsOBJ.songs);



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
