package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saul_wm.bateria.Localizacion.GPS;

public class ContadorPasos extends SensorMovimiento {

    private int pasos = 0;

    public ContadorPasos(Context context){
        super(context, Sensor.TYPE_STEP_DETECTOR, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            pasos++;
            System.out.println("Pasos: " + pasos);
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
}
