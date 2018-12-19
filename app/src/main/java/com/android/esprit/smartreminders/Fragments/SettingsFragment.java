package com.android.esprit.smartreminders.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.esprit.smartreminders.R;

import java.util.List;

import static com.android.esprit.smartreminders.appcommons.AppCommons.getApplication;

public class SettingsFragment extends FragmentChild {
    private Button btnFlash;
    private boolean flashOn;
    private static final int CAMERA_REQUEST = 50;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setBhaviour() {
        btnFlash = this.ParentActivity.findViewById(R.id.flash_open_close);
        flashOn = false;
        ActivityCompat.requestPermissions(ParentActivity, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        btnFlash.setOnClickListener((view) -> {
            Open_Close_Flash();
        });
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void Open_Close_Flash() {

        CameraManager cmanager = (CameraManager) ParentActivity.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cmanager.getCameraIdList()[1];
            flashOn = !flashOn;
            cmanager.setTorchMode(cameraId, flashOn);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBhaviour();
        WifiSignalCalculator();
    }
    private void WifiSignalCalculator()
    {
        Thread t =new Thread(()->{
            ConnectivityManager cm = (ConnectivityManager) SettingsFragment.this.ParentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo Info = cm.getActiveNetworkInfo();
            if (Info == null || !Info.isConnectedOrConnecting()) {
                Log.i("pewpew", "No connection");
            } else {
                int netType = Info.getType();
                int netSubtype = Info.getSubtype();

                if (netType == ConnectivityManager.TYPE_WIFI) {
                    Log.i("pewpew", "Wifi connection");
                    WifiManager wifiManager = (WifiManager)  getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    List<ScanResult> scanResult = wifiManager.getScanResults();
                    for (int i = 0; i < scanResult.size(); i++) {
                        Log.d("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal
                    }


                    // Need to get wifi strength
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    Log.i("pewpew", "GPRS/3G connection");
                    // Need to get differentiate between 3G/GPRS
                }
            }
        });
        t.run();
    }
}
