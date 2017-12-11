package com.basitple.radioapp;

//what to delete here?
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;


public class MainActivity extends FragmentActivity
    implements HomePage.OnFragmentInteractionListener
                , MusicPageFragment.OnFragmentInteractionListener
                , NewsPageFragment.OnFragmentInteractionListener
{
    private List<Song> songs;
    Fragment homePage = null;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        if(homePage == null){
            homePage = new HomePage();
        }
        setContentView(R.layout.activity_main);
        fragmentTransaction.add(R.id.mainFragContainer, homePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initMusicPlayer(){
        new BuildMusicStream(new AsyncResponse<List<Song>>() {
            @Override
            public void processFinish(List<Song> output) {
                songs = output;
                for(Song song : songs) {
                    Log.i("Songs in main", song.getTitle());
                }
            }
        }).execute();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.d("FragmentInteraction", uri.toString());
    }
}