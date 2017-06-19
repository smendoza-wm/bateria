package com.example.saul_wm.bateria;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saul_wm.bateria.Bateria.BateriaDinamica;
import com.example.saul_wm.bateria.Localizacion.GPS;
import com.example.saul_wm.bateria.Localizacion.Localizacion;
import com.example.saul_wm.bateria.Movimiento.Acelerometro;
import com.example.saul_wm.bateria.Movimiento.ContadorPasos;
import com.example.saul_wm.bateria.Movimiento.Orientacion;
import com.example.saul_wm.bateria.Servicios.AplicacionesActivas;
import com.example.saul_wm.bateria.Telefono.HistorialLlamadas;
import com.example.saul_wm.bateria.Telefono.HistorialMjs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText tv1;
    private EditText tv2;
    private EditText tv3;
    private EditText tv4;
    private EditText tv5;
    private EditText tv6;
    private EditText tv7;
    private EditText tv8;
    private TextView tv9;
    private TextView tv10;

    private Button iniciar;
    private Button finalizar;

    private Orientacion orientacion;
    private Acelerometro acelerometro;
    private ContadorPasos contadorPasos;

    private SQLiteDatabase db;

    private Localizacion localizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        localizacion = new Localizacion(this, this, tv9, tv10);

        /*startService(new Intent(this, AplicacionesActivas.class));

        BateriaDinamica batDinamica = new BateriaDinamica(this);
        batDinamica.iniciarRegistro();

      /*  Activity act = this;
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

        tv9.setText(aux);

       /* HistorialMjs historialMjs = new HistorialMjs(this);
        ArrayList<String[]> mensajes = historialMjs.getHistorial();

        aux = "";
        for (String[] msj : mensajes) {
            aux += "-----------------------------------------------\n";
            aux += "Numero " + msj[HistorialMjs.TELEFONO] + "\n";
            aux += "Fecha Llamada " + msj[HistorialMjs.] + "\n" ;
            aux += "Duracion Llamada " + msj[HistorialMjs.TELEFONO] + "\n" ;
            aux += "Tipo de llamada " + msj[HistorialMjs.TELEFONO] + "\n";
        }

        tv9.setText(aux);*/



        //Acelerometro acelerometro = new Acelerometro(this, aceleracionX, aceleracionY, aceleracionZ);
        //acelerometro.activar();

       // Orientacion orientacion = new Orientacion(this, aceleracionX, aceleracionY, aceleracionZ);
        //orientacion.iniciar();

        //ContadorPasos contadorPasos = new ContadorPasos(this, aceleracionX);
        //contadorPasos.iniciar();

        //GPS gps = new GPS(this, this, tv9, tv10 );
        //gps.inicia();

        orientacion = new Orientacion(this, tv1, tv2, tv3);
        contadorPasos = new ContadorPasos(this, tv4);
        acelerometro = new Acelerometro(this, tv5, tv6, tv7, tv8);



    }

    @Override
    protected void onStart() {
        super.onStart();
        localizacion.iniciar();

    }

    @Override
    protected void onStop() {
        super.onStop();
        localizacion.finalzar();

    }

    private void initComponents(){
       // tv_nivelBateria = (TextView) findViewById(R.id.tv_nivelBateria);
        tv1 = (EditText) findViewById(R.id.tv1);
        tv2 = (EditText) findViewById(R.id.tv2);
        tv3 = (EditText) findViewById(R.id.tv3);
        tv4 = (EditText) findViewById(R.id.tv4);
        tv5 = (EditText) findViewById(R.id.tv5);
        tv6 = (EditText) findViewById(R.id.tv6);
        tv7 = (EditText) findViewById(R.id.tv7);
        tv8 = (EditText) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv10 = (TextView) findViewById(R.id.tv10);

        iniciar = (Button) findViewById(R.id.btn_iniciar);
        finalizar = (Button) findViewById(R.id.btn_finalizar);

        iniciar.setOnClickListener(this);
        finalizar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_iniciar:
                orientacion.iniciar();
                contadorPasos.iniciar();
                acelerometro.iniciar();
                break;

            case R.id.btn_finalizar:
                orientacion.detener();
                contadorPasos.detener();
                acelerometro.detener();
                break;
        }
    }

}
