package com.android.esprit.smartreminders.permissionHandlers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

// add your premission Requests Here

public class PermissionHandler {
    //private boolean Permission;

    private boolean LocationPermission;
    private boolean CameraPermission;
    private boolean ChangeWifiStatePermission;
    private boolean AccessWifiStatePermission;
    private List<String> Permissions;
    private Activity activity;

    public PermissionHandler(Activity activity) {
        this.activity=activity;
        updatePermissions(activity);
    }

    private void updatePermissions(Activity activity) {
        LocationPermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        CameraPermission = (ActivityCompat.checkSelfPermission(activity, CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED);
        ChangeWifiStatePermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED);
        AccessWifiStatePermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED);
    }
    private void AskForPermission(Activity activity){
        if(Permissions.size()>0)
            ActivityCompat.requestPermissions(activity, Permissions.toArray(new String[Permissions.size()]),1);
    }

    public void RequestLocationPermission() {
        if (!LocationPermission) {
            ActivityCompat.requestPermissions(activity,(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}),1);
        } else {
            LocationPermission = true;
        }
    }

    public void RequestCameraPermission() {
        if (!CameraPermission) {
            ActivityCompat.requestPermissions(activity,(new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}),1);
        } else {
            CameraPermission = true;
        }

    }
    private void RequestChangeWifiStatePermission() {
        if (ChangeWifiStatePermission) {
            pushBackPermission(Manifest.permission.CHANGE_WIFI_STATE);
        } else {
            ChangeWifiStatePermission = true;
        }
    }

    private void RequestAccessWifiStatePermission() {
        if (AccessWifiStatePermission) {
            pushBackPermission(Manifest.permission.ACCESS_WIFI_STATE);
        } else {
            AccessWifiStatePermission = true;
        }
    }

    /*  private void Request_Permission(Activity activity){
      if(Permission){
          ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.Specify}, 1);
      }else{
          Permission=true;
      }
  }
  */
    private void pushBackPermission(String Permission){
        if(Permissions==null)
            Permissions=new ArrayList<>();
            Permissions.add(Permission);

    }
    private void pushBackPermissions(String Permissions[]){
        if(this.Permissions==null)
            this.Permissions=new ArrayList<>();
            this.Permissions.addAll(Arrays.asList(Permissions));

    }
    public boolean isLocationPermission() {
        System.out.println("permissionLocation"+this.LocationPermission);
        return LocationPermission;
    }

    public boolean isCameraPermission() {
        return CameraPermission;
    }

    public boolean isChangeWifiStatePermission() {
        return ChangeWifiStatePermission;
    }

    public boolean isAccessWifiStatePermission() {
        return AccessWifiStatePermission;
    }



}
