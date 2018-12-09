package com.android.esprit.smartreminders.Test;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.android.esprit.smartreminders.R;

public class NotificationHelper extends ContextWrapper {

    public static final String channel1ID="channel1ID";
    public static final String channel1Name="Channel 1";

    private NotificationManager nManager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if(nManager == null) {
            nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title,String message) {
       return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle(title)
               .setContentText(message)
               .setSmallIcon(R.drawable.ic_alert);
    }
}
