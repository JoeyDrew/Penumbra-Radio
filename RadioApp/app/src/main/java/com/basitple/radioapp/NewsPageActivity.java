package com.basitple.radioapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class NewsPageActivity extends AppCompatActivity {
    Button backButtonNews;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news);

        backButtonNews = (Button)findViewById(R.id.NEWS_BACK);
        backButtonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
