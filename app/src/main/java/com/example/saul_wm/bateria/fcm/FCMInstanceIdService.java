package com.example.saul_wm.bateria.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = FCMInstanceIdService.class.getSimpleName();

    public FCMInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("FCM Token: " + fcmToken);

        sendTokenToServer(fcmToken);
    }

    private void sendTokenToServer(String fcmToken) {
        // Acciones para enviar token a tu app server
    }


}
