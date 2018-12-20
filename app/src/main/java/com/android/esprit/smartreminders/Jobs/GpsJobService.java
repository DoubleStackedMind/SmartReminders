package com.android.esprit.smartreminders.Jobs;


import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.android.esprit.smartreminders.foreGroundServices.GMapsTrackingForeGroundService;


public class GpsJobService extends JobService {
    public static boolean on;
    private Intent LocationServiceIntent;

    @Override
    public boolean onStartJob(JobParameters params) {
        runBackGroundWoork(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }


    private void runBackGroundWoork(JobParameters params) {
        on=false;
        new Thread(() -> {
            if(!isMyServiceRunning(GMapsTrackingForeGroundService.class)) {
                LocationServiceIntent = new Intent(getApplicationContext(), GMapsTrackingForeGroundService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(LocationServiceIntent);
                } else {
                    startService(LocationServiceIntent);
                }
            }
            jobFinished(params, true);
        }).run();
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
