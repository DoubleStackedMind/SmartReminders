package com.android.esprit.smartreminders.foreGroundServices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.broadcastrecivers.WifiStateReceiver;
import com.android.esprit.smartreminders.customControllers.CameraController;
import com.android.esprit.smartreminders.listeners.AmbientLightListener;
import com.android.esprit.smartreminders.listeners.LocationListener;
import com.android.esprit.smartreminders.listeners.ProximityListener;
import com.android.esprit.smartreminders.permissionHandlers.PermissionHandler;

import static com.android.esprit.smartreminders.appcommons.App.CHANNEL_ID;
import static com.android.volley.VolleyLog.TAG;

public class GMapsTrackingForeGroundService extends Service {
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 200; //0.2 seconds
    private static final float LOCATION_DISTANCE = 0.5f;// 2.5 meters
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mLight;
    private boolean TriggerOn;


    private LocationListener[] mLocationListeners;

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        registerBroadCastReciver();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }

    }

    private void StartServiceHolder() {

        Intent notificationbIntent = new Intent(this, MainFrame.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationbIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SmartReminders Service")
                .setContentText("We are Tracking you")
                .setSmallIcon(R.drawable.app_logo)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        registerSensorListeners();
    }

    private void invokeBahaviour() {
        System.out.println("light will be on");
    }

    private void registerSensorListeners() {
        mSensorManager.registerListener(new ProximityListener() {
            @Override
            public void somethingInfrontSensor(boolean result) {
                TriggerOn = !result;
                new Handler().postDelayed(() -> {
                            if (TriggerOn) {
                                invokeBahaviour();
                            }

                        },
                        1000);
            }
        }, mProximity, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        mSensorManager.registerListener(new AmbientLightListener() {
            @Override
            public void howDimIsTheLight(int result) {
                switch (result) {
                    case AmbientLightListener.EXTREMELY_DIM: {
                    }
                    case AmbientLightListener.NOT_DIM: {
                    }
                    case AmbientLightListener.VERY_DIM: {
                    }
                    case AmbientLightListener.NO_LIGHT: {
                        TriggerOn = false;
                    }
                }
            }
        }, mLight, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    private void StartServiceLogic() { // thread Repeating Task
        serviceLogic();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StartServiceHolder();
        StartServiceLogic();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void serviceLogic() {

        mLocationListeners = new LocationListener[]{
                new LocationListener(LocationManager.GPS_PROVIDER),
                new LocationListener(LocationManager.NETWORK_PROVIDER)
        };
        Log.e(TAG, "serviceLogic");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void registerBroadCastReciver() {
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(new WifiStateReceiver(), filter);
    }


}
