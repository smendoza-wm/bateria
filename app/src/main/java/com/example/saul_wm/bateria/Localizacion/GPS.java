package com.example.saul_wm.bateria.Localizacion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.example.saul_wm.bateria.Http.HttpPost;
import com.example.saul_wm.bateria.Utils.Constantes;



public class GPS implements LocationListener {

    private LocationManager locationManager;
    private Activity activity;
    private Context context;

    private TextView tv_latitud;
    private TextView tv_longitud;

    private double latitud = 0;
    private double longitud = 0;

    private String idDispositivo;

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

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
        System.out.println("-----Cambie de cooordenadas con id " + idDispositivo);
        this.longitud = location.getLongitude();
        this.latitud = location.getLatitude();
        if(longitud != 0.0)
            locationManager.removeUpdates(this);

        String [] datos = {"http://dev.avl.webmaps.com.mx/tmp/pruebasAppLocalizacion/ubicacion.php", latitud+"", longitud+"", idDispositivo};
        new HttpPost().execute(datos);

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




}
