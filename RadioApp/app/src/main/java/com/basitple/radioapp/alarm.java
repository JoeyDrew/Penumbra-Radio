package com.basitple.radioapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class alarm extends Fragment {

    View view;
    AlarmManager alarmManager;
    TimePicker time_Picker;
    Context context;
    ToggleButton toggle ;
    PendingIntent alarmIntent;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_alarm, container, false);

        //this.context =this;
        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        time_Picker = (TimePicker)view.findViewById(R.id.timePicker);
        final Calendar calendar = Calendar.getInstance();
        final Intent intent = new Intent(this.context,AlarmPageActivity.class);
        toggle = (ToggleButton)view.findViewById(R.id.toggleButton);
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

        // Inflate the layout for this fragment
        return view;
    }

}
