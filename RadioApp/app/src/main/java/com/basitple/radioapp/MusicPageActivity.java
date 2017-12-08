package com.basitple.radioapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MusicPageActivity extends AppCompatActivity{
    LinearLayout layout;
    TextView title;
    @Override
    protected void onCreate(Bundle saveInstancedState){
        super.onCreate(saveInstancedState);
        setContentView(R.layout.activity_music_page);
        layout = (LinearLayout)findViewById(R.id.MUSIC_LAYOUT);

        for(int i = 0 ; i < 100; i++){
            title = new TextView(this);
            title.setText("i: " + i);
            layout.addView(title);
        }

    }
}
