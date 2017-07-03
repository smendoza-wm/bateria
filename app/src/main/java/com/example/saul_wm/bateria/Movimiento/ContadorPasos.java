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
    private GPS gps;

    private Sensor contadorPasos;

    private EditText tv;

    public ContadorPasos(Context context){
        super(context);
        contadorPasos = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        gps = new GPS(context);

        this.tv = tv;
    }

    @Override
    public void iniciar() {
        sensorManager.registerListener(this, contadorPasos, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void detener() {
        sensorManager.unregisterListener(this);
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
            //tv.setText(pasos + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
