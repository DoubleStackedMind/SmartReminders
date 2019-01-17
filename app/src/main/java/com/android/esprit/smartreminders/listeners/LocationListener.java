package com.android.esprit.smartreminders.listeners;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.Zone;


public class LocationListener implements android.location.LocationListener {
    private static final String TAG = "LocationListener";
    private Location mLastLocation;


    public LocationListener(String provider) {
        Log.e(TAG, "LocationListener " + provider);
        mLastLocation = new Location(provider);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);
        if (isUserOutOfGeoFence(location)) {

        } else if (isUserInGeoFence(location)) {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG, "onProviderDisabled: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG, "onProviderEnabled: " + provider);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG, "onStatusChanged: " + provider);
    }

    private boolean isUserOutOfGeoFence(Location location) {


        return true;
    }

    private boolean isUserInGeoFence(Location location) {
        return true;
    }

    private void runActions() {

    }
}