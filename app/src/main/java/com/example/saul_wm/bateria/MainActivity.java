package com.example.saul_wm.bateria;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.saul_wm.bateria.Bateria.BateriaDinamica;
import com.example.saul_wm.bateria.Telefono.Telefono;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    TextView tv_nivelBateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        BateriaDinamica batDinamica = new BateriaDinamica(this);


       /* Activity act = this;
        Telefono tel = new Telefono(act);
        tel.getCallDetails();
        ArrayList<HashMap<String,String>> llamadas = tel.getLlamadas();

        String aux = "";
        for (HashMap<String, String> llamada : llamadas) {
            aux += "-----------------------------------------------";
            aux += "Numero " + llamada.get("NumTel") + "\n";
            aux += "Fecha Llamada " + llamada.get("FechaLlamada") + "\n" ;
            aux += "Duracion Llamada " + llamada.get("DuracionLlamada") + "\n" ;
        }

        Log.d("Llamada", aux);*/
    }

    private void initComponents(){
        tv_nivelBateria = (TextView) findViewById(R.id.tv_nivelBateria);
    }
}


 /* initComponents();
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningServices(10);
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
            try {
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(
                        info.processName, PackageManager.GET_META_DATA));
                Log.w("LABEL", c.toString());
            } catch (Exception e) {
                // Name Not FOund Exception
            }
        }

       // BateriaDinamica bateria = new BateriaDinamica(this);
        //tv_nivelBateria.setText(""+bateria.getNivelActual());

       /* if(bateria.esBateriaBaja()){
            Log.d("Nivel de bateria", "Bajo");
        }
        else
            Log.d("Nivel de bateria", "Por encima del minimo");

        if(bateria.estaCargando()){
            Log.d("Carga", "Esta cargando");
            if(bateria.getFuenteCarga().equals(BateriaDinamica.CARGA_USB))
                Log.d("Carga", "Por usb");
            else if(bateria.getFuenteCarga().equals(BateriaDinamica.CARGA_AC))
                Toast.makeText(this, "Mediante AC", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Carga No esta cargando", Toast.LENGTH_LONG).show();
        }


       /* Bateria bateria = new Bateria(getApplicationContext(), tv_nivelBateria);
        tv_nivelBateria.setText(""+bateria.getNivelActual());*/