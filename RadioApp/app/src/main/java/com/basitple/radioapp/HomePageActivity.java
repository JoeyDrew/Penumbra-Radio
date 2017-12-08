package com.basitple.radioapp;

//what to delete here?
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button newsButton;
    private Button alarmButton;
    private Button musicButton;
    private Button profileButton;
    private Button logOutButton;
    public static List<Song> songs;
    BuildMusicStream stream;
    private Intent toAlarm;
    private Intent toProfile;
    private Intent toMusic;
    private Intent toNews;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toAlarm = new Intent(HomePageActivity.this, AlarmPageActivity.class);
        toMusic = new Intent(HomePageActivity.this, MusicPageActivity.class);
        toNews = new Intent(HomePageActivity.this, NewsPageActivity.class);
        toProfile = new Intent(HomePageActivity.this, ProfilePageActivity.class);
        initUI();
        initMusicPlayer();
    }

    private void initUI(){
        newsButton = (Button)findViewById(R.id.NEWS_BUTTON);
        alarmButton = (Button)findViewById(R.id.ALARM_BUTTON);
        musicButton = (Button)findViewById(R.id.MUSIC_BUTTON);
        profileButton = (Button)findViewById(R.id.PROFILE_BUTTON);
        logOutButton = (Button)findViewById(R.id.LOG_OUT_BUTTON);

        newsButton.setOnClickListener(this);
        alarmButton.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        musicButton.setOnClickListener(this);

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
    public void onClick(View v){
        if(v == newsButton){
            startActivity(toNews);
        } else if(v == alarmButton){
            startActivity(toAlarm);
        } else if(v == musicButton){
            startActivity(toMusic);
        } else if(v == profileButton){
            startActivity(toProfile);
        } else if(v == logOutButton){
            finish();
        }
    }
}