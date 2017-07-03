package com.example.saul_wm.bateria;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saul_wm.bateria.Bateria.BateriaDinamica;
import com.example.saul_wm.bateria.Http.APIService;
import com.example.saul_wm.bateria.Localizacion.GPS;
import com.example.saul_wm.bateria.Localizacion.Localizacion;
import com.example.saul_wm.bateria.Modelo.LocalizacionJSON;
import com.example.saul_wm.bateria.Modelo.UbicacionJSON;
import com.example.saul_wm.bateria.Movimiento.Acelerometro;
import com.example.saul_wm.bateria.Movimiento.ContadorPasos;
import com.example.saul_wm.bateria.Movimiento.Orientacion;
import com.example.saul_wm.bateria.Pantalla.Pantalla;
import com.example.saul_wm.bateria.Servicios.AplicacionesActivas;
import com.example.saul_wm.bateria.Servicios.Localizador;
import com.example.saul_wm.bateria.Telefono.HistorialLlamadas;
import com.example.saul_wm.bateria.Telefono.HistorialMjs;
import com.example.saul_wm.bateria.Utils.Constantes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private APIService mAPIService;
    private LocalizacionJSON locJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        /*locJSON = new LocalizacionJSON();
        locJSON.setFecha("2017-06-28");
        locJSON.setHora("12:00");
        locJSON.setLatitud("98.123456");
        locJSON.setLongitud("55.123456");*/

        //mAPIService = Constantes.getAPIService();
        //Intent intent = new Intent(getApplicationContext(), ServiceContadorPasos.class );
        //startService(intent);

        //iniciarAcelerometro();
        //iniciarOrientacion();
        //iniciarContadorDePasos();

        //getHistorialLlamadas();
        //getHistorialMensajes();

        localizacion = new Localizacion(this, this, tv9, tv10);
        Intent localizador = new Intent(this, Localizador.class);
        //startService(localizador);


        //iniciarPantalla();
        //iniciarBateriaDinamica();

        //iniciarServicioApp();

        //GPS gps = new GPS(this, this, tv9, tv10 );
        //gps.inicia();

       // orientacion = new Orientacion(this, tv1, tv2, tv3);
        //contadorPasos = new ContadorPasos(this, tv4);
        //acelerometro = new Acelerometro(this, tv5, tv6, tv7, tv8);




    }

    @Override
    protected void onStart() {
        super.onStart();
        //localizacion.iniciar();

    }

    @Override
    protected void onStop() {
        super.onStop();

        //localizacion.finalzar();

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
                /*orientacion.iniciar();
                contadorPasos.iniciar();
                acelerometro.iniciar();*/
                //sendPost("Saul", locJSON);
                break;

            case R.id.btn_finalizar:
                orientacion.detener();
                contadorPasos.detener();
                acelerometro.detener();
                break;
        }
    }


    public void iniciarBateriaDinamica(){
        BateriaDinamica batDinamica = new BateriaDinamica(this);
        batDinamica.iniciarRegistro();
    }

    public void iniciarOrientacion(){
        orientacion = new Orientacion(this, tv1, tv2, tv3);
    }

    public void iniciarContadorDePasos(){
        contadorPasos = new ContadorPasos(this);
        contadorPasos.iniciar();
    }

    public void iniciarAcelerometro(){
        acelerometro = new Acelerometro(this, tv5, tv6, tv7, tv8);
    }

    public void getHistorialMensajes(){
        HistorialMjs historialMjs = new HistorialMjs(this);
        ArrayList<String[]> mensajes = historialMjs.getHistorial();

        String aux = "";
        for (String[] msj : mensajes) {
            aux += "-----------------------------------------------\n";
            aux += "Numero " + msj[HistorialMjs.TELEFONO] + "\n";
            aux += "Fecha Llamada " + msj[HistorialMjs.FECHA] + "\n" ;
            aux += "Tipo de msj " + msj[HistorialMjs.TIPO] + "\n" ;
            aux += "Contenido " + msj[HistorialMjs.MENSAJE] + "\n";
        }

        tv9.setText(aux);
    }

    public void getHistorialLlamadas(){
        Activity act = this;
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

        tv10.setText(aux);
    }

    public void iniciarGPS(){
        GPS gps = new GPS(this, this, tv9, tv10 );
        //gps.inicia();
    }

    public void iniciarPantalla(){
        Pantalla pantalla = new Pantalla(this);
    }

    public void iniciarServicioApp() {
        startService(new Intent(this, AplicacionesActivas.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constantes.REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("loc", "El usuario permitió el cambio de ajustes de ubicación.");
                        localizacion.procesarLocalizacion();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d("loc", "El usuario no permitió el cambio de ajustes de ubicación");
                        break;
                }
                break;
        }

    }

    public void sendPost(String idDispositivo, LocalizacionJSON locJSON) {
        mAPIService.savePost(idDispositivo, locJSON).enqueue(new Callback<UbicacionJSON>() {
            @Override
            public void onResponse(Call<UbicacionJSON> call, Response<UbicacionJSON> response) {
                if(response.isSuccessful()) {
                    System.out.println("post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UbicacionJSON> call, Throwable t) {
                Log.e("APP", "Unable to submit post to API.");
            }
        });
    }

}
