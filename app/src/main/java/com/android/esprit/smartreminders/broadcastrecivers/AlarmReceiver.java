package com.android.esprit.smartreminders.broadcastrecivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.esprit.smartreminders.Fragments.FragmentFormEvent;
import com.android.esprit.smartreminders.Test.LocalData;
import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.notifications.NotificationScheduler;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalData localData = new LocalData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationScheduler.showNotification(context, MainFrame.class,
                "Don't you have a meeting?", "Keep an eye on the time!");

    }
}


