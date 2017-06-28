package com.example.saul_wm.bateria.Localizacion;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Movimiento.ContadorPasos;
import com.example.saul_wm.bateria.Utils.Constantes;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPS implements LocationListener {

    private LocationManager locationManager;
    private Activity activity;
    private Context context;

    private TextView tv_latitud;
    private TextView tv_longitud;



    private double latitud = 0;


    private double longitud = 0;

    public GPS(Context context, Activity act, TextView tv, TextView tv2) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.activity = act;
        this.tv_latitud = tv;
        this.tv_longitud = tv2;
    }

    public GPS(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void iniciaConstante() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                Constantes.TIEMPO_MIN_ACTUALIZACION_GPS, Constantes.DISTANCIA_MIN_ACTUALIZACION_GPS, this);
    }

    public void iniciarSimple() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        System.out.println("Localizando una vez");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, this);
    }

    public void termina(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("-----Cambie de cooordenadas");
        this.longitud = location.getLongitude();
        this.latitud = location.getLatitude();
        if(longitud != 0.0)
            locationManager.removeUpdates(this);

        new HttpPost().execute("http://dev.avl.webmaps.com.mx/tmp/pruebasAppLocalizacion/ubicacion.php");

        //tv_latitud.setText(location.getLatitude()+"");
        //tv_longitud.setText(location.getLongitude() + "");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("Se encendio el GPS");
        //TODO Enviar mensaje para avisar del encendido del gps
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("Se apago el GPS");
        //TODO Enviar mensaje para avisar apagado del GPS
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    private void enviaDatos(URL url) {
        // Obtener la conexión
        HttpURLConnection con = null;
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = hourFormat.format(date);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(date);

        try {
            // Construir los datos a enviar
            String data = "posicion=" + ",latitud;" + URLEncoder.encode(latitud+"","UTF-8")
                        + ",longitud;" + URLEncoder.encode(longitud+"", "UTF-8")
                        + ",fecha;" + URLEncoder.encode(fecha+"", "UTF-8")
                        + ",hora;" + URLEncoder.encode(hora+"", "UTF-8");

            con = (HttpURLConnection)url.openConnection();

            // Activar método POST
            con.setDoOutput(true);

            // Tamaño previamente conocido
            con.setFixedLengthStreamingMode(data.getBytes().length);

            // Establecer application/x-www-form-urlencoded debido a la simplicidad de los datos
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            OutputStream out = new BufferedOutputStream(con.getOutputStream());

            out.write(data.getBytes());
            out.flush();
            out.close();
            System.out.println("Termine de enviar la peticion post");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con!=null)
                con.disconnect();
        }
    }

    class HttpPost extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                enviaDatos(url);

            } catch (Exception e) {
                this.exception = e;
            }
            return null;
        }

        protected void onPostExecute() {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}
