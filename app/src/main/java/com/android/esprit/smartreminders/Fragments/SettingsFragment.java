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
import android.widget.Switch;

import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.permissionHandlers.PermissionHandler;

import java.util.List;

import static com.android.esprit.smartreminders.appcommons.AppCommons.getApplication;

public class SettingsFragment extends FragmentChild {
    private Switch cameraPermission;
    private Switch locationPermission;


    private PermissionHandler p;



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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        setBhaviour();
    }
    private void setBhaviour() {
     locationPermission.setOnCheckedChangeListener((btnView,isChecked)->{
         if(isChecked){
          p.RequestLocationPermission();

         }else{
             locationPermission.setChecked(true);
         }
     });
     cameraPermission.setOnCheckedChangeListener((btnView,isChecked)->{
         if(isChecked){
             p.RequestCameraPermission();

         }else{
          cameraPermission.setChecked(true);
         }
     });

    }

    private void initViews(){
        p= new PermissionHandler(this.ParentActivity);
        cameraPermission= this.ParentActivity.findViewById(R.id.cameraPermissionSwitch);
        locationPermission= this.ParentActivity.findViewById(R.id.locationInformationSwitch);
        locationPermission.setChecked(p.isLocationPermission());
        cameraPermission.setChecked(p.isCameraPermission());
    }
 /*   private void WifiSignalCalculator()
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
    }*/
}

