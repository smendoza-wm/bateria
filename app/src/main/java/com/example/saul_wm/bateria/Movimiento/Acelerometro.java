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

public class Acelerometro implements SensorEventListener{
    private float acelX;
    private float acelY;
    private float acelZ;

    private float ultAcelX;
    private float ultAcelY;
    private float ultAcelZ;

    private ArrayList<Float> historialAceleracion;

    private long tiempoAnterior;
    private Context context;

    private SensorManager sensorManager;
    private Sensor acelerometro;

    private GPS gps;

    private EditText x;
    private EditText y;
    private EditText z;
    private EditText promedio;

    private String idDispositivo;

    public Acelerometro(Context context, EditText x, EditText y, EditText z, EditText promedio){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        this.x = x;
        this.y = y;
        this.z = z;
        this.promedio = promedio;


    }

    public Acelerometro(Context context){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        historialAceleracion = new ArrayList<>();
        this.context = context;
        gps = new GPS(context);
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

               // x.setText(acelX + "");
               // y.setText(acelY + "");
               // z.setText(acelZ + "");

                ultAcelX = acelX;
                ultAcelY = acelY;
                ultAcelZ = acelZ;

                float promedio = ( Math.abs(acelX) + Math.abs(acelY) + Math.abs(acelZ) ) / 3;
                historialAceleracion.add(promedio);
                if(historialAceleracion.size() == 20) {
                    float prom = 0;
                    for (int i = 0; i < historialAceleracion.size(); i++){
                        prom += historialAceleracion.get(i);
                    }
                    prom /= 20;
                    System.out.println("EL PROMEDIO DE 10 MEDICIONES FUE: " + prom);
                    //Toast.makeText(context,"EL PROMEDIO DE 10 MEDICIONES FUE: " + prom, Toast.LENGTH_SHORT).show();
                    historialAceleracion.clear();

                    if(prom > 0.65){
                        gps.setIdDispositivo(idDispositivo);
                        gps.iniciarSimple();
                        System.out.println("Latitud: " + gps.getLatitud());
                        System.out.println("Longitud: " + gps.getLongitud());
                    }

                }
               // this.promedio.setText(promedio + "");
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
