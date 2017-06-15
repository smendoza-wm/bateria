package com.example.saul_wm.bateria.Servicios;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.Collections;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AplicacionesActivas extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.saul_wm.bateria.Servicios.action.FOO";
    private static final String ACTION_BAZ = "com.example.saul_wm.bateria.Servicios.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.saul_wm.bateria.Servicios.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.saul_wm.bateria.Servicios.extra.PARAM2";

    public AplicacionesActivas() {
        super("AplicacionesActivas");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AplicacionesActivas.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AplicacionesActivas.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String top="";
        for(;;){

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
                ActivityManager am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(5);
                top = taskInfo.get(0).topActivity.getPackageName();
                Log.i("TOP", "top app = " + top);
            }else{ //For versions Lollipop and above
                List<AndroidAppProcess> processes = ProcessManager.getRunningForegroundApps(getApplicationContext());
                Collections.sort(processes, new ProcessManager.ProcessComparator());
                for (int i = 0; i <=processes.size()-1 ; i++) {
                    if(processes.get(i).name.equalsIgnoreCase("com.google.android.gms")) { //always the package name above/below this package is the top app
                        if ((i+1)<=processes.size()-1) { //If processes.get(i+1) available, then that app is the top app
                            top = processes.get(i + 1).name;
                        } else if (i!=0) { //If the last package name is "com.google.android.gms" then the package name above this is the top app
                            top = processes.get(i - 1).name;
                        } else{
                            if (i == processes.size()-1) { //If only one package name available
                                top = processes.get(i).name;
                            }
                        }

                    }
                    Log.i("TOP", "top app = " + top);
                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
