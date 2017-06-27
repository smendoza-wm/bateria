package com.example.saul_wm.bateria.Movimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Orientacion implements SensorEventListener{

    private Sensor acelerometro;
    private Sensor magnetometro;

    private SensorManager sensorManager;

    private float[] gravedad;
    private float[] geomagnetico;

    private long tiempoAnterior = 0;

    private EditText tv1;
    private EditText tv2;
    private EditText tv3;


    public Orientacion(Context context, EditText tv1, EditText tv2, EditText tv3){
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometro = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;

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

                if ((tiempoActual - tiempoAnterior) > 500) { //Si han pasado 'n' segundos
                    tiempoAnterior = tiempoActual;
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);

                   /* System.out.println("-----------------------------------------------");
                    System.out.println("Azimuth:" + orientation[0]);
                    System.out.println("Pitch: " + orientation[1]);
                    System.out.println("Roll: " + orientation[2]);
                    System.out.println("***********************************************");*/

                    tv1.setText(orientation[0]+"");
                    tv2.setText(orientation[1]+"");
                    tv3.setText(orientation[2]+"");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
