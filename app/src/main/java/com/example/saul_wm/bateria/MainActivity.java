package com.example.saul_wm.bateria;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.saul_wm.bateria.Telefono.HistorialMjs;


public class MainActivity extends AppCompatActivity {

    TextView tv_nivelBateria;
    TextView aceleracionX;
    TextView aceleracionY;
    TextView aceleracionZ;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        HistorialMjs historialMjs = new HistorialMjs(this);
        historialMjs.getHistorial();

        //startService(new Intent(this, AplicacionesActivas.class));

        //BateriaDinamica batDinamica = new BateriaDinamica(this);
        //batDinamica.iniciarRegistro();

        /*Activity act = this;
        HistorialLlamadas historialLlamadas = new HistorialLlamadas(act);

        ArrayList<String[]> llamadas =  historialLlamadas.getHistorial();

        String aux = "";
        for (String[] llamada : llamadas) {
            aux += "-----------------------------------------------\n";
            aux += "Numero " + llamada[0] + "\n";
            aux += "Fecha Llamada " + llamada[1] + "\n" ;
            aux += "Duracion Llamada " + llamada[2] + "\n" ;
            aux += "Tipo de llamada " + llamada[3] + "\n";
        }

        Log.d("Llamada", aux);*/

        //Acelerometro acelerometro = new Acelerometro(this, aceleracionX, aceleracionY, aceleracionZ);
        //acelerometro.activar();

       // Orientacion orientacion = new Orientacion(this, aceleracionX, aceleracionY, aceleracionZ);
        //orientacion.iniciar();

        //ContadorPasos contadorPasos = new ContadorPasos(this, aceleracionX);
        //contadorPasos.iniciar();

        //GPS gps = new GPS(this, this, aceleracionX, aceleracionY);
        //gps.inicia();

    }

    private void initComponents(){
       // tv_nivelBateria = (TextView) findViewById(R.id.tv_nivelBateria);
        aceleracionX = (TextView) findViewById(R.id.et_aceleracionX);
        aceleracionY = (TextView) findViewById(R.id.et_aceleracionY);
        aceleracionZ = (TextView) findViewById(R.id.et_aceleracionZ);
    }
}
