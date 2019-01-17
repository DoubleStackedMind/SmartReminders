package com.android.esprit.smartreminders.customControllers;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import org.jetbrains.annotations.NotNull;

public class CameraController {
    private Context context;
    private String cameraId;
    private CameraManager cmanager;
    private boolean flashOn;

    public CameraController(@NotNull Context context) {
        this.context = context;
        flashOn=false;
        cmanager = (CameraManager) this.context.getSystemService(Context.CAMERA_SERVICE);
        assert cmanager != null;
        try {
            cameraId = cmanager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            System.out.println("unable to instanciate Camera id");
        }
    }

    public void enableFlash() {

        setFlashState(true);flashOn=true;
    }

    public void disableFlash() {
        setFlashState(false);flashOn=false;
    }

    private void setFlashState(boolean state) {
        try {
            cmanager.setTorchMode(cameraId, state);

        } catch (CameraAccessException e) {
            System.out.println("unable to access camera");
        }
    }
    public boolean isFlashOn(){return flashOn;}
}
