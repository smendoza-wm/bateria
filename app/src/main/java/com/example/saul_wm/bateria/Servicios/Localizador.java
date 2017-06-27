package com.example.saul_wm.bateria.Servicios;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Localizacion.GPS;
import com.example.saul_wm.bateria.MainActivity;

import org.w3c.dom.Text;

public class Localizador extends Service implements SensorEventListener{
    private SensorManager sensorManager = null;
    private Sensor sensor = null;
    private int pasos = 0;
    private GPS gps;

    public Localizador() {

    }



    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        primerPlano();
        iniciarContadorPasos();
        Context context = getApplicationContext();
        gps = new GPS(context);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            pasos++;
            System.out.println("Numero de pasos: " + pasos);

            if(pasos % 10 == 0){
                gps.iniciarSimple();
                System.out.println("Latitud: " + gps.getLatitud());
                System.out.println("Longitud: " + gps.getLongitud());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void iniciarContadorPasos(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }
}
