package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;

public class ContadorPasos extends SensorMovimiento {

    private int pasos = 0;
    private int pasosAnteriores = 0;

    private Sensor contadorPasos;

    TextView tv;

    public ContadorPasos(Context context, TextView tv){
        super(context);
        contadorPasos = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
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
            // Calculate steps taken based on first counter value received.

            tv.setText(pasos + "");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
