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

import com.example.saul_wm.bateria.Alarma.Alarm;
import com.example.saul_wm.bateria.Localizacion.GPS;
import com.example.saul_wm.bateria.MainActivity;
import com.example.saul_wm.bateria.Movimiento.Acelerometro;
import com.example.saul_wm.bateria.Movimiento.ContadorPasos;

import org.w3c.dom.Text;

public class Localizador extends Service{

    private ContadorPasos contadorPasos;
    private Acelerometro acelerometro;

    Alarm alarm = new Alarm();

    public Localizador() {

    }



    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        primerPlano();
        iniciarAcelerometro();
        alarm.setAlarm(this);
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
        acelerometro.iniciar();
    }
}
