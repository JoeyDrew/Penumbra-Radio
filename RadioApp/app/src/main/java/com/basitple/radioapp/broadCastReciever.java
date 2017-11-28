package com.basitple.radioapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by basit on 11/28/2017.
 */

    public class broadCastReciever extends BroadcastReceiver {

    @Override
    public  void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("android.intent.actiion.BOOT_COMPLETED")){

        }
    }
}

