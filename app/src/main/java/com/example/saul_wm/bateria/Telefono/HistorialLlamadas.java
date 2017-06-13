package com.example.saul_wm.bateria.Telefono;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HistorialLlamadas {


    private ArrayList<String[]> llamadas = new ArrayList<>();
    private Activity activity;

    public HistorialLlamadas(Activity act) {
        this.activity = act;
        getHistorialLlamadas();
    }

    private void getHistorialLlamadas() {


        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {
            Cursor managedCursor = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


            while (managedCursor.moveToNext()) {
                String [] llamada = new String[4];
                llamada[0] =  managedCursor.getString(number);
                llamada[1] = (new Date(Long.valueOf(managedCursor.getString(date))) + "");
                llamada[2] =  managedCursor.getString(duration);
                int dircode = Integer.parseInt(managedCursor.getString(type));
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        llamada[3] = "Saliente";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        llamada[3] =  "Entrante";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        llamada[3] =  "Perdida";
                        break;
                }
                llamadas.add(llamada);
            }
            managedCursor.close();
       }
    }

    public ArrayList<String[]> getHistorial(){
        return this.llamadas;
    }

}
