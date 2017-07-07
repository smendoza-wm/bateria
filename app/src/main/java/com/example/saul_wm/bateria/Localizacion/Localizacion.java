package com.example.saul_wm.bateria.Localizacion;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.saul_wm.bateria.BaseDatos.BaseDatos;
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
import java.util.Locale;

public class Localizacion implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    protected Context context;
    private SQLiteDatabase db;

    private TextView tv1;
    private TextView tv2;

    private Activity activity;

    private static Location lastLocation;
    private LocationRequest locationRequest;

    private Localizacion.Alarma alarma;

    private KeepAlive keepAlive;

    private static String idDispositivo ="";

    public long getUltimaHoraLocalizacion() {
        return ultimaHoraLocalizacion;
    }

    private static long ultimaHoraLocalizacion = 0;

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Localizacion(Context context) {
        this.context = context;
        alarma = new Alarma();
        alarma.setAlarm(context);
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        configurarOpciones();
    }

    public Localizacion(){}

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


    }

    public void finalzar() {
        googleApiClient.disconnect();
        alarma.cancelAlarm(context);
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
        //insertarMedicion();
        lastLocation = location;
        System.out.println("Cambie de locacalizacion");
        ultimaHoraLocalizacion = System.currentTimeMillis();
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


    public static class Alarma extends BroadcastReceiver
    {

        private  long horaActual = 0;

        public Alarma(){}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            wl.acquire();
            onAlarm();
            wl.release();
        }

        public void setAlarm(Context context)
        {

            AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, Alarma.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constantes.INTERVALO_ALARMA , pi); // Millisec * Second * Minute

        }

        public void cancelAlarm(Context context)
        {
            Intent intent = new Intent(context, Alarma.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(sender);
        }

        private void onAlarm(){
            horaActual = System.currentTimeMillis();
            if(horaActual - ultimaHoraLocalizacion > Constantes.INTERVALO_KEEP_ALIVE){
                String [] datos = {"http://dev.avl.webmaps.com.mx/tmp/pruebasAppLocalizacion/ubicacion.php", lastLocation.getLatitude()
                        +"", lastLocation.getLongitude()+"", idDispositivo};
                new HttpPost().execute(datos);
                System.out.println("ULTIMA HORA DE ACTUaLIZACION DESDE ALARMA: " + ultimaHoraLocalizacion + "Voy a mandar un keep-alive");
            }
            //System.out.println("ULTIMA HORA DE ACTUaLIZACION DESDE ALARMA: " + ultimaHoraLocalizacion );
        }


    }
}
