package com.example.saul_wm.bateria.Pantalla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class Pantalla extends BroadcastReceiver {
    private boolean prendido;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON )){
            prendido = true;
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            prendido = false;
        }
    }

    public Pantalla(Context context){
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_SCREEN_ON));
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }
}
