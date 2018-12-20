package com.android.esprit.smartreminders.permissionHandlers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// add your premission Requests Here

public class PermissionHandler {
    //private boolean Permission;

    private boolean LocationPermission;
    private boolean CameraPermission;
    private boolean ChangeWifiStatePermission;
    private boolean AccessWifiStatePermission;
    private List<String> Permissions;

    public PermissionHandler(Activity activity) {
        updatePermissions(activity);
        RequestCameraPermission();
        RequestLocationPermission();
        RequestChangeWifiStatePermission();
        RequestAccessWifiStatePermission();
        System.out.println(Permissions);
        AskForPermission(activity);
    }

    private void updatePermissions(Activity activity) {
        LocationPermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        CameraPermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED);
        ChangeWifiStatePermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED);
        AccessWifiStatePermission = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED);
        System.out.println("Permission" +"Location:"+this.LocationPermission+",Camera:"+CameraPermission+",ChangeWifiState:"+ChangeWifiStatePermission+",AcessWifiState:"+AccessWifiStatePermission);
    }
    private void AskForPermission(Activity activity){
        if(Permissions.size()>0)
            ActivityCompat.requestPermissions(activity, Permissions.toArray(new String[Permissions.size()]),1);
    }

    private void RequestLocationPermission() {
        if (!LocationPermission) {
           pushBackPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION});
        } else {
            LocationPermission = true;
        }
    }

    private void RequestCameraPermission() {
        if (!CameraPermission) {
            System.out.println("asking for permission for Camera");
            pushBackPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});
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
