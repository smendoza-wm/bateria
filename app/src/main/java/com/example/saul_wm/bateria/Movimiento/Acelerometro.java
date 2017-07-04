package com.example.saul_wm.bateria.Movimiento;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Localizacion.GPS;
import com.example.saul_wm.bateria.Utils.Constantes;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class Acelerometro extends SensorMovimiento{

    private float acelX;
    private float acelY;
    private float acelZ;

    private ArrayList<Float> historialAceleracion;
    private long tiempoAnterior;

    private String idDispositivo;

    public Acelerometro(Context context){
        super(context, Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_NORMAL);
        historialAceleracion = new ArrayList<>();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor auxSensor = sensorEvent.sensor;
        if (auxSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            acelX = sensorEvent.values[0];
            acelY = sensorEvent.values[1];
            acelZ = sensorEvent.values[2];

            long tiempoActual = System.currentTimeMillis();

            if ((tiempoActual - tiempoAnterior) > Constantes.INTERVALO_MUESTRA_ACELERACION) { //Si han pasado 'n' segundos
                tiempoAnterior = tiempoActual;

                float promedio = ( Math.abs(acelX) + Math.abs(acelY) + Math.abs(acelZ) ) / 3;
                historialAceleracion.add(promedio);
                if(historialAceleracion.size() == Constantes.NUMERO_MUESTRAS_EVENTO) {
                    float prom = 0;
                    for (int i = 0; i < historialAceleracion.size(); i++){
                        prom += historialAceleracion.get(i);
                    }
                    prom /= Constantes.NUMERO_MUESTRAS_EVENTO;
                    System.out.println("EL PROMEDIO DE "+ Constantes.NUMERO_MUESTRAS_EVENTO +" MEDICIONES FUE: " + prom);
                    historialAceleracion.clear();

                    if(prom > Constantes.ACELERACION_MINIMA_EVENTO){
                        gps.setIdDispositivo(idDispositivo);
                        gps.iniciarSimple();
                        System.out.println("Latitud: " + gps.getLatitud());
                        System.out.println("Longitud: " + gps.getLongitud());
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    public float getAceleracionX() {
        return acelX;
    }

    public float getAceleracionY() {
        return acelY;
    }

    public float getAceleracionZ() {
        return acelZ;
    }

    public void setIdDispositivo(String id){this.idDispositivo = id;}

}
