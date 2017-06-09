package com.example.saul_wm.bateria.Bateria;

/*########################################################################################
  #  @descripcion: Clase que monitorea el nivel de la bateria, y de la cual se pueden    #
  #               obtener varias variables como tipo de conexion, historial de uso, etc. #
  ########################################################################################*/


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BateriaEstatica {
    public static final String CARGA_USB = "USB";
    public static final String CARGA_AC = "AC";

    private int nivelMin = 40;
    private int nivelActual;

    Intent statusBateria;


    public BateriaEstatica(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.statusBateria = context.registerReceiver(null, ifilter);
    }

    public int getNivelActual(){
        this.nivelActual = statusBateria.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        return this.nivelActual;
    }

    public boolean esBateriaBaja(){
        if(getNivelActual() < nivelMin)
            return true;
        else
            return false;
    }

    public boolean estaCargando() {

        int status = statusBateria.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
            return true;
        else
            return false;
    }

    public String fuenteCarga(){
        int chargePlug = statusBateria.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
            return "USB";
        else
            return "AC";
    }

}
