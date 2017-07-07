package com.example.saul_wm.bateria.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.saul_wm.bateria.BaseDatos.BaseDatos;
import com.example.saul_wm.bateria.Localizacion.Localizacion;
import com.example.saul_wm.bateria.MainActivity;
import com.example.saul_wm.bateria.Movimiento.Acelerometro;
import com.example.saul_wm.bateria.Movimiento.ContadorPasos;
import com.example.saul_wm.bateria.R;
import com.example.saul_wm.bateria.Utils.Constantes;

public class Localizador extends Service{

    private static final String ACTION_STOP_SERVICE = "stop";
    private ContadorPasos contadorPasos;
    private Acelerometro acelerometro;
    private Localizacion locGoogle;

    private String idDispositivo;

    private NotificationCompat.Builder builder;

    public Localizador() {

    }


    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        if(intent != null) {
            if (ACTION_STOP_SERVICE.equals(intent.getAction())) {

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(1);
                locGoogle.finalzar();

                stopSelf();
            }
        }
        primerPlano();

        idDispositivo = getIdDispositivo();
        iniciarServicioLocalizacionGoogle();
        //iniciarAcelerometro();

        /*alarma = new Alarma();
        alarma.setIdDispositivo(idDispositivo);
        alarma.setAlarm(this);*/

        //iniciarContadorPasos();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void primerPlano()
    {

        Intent stopSelf = new Intent(this, Localizador.class);
        stopSelf.setAction(ACTION_STOP_SERVICE);

        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf,PendingIntent.FLAG_CANCEL_CURRENT);
        /*Se agrega el bloque de notificación para crear un servicio en segundo plano*/
         builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Finder&Tracker")
                .setContentText("Localizando dispositivo")
                .addAction(R.drawable.common_google_signin_btn_text_dark, "Cerrar servicio", pStopSelf);
        //Se le indica que pantalla debera abrir en caso de que se seleccione la notificación
        Intent abrirAPP= new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, abrirAPP, 0);
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

    private void iniciarServicioLocalizacionGoogle(){
        locGoogle = new Localizacion(getApplicationContext());
        locGoogle.setIdDispositivo(idDispositivo);
        locGoogle.iniciar();
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
