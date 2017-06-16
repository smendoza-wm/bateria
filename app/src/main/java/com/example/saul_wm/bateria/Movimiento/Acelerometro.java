package com.example.saul_wm.bateria.Movimiento;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saul_wm.bateria.Utils.Constantes;

import org.w3c.dom.Text;

public class Acelerometro implements SensorEventListener{
    private float acelX;
    private float acelY;
    private float acelZ;

    private float ultAcelX;
    private float ultAcelY;
    private float ultAcelZ;

    private long tiempoAnterior;

    private SensorManager sensorManager;
    private Sensor acelerometro;

    private EditText x;
    private EditText y;
    private EditText z;
    private EditText promedio;

    public Acelerometro(Context context, EditText x, EditText y, EditText z, EditText promedio){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.x = x;
        this.y = y;
        this.z = z;
        this.promedio = promedio;
    }

    public void iniciar(){
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);
    }

    public void detener(){
        sensorManager.unregisterListener(this);
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
                long difTiempo = (tiempoActual - tiempoAnterior);
                tiempoAnterior = tiempoActual;

                //TODO Si la velociodad es mayor que cierto limite (por ejemplo si es mayor a 1 m/s, entonces dispara un evento
                //TODO obtener velocidad
                float speed = Math.abs(acelX + acelY + acelY - ultAcelX - ultAcelY - ultAcelZ)/ difTiempo * 10000;

                Log.d("Velocidad",speed+"");
                Log.d("Velocidad X", acelX+"");
                Log.d("Velocidad Y", acelY+"");
                Log.d("Velocidad Z", acelZ+"");

                x.setText(acelX + "");
                y.setText(acelY + "");
                z.setText(acelZ + "");

                ultAcelX = acelX;
                ultAcelY = acelY;
                ultAcelZ = acelZ;

                float promedio = ( Math.abs(acelX) + Math.abs(acelY) + Math.abs(acelZ) ) / 3;
                this.promedio.setText(promedio + "");
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

}
