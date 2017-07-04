package com.example.saul_wm.bateria.Localizacion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Alarma.Alarma;
import com.example.saul_wm.bateria.Http.HttpPost;
import com.example.saul_wm.bateria.Modelo.KeepAlive;
import com.example.saul_wm.bateria.Utils.Constantes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Localizacion implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private Context context;

    private TextView tv1;
    private TextView tv2;

    private Activity activity;

    private Location lastLocation;
    private LocationRequest locationRequest;

    private Alarma alarma;

    private KeepAlive keepAlive;

    private String idDispositivo ="";

    public long getUltimaHoraLocalizacion() {
        return ultimaHoraLocalizacion;
    }

    private long ultimaHoraLocalizacion = 0;

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Localizacion(Context context) {
        this.context = context;
        keepAlive = KeepAlive.getInstance();
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        configurarOpciones();
        alarma = new Alarma();
        alarma.setAlarm(context);

    }

    private void configurarOpciones() {
        locationRequest = new LocationRequest()
                .setInterval(Constantes.INTERVALO_LOCALIZACION)
                .setFastestInterval(Constantes.INTERVALO_LOCALIZACION_RAPIDA)
                .setSmallestDisplacement(Constantes.MINIMA_DISTANCIA_ENTRE_ACTUALIZACIONES)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        // Verificar ajustes de ubicación actuales
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(
                googleApiClient, builder.build()
        );

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d("status", "Los ajustes de ubicación satisfacen la configuración.");
                        procesarLocalizacion();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.d("satus", "Los ajustes de ubicación no satisfacen la configuración. " +
                                    "Se mostrará un diálogo de ayuda.");
                            status.startResolutionForResult(
                                    (Activity)context,
                                    Constantes.REQUEST_CHECK_SETTINGS);

                        } catch (Exception e) {
                            Log.d("status", "El Intent del diálogo no funcionó.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("status", "No se pueden guardar los cambios");
                        break;
                }
            }
        });
    }


    public void iniciar() {
        googleApiClient.connect();
        //alarma = new Alarma();
        //alarma.setLoc(this);
        //alarma.setAlarm(context, this);
    }

    public void finalzar() {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        procesarLocalizacion();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void procesarLocalizacion() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(
                        (Activity)context , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constantes.REQUEST_LOCATION);
            }
        } else {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            System.out.println("peticion de localizacion");
            if (lastLocation != null) {
                //tv1.setText(String.valueOf(lastLocation.getLatitude()));
                //tv2.setText(String.valueOf(lastLocation.getLongitude()));
            } else {
                Toast.makeText(context, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        ultimaHoraLocalizacion = System.currentTimeMillis();
        keepAlive.setUltimaHoraActualizacion(ultimaHoraLocalizacion);
        Log.d("localizacion", String.format("Nueva ubicación: (%s, %s)",
                location.getLatitude(), location.getLongitude()));
        lastLocation = location;
        System.out.println("Cambie de locacalizacion");
        //tv1.setText(String.valueOf(lastLocation.getLatitude()));
        //tv2.setText(String.valueOf(lastLocation.getLongitude()));
        String [] datos = {"http://dev.avl.webmaps.com.mx/tmp/pruebasAppLocalizacion/ubicacion.php", lastLocation.getLatitude()
                +"", lastLocation.getLongitude()+"", idDispositivo};
        new HttpPost().execute(datos);
    }

    private void startLocationUpdates() {

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
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

    }

    private String getFecha(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(date);
        return fecha;
    }

    private String getHora(){
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = hourFormat.format(date);
        return hora;
    }
}
