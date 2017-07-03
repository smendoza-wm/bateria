package com.example.saul_wm.bateria.Servicios;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.saul_wm.bateria.Alarma.Alarma;
import com.example.saul_wm.bateria.BaseDatos.BaseDatos;
import com.example.saul_wm.bateria.MainActivity;
import com.example.saul_wm.bateria.Movimiento.Acelerometro;
import com.example.saul_wm.bateria.Movimiento.ContadorPasos;
import com.example.saul_wm.bateria.Utils.Constantes;

public class Localizador extends Service{

    private ContadorPasos contadorPasos;
    private Acelerometro acelerometro;

    private String idDispositivo;

    Alarma alarma;

    public Localizador() {

    }



    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        primerPlano();
        idDispositivo = getIdDispositivo();
        iniciarAcelerometro();

        //Alarma alarma = new Alarma();

        //alarma.setAlarm(this);
        //iniciarContadorPasos();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void primerPlano()
    {
        /*Se agrega el bloque de notificación para crear un servicio en segundo plano*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Finder&Tracker")
                .setContentText("Localizando dispositivo");
        //Se le indica que pantalla debera abrir en caso de que se seleccione la notificación
        Intent abrirAPP= new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this, 0, abrirAPP, 0);
        builder.setContentIntent(pi);
        //Ponemos en primer plano el servicio
        startForeground(1, builder.build());
    }



    private void iniciarContadorPasos(){
        contadorPasos = new ContadorPasos(getApplicationContext());
        contadorPasos.iniciar();
    }

    private void iniciarAcelerometro(){
        acelerometro = new Acelerometro(getApplicationContext());
        acelerometro.setIdDispositivo(idDispositivo);
        acelerometro.iniciar();
    }

    private String getIdDispositivo(){
        BaseDatos bdBateria = new BaseDatos(this, Constantes.NOMBRE_BD, null, Constantes.VERSION_BD);
        SQLiteDatabase db = bdBateria.getWritableDatabase();
        Cursor cursor = db.query("dat_dispositivo", null, null, null, null, null, null);
        cursor.moveToNext();
        String id = cursor.getString(1);
        cursor.close();
        db.close();
        return id;
    }
}
