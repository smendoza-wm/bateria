package com.example.saul_wm.bateria.Bateria;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.example.saul_wm.bateria.Utils.Constantes;

/*###########################################################################################
  #  @description: Clase que monitorea el nivel de la bateria, lo hace de manera estatica,  #
  #                es decir, solo obtiene la informacion de la bateria cuando se crea un    #
  #                un objeto, y no vuelve a actualizar su informacion, a menos que se llame #
  #                al metodo actualizarInformacion();                                       #
  #  @author: WebMaps S.A.                                                                  #
  ###########################################################################################*/

public class BateriaEstatica {

    //Constantes para ver el tipo de carga
    public static final String CARGA_USB = "USB";
    public static final String CARGA_AC = "AC";

    private int nivelActual = 0;

    private boolean bateriaBaja = false;
    private boolean cargando = false;
    private String fuenteCarga = "NO_CARGA";

    private Intent statusBateria;
    private IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    public BateriaEstatica(Context context){
        actualizaInformacion(context);
    }

    public void actualizaInformacion(Context context){

        this.statusBateria = context.registerReceiver(null, ifilter);
        actualizaNivelActual();
        actualizaBateriaBaja();
        actualizaEstaCargando();
        actualizaFuenteCarga();
    }

    private void actualizaNivelActual(){
        this.nivelActual = statusBateria.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
    }

    private void actualizaBateriaBaja(){
        if(nivelActual < Constantes.BATERIA_MINIMA)
            bateriaBaja = true;
        else
            bateriaBaja = false;
    }

    private void actualizaEstaCargando(){
        int status = statusBateria.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
            cargando = true;
        else
            cargando = false;
    }

    private void actualizaFuenteCarga(){
        int conector = statusBateria.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if(conector == BatteryManager.BATTERY_PLUGGED_USB)
            fuenteCarga = "USB";
        else if(conector == BatteryManager.BATTERY_PLUGGED_AC)
            fuenteCarga = "AC";
        else
            fuenteCarga = "NO_CARGA";
    }

    public int getNivelActual(){
        return this.nivelActual;
    }

    public boolean esBateriaBaja(){
        return this.bateriaBaja;
    }

    public boolean estaCargando() {
        return this.cargando;
    }

    public String getFuenteCarga(){
        return this.fuenteCarga;
    }

}
