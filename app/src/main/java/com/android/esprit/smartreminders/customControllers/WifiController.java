package com.android.esprit.smartreminders.customControllers;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.jetbrains.annotations.NotNull;

import java.util.TreeSet;
import java.util.stream.Collectors;

public class WifiController {
    private TreeSet<String> KnownWifiSSID;
    private Context context;
    private WifiManager wifimanager;
    private String currentSSID;
    private WifiInfo wifiInfo;

    public WifiController(@NotNull Context context) {
        this.context=context;
        wifimanager = (WifiManager) this.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        KnownWifiSSID = new TreeSet<>();
        KnownWifiSSID.addAll(wifimanager.getConfiguredNetworks().stream().map(x -> x.SSID).collect(Collectors.toSet()));
        wifiInfo = wifimanager.getConnectionInfo();
        currentSSID = wifiInfo.getSSID();

    }

    public void enableWifi() {
        setWifiState(true);
    }

    public void disableWifi() {
        setWifiState(false);
    }

    private void setWifiState(boolean state) {
        wifimanager.setWifiEnabled(state);
    }

}
