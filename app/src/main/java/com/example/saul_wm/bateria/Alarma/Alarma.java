package com.example.saul_wm.bateria.Alarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.saul_wm.bateria.Http.HttpGet;
import com.example.saul_wm.bateria.Localizacion.Localizacion;
import com.example.saul_wm.bateria.Modelo.KeepAlive;
import com.example.saul_wm.bateria.Servicios.Localizador;
import com.example.saul_wm.bateria.Utils.Constantes;

public class Alarma extends BroadcastReceiver
{
    protected Context context;
    private  long horaActual = 0;
    private KeepAlive keepAlive;

    private  Localizacion loc;

    public void setLoc(Localizacion loc) {
        this.loc = loc;
    }

    public Alarma(){
        //keepAlive = KeepAlive.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        keepAlive = KeepAlive.getInstance();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        this.context = context;

        onAlarm();

        wl.release();
    }

    public void setAlarm(Context context)
    {

        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarma.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10 , pi); // Millisec * Second * Minute

    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarma.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


    public long getHoraActual(){
        return this.horaActual;
    }

    private void onAlarm(){
        System.out.println("ultima hora" + keepAlive.getUltimaHoraActualizacion());
    }
}