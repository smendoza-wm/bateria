package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.saul_wm.bateria.Localizacion.GPS;

public abstract class SensorMovimiento implements SensorEventListener {

    protected SensorManager sensorManager;
    protected Sensor sensor;
    protected GPS gps;

    private int DELAY_MUESTREO;

    public SensorMovimiento(Context context, int TIPO_SENSOR, int DELAY_MUESTREO){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(TIPO_SENSOR);
        this.DELAY_MUESTREO = DELAY_MUESTREO;
        gps = new GPS(context);
    }

    public void iniciar(){
        sensorManager.registerListener(this, sensor, DELAY_MUESTREO);
    }

    public  void detener(){
        sensorManager.unregisterListener(this);
    }

}
