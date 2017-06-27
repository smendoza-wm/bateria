package com.example.saul_wm.bateria.Servicios;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;


public class AplicacionesActivas extends IntentService {

    private Handler mHandler;

    public AplicacionesActivas() {
        super("AplicacionesActivas");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //mHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String top = "";
        for (; ; ) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
                ActivityManager am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(5);
                top = taskInfo.get(0).topActivity.getPackageName();
                Log.i("Version anterior", "top app = " + top);
            } else { //For versions Lollipop and above
                List<AndroidAppProcess> processes = ProcessManager.getRunningForegroundApps(getApplicationContext());
                Collections.sort(processes, new ProcessManager.ProcessComparator());
                for (int i = 0; i <= processes.size() - 1; i++) {
                    if (processes.get(i).name.equalsIgnoreCase("com.google.android.gms")) { //always the package name above/below this package is the top app
                        if ((i + 1) <= processes.size() - 1) { //If processes.get(i+1) available, then that app is the top app
                            top = processes.get(i + 1).name;
                        } else if (i != 0) { //If the last package name is "com.google.android.gms" then the package name above this is the top app
                            top = processes.get(i - 1).name;
                        } else {
                            if (i == processes.size() - 1) { //If only one package name available
                                top = processes.get(i).name;
                            }
                        }



                        Log.i("TOP", "top app = " + top);

                        Log.i("activa", "top app = " + top);

                    }
                }
            }
        }
    }
}
