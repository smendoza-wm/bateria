package com.example.saul_wm.bateria.Localizacion;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Utils.Constantes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

/**
 * Created by Saul-WM on 19/06/2017.
 */

public class Localizacion implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private Context context;

    private TextView tv1;
    private TextView tv2;

    private Activity activity;

    public Localizacion(Context context, Activity activity, TextView tv1, TextView tv2) {
        this.context = context;
        this.activity = activity;
        this.tv1 = tv1;
        this.tv2 = tv2;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    public void iniciar() {
        googleApiClient.connect();
    }

    public void finalzar() {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(
                        activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constantes.REQUEST_LOCATION);
            }
        }
        else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                tv1.setText(String.valueOf(lastLocation.getLatitude()));
                tv2.setText(String.valueOf(lastLocation.getLongitude()));
            } else {
                Toast.makeText(context, "Ubicación no encontrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
