package com.android.esprit.smartreminders.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class ProximityListener implements SensorEventListener {
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] <= 1) { // something is infront of proximity sensor
                somethingInfrontSensor(true);
            } else if (event.values[0] <= 9 && event.values[0] > 1) {// nothing is infront of proximity sensor
                somethingInfrontSensor(true);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

     public abstract void somethingInfrontSensor(boolean result);
}
