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
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.util.ArrayList;
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
    private ArrayList<AudioFile> audioList;
    SlidingUpPanelLayout slidingLayout;
    Fragment homePage = null;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.basitple.radioapp.PlayNewAudio";
    private ServiceConnection serviceConnection;
    private MusicService player;
    boolean serviceBound = false;
    ImageButton prevSongBtn;
    ImageButton nextSongBtn;
    ImageButton playSongBtn;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initPermissions();
        initMusicPlayer();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        if(homePage == null){
            homePage = new HomePage();
        }
        setContentView(R.layout.activity_main);
        playSongBtn = (ImageButton) findViewById(R.id.PLAY_SONG);
        nextSongBtn = (ImageButton) findViewById(R.id.NEXT_SONG);
        prevSongBtn = (ImageButton) findViewById(R.id.PREVIOUS_SONG);
        playSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceBound) {
                    playAudio(0);
                } else if (!player.isPlaying()){
                    player.resumeMedia();
                } else {
                    player.pauseMedia();
                }
            }
        });

        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.SLIDING_LAYOUT);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        fragmentTransaction.add(R.id.mainFragContainer, homePage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void initMusicPlayer(){
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
            loadAudio();
            new BuildMusicStream(new AsyncResponse<Boolean>() {
                @Override
                public void processFinish(Boolean output) {
                    if (!output) finish();
                }
            }).execute().get();
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

    private void playAudio(int audioIndex) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudio(audioList);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(this, MusicService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
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

    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                audioList.add(new AudioFile(data, title, album, artist));
            }
        }
        cursor.close();
    }
}