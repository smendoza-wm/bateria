package com.example.saul_wm.bateria.Bateria;

/*########################################################################################
  #  @descripcion: Clase que monitorea el nivel de la bateria, y de la cual se pueden    #
  #               obtener varias variables como tipo de conexion, historial de uso, etc. #
  ########################################################################################*/

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class BateriaDinamica extends BroadcastReceiver{

    private int nivelMin = 20;
    private int nivelActual = 0;

    private boolean esBateriaBaja;
    private boolean estaCargando;
    private String fuenteCarga="SinCarga";


    public static final String CARGA_USB = "USB";
    public static final String CARGA_AC = "AC";

    public BateriaDinamica(Context context){
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) != nivelActual){
            actualizaNivelActual(intent);
            actualizaBateriaBaja(intent);
            actualizaEstaCargando(intent);
            actualizaFuenteCarga(intent);

            Log.d("Bateria ", ""+intent.getAction());
            Log.d("Bateria ", ""+nivelActual);
            Log.d("Bateria ", ""+esBateriaBaja);
            Log.d("Bateria ", ""+estaCargando);
            Log.d("Bateria ", ""+fuenteCarga);

        }
    }

    private void actualizaNivelActual(Intent intent){
        nivelActual = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
    }

    private void actualizaBateriaBaja(Intent intent){
        if(nivelActual < nivelMin)
            esBateriaBaja = true;
        else
            esBateriaBaja = false;
    }

    private void actualizaEstaCargando(Intent intent){
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
            estaCargando = true;
        else
            estaCargando = false;
    }

    private void actualizaFuenteCarga(Intent intent){
        if(estaCargando) {
            int tipoCarga = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (tipoCarga == BatteryManager.BATTERY_PLUGGED_USB)
                fuenteCarga = "USB";
            else
                fuenteCarga = "AC";
        }
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean esBateriaBaja() {
        return esBateriaBaja;
    }

    public boolean estaCargando() {
        return estaCargando;
    }

    public String getFuenteCarga() {
        return fuenteCarga;
    }

}
