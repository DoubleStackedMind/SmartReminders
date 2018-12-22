package com.android.esprit.smartreminders.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class AmbientLightListener implements SensorEventListener {

    public static final int NOT_DIM = 3;
    public static final int VERY_DIM = 2;
    public static final int EXTREMELY_DIM = 1;
    public static final int NO_LIGHT = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (event.values[0] <= 1) {
                howDimIsTheLight(0);
            } else if (event.values[0] <= 3) {// extremely dim
                howDimIsTheLight(1);
            } else if (event.values[0] <= 12) {// light is very dim
                howDimIsTheLight(2);
            } else if (event.values[0] > 12) {// NOT DIM
                howDimIsTheLight(3);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public abstract void howDimIsTheLight(int result);
}
