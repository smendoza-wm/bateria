package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Orientacion implements SensorEventListener{

    private Sensor acelerometro;
    private Sensor magnetometro;

    private SensorManager sensorManager;

    private float[] gravedad;
    private float[] geomagnetico;

    private long tiempoAnterior = 0;

    public Orientacion(Context context){
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometro = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void iniciar(){
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometro, SensorManager.SENSOR_DELAY_UI);
    }

    public void detener(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravedad = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetico = event.values;
        if (gravedad != null && geomagnetico != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravedad, geomagnetico);
            if (success) {

                long tiempoActual = System.currentTimeMillis();

                if ((tiempoActual - tiempoAnterior) > 1000) { //Si han pasado 'n' segundos
                    tiempoAnterior = tiempoActual;
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);

                    System.out.println("-----------------------------------------------");
                    System.out.println("Azimuth:" + orientation[0]);
                    System.out.println("Pitch: " + orientation[1]);
                    System.out.println("Roll: " + orientation[2]);
                    System.out.println("***********************************************");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
