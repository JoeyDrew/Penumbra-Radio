package com.basitple.radioapp;

//what to delete here?
import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                , View.OnClickListener
{
    private ArrayList<Song> songs;
    private int currentSongIndex;
    private int maxIndex;
    private Boolean playing;
    private Boolean ready = false;
    SlidingUpPanelLayout slidingLayout;
    Fragment homePage = null;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    private MediaPlayer player;
    boolean serviceBound = false;
    ImageButton prevSongBtn;
    ImageButton nextSongBtn;
    ImageButton playSongBtn;
    SeekBar seekBar;
    Map<String, String> headers;
    private final Context context = this;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        if(homePage == null){
            homePage = new HomePage();
        }
        setContentView(R.layout.activity_main);
        playSongBtn = (ImageButton) findViewById(R.id.PLAY_SONG);
        nextSongBtn = (ImageButton) findViewById(R.id.NEXT_SONG);
        prevSongBtn = (ImageButton) findViewById(R.id.PREVIOUS_SONG);
        seekBar = (SeekBar) findViewById(R.id.SEEK_BAR);

        playSongBtn.setOnClickListener(this);
        nextSongBtn.setOnClickListener(this);
        prevSongBtn.setOnClickListener(this);

        initPermissions();
        headers = new HashMap<>();
        playing = false;
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.SLIDING_LAYOUT);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        fragmentTransaction.add(R.id.mainFragContainer, homePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    private void initMusicPlayer(){
        try {
            currentSongIndex = 0;
            player = new MediaPlayer();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(player.getDuration());
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (player != null) {
                                player.seekTo(progress * 1000);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    playing = true;
                    playSongBtn.setImageResource(android.R.drawable.ic_media_pause);
                    mp.start();
                }


            });
            player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.d("Buffering", Integer.toString(percent));
                }
            });
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            new BuildMusicStream(new AsyncResponse<ArrayList<Song>>() {
                @Override
                public void processFinish(ArrayList<Song> output) {
                    if(output == null) finish();
                    songs = output;
                    maxIndex = songs.size()-1;
                    setSong(currentSongIndex);
                }
            }).execute(this);

        } catch (Throwable t){
            t.printStackTrace();
            return;
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
        if(player != null){
            player.release();
            player = null;
        }
    }
    public void setSong(int index){
        String url = Config.SERVER_URL + "/songs/" + Integer.toString(index+1) + "/listen";
        Log.d("url", url);
        try {

            player.setDataSource(url);
            ready = false;
            player.prepareAsync();
            playing = true;
        } catch (Throwable t){
            Log.e("Failed to set source", url);
            t.printStackTrace();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int clickId = v.getId();
        switch (clickId) {
            case R.id.PLAY_SONG: {
                if(songs != null) {
                    if (!playing) {
                        playing = true;
                        playSongBtn.setImageResource(android.R.drawable.ic_media_pause);
                        player.start();
                    } else {
                        playing = false;
                        playSongBtn.setImageResource(android.R.drawable.ic_media_play);
                        player.pause();
                    }
                } else {
                    playSongBtn.setImageResource(android.R.drawable.ic_media_pause);
                    initMusicPlayer();
                }

                break;
            }
            case R.id.PREVIOUS_SONG: {
                if(songs != null){

                } else {
                    initMusicPlayer();
                }
                break;
            }
            case R.id.NEXT_SONG: {
                if(songs != null) {
                    currentSongIndex = (currentSongIndex + 1) % maxIndex;
                    player.reset();
                    playing = false;
                    setSong(currentSongIndex);
                } else {
                    initMusicPlayer();
                }
                break;
            }
        }
    }
}