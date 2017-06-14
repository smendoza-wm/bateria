package com.example.saul_wm.bateria.Bateria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import com.example.saul_wm.bateria.Utils.Constantes;

/*###########################################################################################
  #  @description: Clase que monitorea el nivel de la bateria, lo hace de manera dinamica   #
  #                mediante un BroadCast Receiver. Util para disparar eventos al existir    #
  #                un cambio en el estado de bateria: aumento/decremento en el % de pila,   #
  #                cambio en estado de carga (cargado/descargado) y llegar al nivel de      #
  #                bateria minima.                                                          #
  #                Hace uso de mas recursos, por lo que se sugiere usarla solo en caso      #
  #                necesario.                                                               #
  #                                                                                         #
  #  @author: WebMaps S.A.                                                                  #
  ###########################################################################################*/

public class BateriaDinamica extends BroadcastReceiver{

    private int nivelActual = 0;

    private boolean bateriaBaja;
    private boolean cargando;
    private String fuenteCarga = "NO_CARGA";

    private Context context;

    private boolean banderaBateriaMin = true;
    boolean cargandoAux = true;

    public static final String CARGA_USB = "USB";
    public static final String CARGA_AC = "AC";

    public BateriaDinamica(Context context){
        this.context = context;
    }

    public void iniciarRegistro(){
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void detenerRegistro(){
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Agregar alguna de las funciones para disparar eventos
        eventoPorcBat(intent);
        //eventoBatMin(intent);
        //eventoCambioCarga(intent);
    }

    private void actualizaInformacion(Intent intent){
        actualizaNivelActual(intent);
        actualizaBateriaBaja(intent);
        actualizaEstaCargando(intent);
        actualizaFuenteCarga(intent);
    }

    private void actualizaNivelActual(Intent intent){
        nivelActual = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
    }

    private void actualizaBateriaBaja(Intent intent){
        if(nivelActual < Constantes.BATERIA_MINIMA)
            bateriaBaja = true;
        else
            bateriaBaja = false;
    }

    private void actualizaEstaCargando(Intent intent){
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
            cargando = true;
        else
            cargando = false;
    }

    private void actualizaFuenteCarga(Intent intent){
        if(cargando) {
            int tipoCarga = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (tipoCarga == BatteryManager.BATTERY_PLUGGED_USB)
                fuenteCarga = "USB";
            else if(tipoCarga == BatteryManager.BATTERY_PLUGGED_AC)
                fuenteCarga = "AC";
            else
                fuenteCarga = "NO_CARGA";
        }
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean esBateriaBaja() {
        return bateriaBaja;
    }

    public boolean estaCargando() {
        return cargando;
    }

    public String getFuenteCarga() {
        return fuenteCarga;
    }

    public void imprimeInfo(){
        Log.d("Bateria ", "" + nivelActual);
        Log.d("Bateria ", "" + bateriaBaja);
        Log.d("Bateria ", "" + cargando);
        Log.d("Bateria ", "" + fuenteCarga);
    }

    private void eventoPorcBat(Intent intent){
        if(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) != nivelActual){
            //Si el nivel de la bateria cambia de porcentaje, dispara un evento
            actualizaInformacion(intent);
            Toast.makeText(context, "Bateria " + nivelActual, Toast.LENGTH_SHORT).show();
            imprimeInfo();
        }
    }

    private void eventoBatMin(Intent intent){
        if(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) < Constantes.BATERIA_MINIMA){
            // Si el nivel de la bateria llega a un minimo, dispara un evento
            if(banderaBateriaMin) {
                banderaBateriaMin = false;
                actualizaInformacion(intent);
                Toast.makeText(context, "Bateria minima", Toast.LENGTH_SHORT).show();
            }
        }
        else
            banderaBateriaMin = true;
    }

    private void eventoCambioCarga(Intent intent){
        if(intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING  )
            cargandoAux = true;
        else
            cargandoAux = false;

        if((cargandoAux != cargando) ){
            //Si cambia el estado de carga (cargandose/descargandose) dispara un evento
            actualizaInformacion(intent);
            if(cargandoAux)
                Toast.makeText(context, "Cargando el dispositivo", Toast.LENGTH_SHORT ).show();
            else
                Toast.makeText(context, "Bateria descargandose", Toast.LENGTH_SHORT ).show();
        }
    }
}
