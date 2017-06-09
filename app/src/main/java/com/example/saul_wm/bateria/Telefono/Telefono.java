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

public class Telefono {
    private String numTelefono;

    private ArrayList<HashMap<String, String>> llamadas = new ArrayList<>();
    private Activity activity;

    public Telefono(Activity act) {
        this.activity = act;
    }

    public void getCallDetails() {


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
                HashMap<String, String> llamada = new HashMap<>();
                llamada.put("NumTel", managedCursor.getString(number));
                llamada.put("FechaLlamada", (new Date(Long.valueOf(managedCursor.getString(date))) + ""));
                llamada.put("DuracionLlamada", managedCursor.getString(duration));
                int dircode = Integer.parseInt(managedCursor.getString(type));
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        llamada.put("TipoLlamada", "Saliente");
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        llamada.put("TipoLlamada", "Entrante");
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        llamada.put("TipoLlamada", "Perdida");
                        break;
                }
                llamadas.add(llamada);
            }
            managedCursor.close();
       }
    }

    public ArrayList<HashMap<String,String>> getLlamadas(){
        return this.llamadas;
    }

}
