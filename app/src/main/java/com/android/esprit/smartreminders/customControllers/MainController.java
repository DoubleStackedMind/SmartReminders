package com.android.esprit.smartreminders.customControllers;

import android.content.Context;
public class MainController {
    private CameraController cc;
    private SoundManager sm;
    private WifiController wc;
    private Context context;
    private static MainController instance;

    private MainController(Context context) {
        cc = new CameraController(context);
        wc = new WifiController(context);
        sm = new SoundManager(context);

    }

    public static MainController getInstance(Context context) {
        if (instance == null)
            instance = new MainController(context);

        return MainController.instance;

    }

    public  void open_flash() {
        cc.enableFlash();
    }

    public void close_flash() {
        cc.disableFlash();
    }

    public void enable_wifi() {
        wc.enableWifi();
    }

    public void disable_wifi() {
        wc.disableWifi();
    }

    public void set_on_vibrate_mode() {
        sm.enableVibrateMode();
    }

    public void set_on_silent_mode() {
        sm.enableSilentMode();
    }

    public void set_on_normal_mode() {
        sm.enableNormalMode();
    }
}
