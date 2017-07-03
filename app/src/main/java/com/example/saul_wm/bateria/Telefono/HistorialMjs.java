package com.example.saul_wm.bateria.Telefono;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

public class HistorialMjs {

    private Context context;
    private ArrayList<String[]> historial;

    public static final int TELEFONO = 0;
    public static final int FECHA = 1;
    public static final int TIPO = 2;
    public static final int MENSAJE = 3;

    public HistorialMjs(Context context){
        this.context = context;
        historial = new ArrayList<>();
    }

    public ArrayList<String[]> getHistorial(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("*********SMS History*************** :");
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        String[] msj = new String[4];
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                msj[0] = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                msj[1] = new Date(Long.valueOf(date))+ "";
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                switch (Integer.parseInt(type)) {
                    case 1:
                        msj[2] = "INBOX";
                        break;

                    case 2:
                        msj[2] = "SENT";
                        break;

                    case 3:
                        msj[2] = "DRAFT";
                        break;
                }
                msj[3] = cursor.getString(cursor.getColumnIndexOrThrow("body"));

                historial.add(msj);
                stringBuffer.append("\nPhone Number:--- " + msj[0] + " \nMessage Type:--- "
                        + msj[2] + " \nMessage Date:--- " + msj[1]
                        + " \nMessage Body:--- " + msj[3]);
                stringBuffer.append("\n----------------------------------");
                cursor.moveToNext();
            }

        }
        System.out.println(stringBuffer);
        cursor.close();
        return historial;
    }

    //TODO Mandar mensajes de panico

}
