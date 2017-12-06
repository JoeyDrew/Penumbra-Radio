package com.basitple.radioapp;

//what to delete here?
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Main_page2 extends AppCompatActivity
{
    Button butAlarm;

    /*For Audio Player*/
    private Button btn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;
    SeekBar seekBar;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private TextView songTitle;
    private int index = 0;
    Handler seekHandler = new Handler();
    /*For Audio player*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        //Navigation to News Button
        butAlarm = (Button)findViewById(R.id.newsButton);
        butAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new news());
            }
        });

         /*For Audio player*/
        btn = (Button) findViewById(R.id.button);
        songTitle = (TextView) findViewById(R.id.songTitle);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        btn.setOnClickListener(pausePlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //SeekBar
        seekUpdation();


        //Add song to list
        //As an example I added four songs hosted on my Google Drive
        HashMap<String, String> song1 = new HashMap<String, String>();
        HashMap<String, String> song2 = new HashMap<String, String>();
        HashMap<String, String> song3 = new HashMap<String, String>();
        HashMap<String, String> song4 = new HashMap<String, String>();

        //Song 1
        song1.put("songTitle", "Osterreich - Munou");
        song1.put("songPath", "https://drive.google.com/uc?export=download&id=1H_gZKnj9rt_UrO3pqR7CvWhwwoKpgHta");
        songsList.add(song1);

        //Song 2
        song2.put("songTitle", "Wind Adlib - Dependable Cheerful Person");
        song2.put("songPath", "https://drive.google.com/uc?export=download&id=1Ps9ScinoAVmnxJftPrPriv270qi1FAMd");
        songsList.add(song2);

        //Song 3
        song3.put("songTitle", "Yui - Again");
        song3.put("songPath", "https://drive.google.com/uc?export=download&id=12NsEjSHdY0z_X5jjtoTB4HFf9ZzpnFRE");
        songsList.add(song3);

        //Song 4
        song4.put("songTitle", "Chata - Dango Daikazoku");
        song4.put("songPath", "https://drive.google.com/uc?export=download&id=1kdHjUAURiWGFFB-kzb1KuLnWcxQOv9ow");
        songsList.add(song4);

        //Show song list // Checking List
        //TextView sL = (TextView)findViewById(R.id.textView);
        //sL.setText(songsList.get(index).get("songTitle")+songsList.get(index+1).get("songTitle")+songsList.get(index+2).get("songTitle") );


        final int duration = mediaPlayer.getDuration();
        final int amountToUpdate = duration / 100;
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (!(amountToUpdate * seekBar.getProgress() >= duration)) {
                            int p = seekBar.getProgress();
                            p += 1;
                            seekBar.setProgress(p);
                        }
                    }
                });
            };
        }, amountToUpdate);
         /* For Audio player*/
    }

    /*For Audio player*/
    //On Play
    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if (!playPause) {
                btn.setText("Pause");
                if (intialStage) {
                    new Player().execute(songsList.get(index).get("songPath"));
                    songTitle.setText(songsList.get(index).get("songTitle"));
                } else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                    seekBar.setEnabled(true);
                }
                playPause = true;
            } else {
                btn.setText("play");
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                playPause = false;
            }
        }
    };

    //Seekbar
    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() { seekBar.setProgress(mediaPlayer.getCurrentPosition()); seekHandler.postDelayed(run, 1000); }


    //Media Player Stuff
    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {
                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub

                        //Play next song
                        if(index < (songsList.size() - 1)) {
                            new Player().execute(songsList.get(index+1).get("songPath"));
                            songTitle.setText(songsList.get(index+1).get("songTitle"));
                            index++;
                        }
                        //Play next song

                        intialStage = true;
                        playPause=false;
                        btn.setText("play");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();

                //Seekbar
                seekBar.setMax(mediaPlayer.getDuration());
                //Seekbar

                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();
            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(Main_page2.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    /*For Audio player*/

    //Method to Hiding Navigation Bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //FragmentManager(fm) swaps out the fragments
    private void loadFragment(Fragment fragment)
    {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();

        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.backActivity, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}