package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorMovimiento implements SensorEventListener {
    protected SensorManager sensorManager;


    public SensorMovimiento(Context context){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
    }

    public abstract void iniciar();
    public abstract void detener();


}
