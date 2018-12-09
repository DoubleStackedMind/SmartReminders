package com.android.esprit.smartreminders.Test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import com.android.esprit.smartreminders.Fragments.FragmentFormEvent;


public class Notification_reciever extends BroadcastReceiver {

    Camera camera;
    Parameters parameters;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("", "");
        notificationHelper.getManager().notify(1, nb.build());
        FragmentFormEvent f = new FragmentFormEvent();

    }

    }


