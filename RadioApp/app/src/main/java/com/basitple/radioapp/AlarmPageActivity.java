package com.basitple.radioapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class AlarmPageActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    TimePicker time_Picker;
    Context context;
    ToggleButton toggle ;
    PendingIntent alarmIntent;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        this.context =this;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        time_Picker = (TimePicker)findViewById(R.id.timePicker);
        final Calendar calendar = Calendar.getInstance();
        final Intent intent = new Intent(this.context,AlarmPageActivity.class);
        toggle = (ToggleButton)findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public  void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    //send a message.
                    //start the alarm
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, time_Picker.getHour());
                    calendar.set(Calendar.MINUTE, time_Picker.getMinute());
                    alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);
                }
                else{

                    //turn of the alarm
            }

        }});
    }

}
