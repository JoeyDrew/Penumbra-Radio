package com.basitple.radioapp;

//what to delete here?
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends FragmentActivity
    implements HomePage.OnFragmentInteractionListener
                , MusicPageFragment.OnFragmentInteractionListener
                , NewsPageFragment.OnFragmentInteractionListener
{
    private List<Song> songs;
    SlidingUpPanelLayout slidingLayout;
    Fragment homePage = null;
    final static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    final static int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    private ServiceConnection serviceConnection;
    private MusicService player;
    boolean serviceBound = false;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initPermissions();
        songs = initMusicPlayer();
        playAudio(songs.get(0).getSong().getAbsolutePath());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        if(homePage == null){
            homePage = new HomePage();
        }
        setContentView(R.layout.activity_main);
        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.SLIDING_LAYOUT);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        fragmentTransaction.add(R.id.mainFragContainer, homePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private List<Song> initMusicPlayer(){
        try {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
                    player = binder.getService();
                    serviceBound = true;
                    Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    serviceBound = false;
                }
            };
            return new BuildMusicStream(new AsyncResponse<List<Song>>() {
                @Override
                public void processFinish(List<Song> output) {
                    songs = output;
                    if (songs == null) {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    } else {
                        for (Song song : songs) {
                            if (song.getSong() != null) {
                                Log.d("Song path", song.getSong().getAbsolutePath());
                            } else {
                                Log.d("Path does not exist", song.getTitle());
                            }
                        }
                    }
                }
            }).execute().get();
        } catch (Throwable t){
            t.printStackTrace();
            return null;
        }
    }

    private void initPermissions(){

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.d("FragmentInteraction", uri.toString());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                } else {
                    Log.d("What went wrong", "idfk");
                }
                return;
            }
            case MY_PERMISSIONS_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void playAudio(String media){
        if (!serviceBound){
            Intent playerIntent = new Intent(this, MusicService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }
}