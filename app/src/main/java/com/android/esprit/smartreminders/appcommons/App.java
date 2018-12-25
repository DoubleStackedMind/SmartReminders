package com.android.esprit.smartreminders.appcommons;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Fragments.ZonesFragment;
import com.android.esprit.smartreminders.Jobs.GpsJobService;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumer;
import com.android.esprit.smartreminders.Services.WebServiceUser;
import com.android.esprit.smartreminders.permissionHandlers.PermissionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends Application {
    public static final String CHANNEL_ID = "SmartRemindersChannel";
    public static User sessionUser;
    public static Context base;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppCommons.install(this);
        AppCommons.getAppCommonsConfiguration().setEditTextRequiredErrorMessage(R.string.label_required);
        App.base = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "SmarReminders Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        // p = new PermissionHandler(base);
        //  if (p.isLocationPermission()) {
        ComponentName comp = new ComponentName(this, GpsJobService.class);
        JobInfo info = new JobInfo.Builder(9767, comp).setPersisted(true).setPeriodic(15 * 60 * 1000).build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
        //int resultCode = scheduler.schedule(info);
        /*if (resultCode == JobScheduler.RESULT_SUCCESS)
            System.out.println("Sechduled with success");
        else {
            System.out.println("Sechduled with failure");
            */
    }

}


