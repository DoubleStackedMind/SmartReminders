package com.android.esprit.smartreminders.customControllers;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import org.jetbrains.annotations.NotNull;

public class CameraController {
    private Context context;
    private String cameraId;
    private CameraManager cmanager;

    public CameraController(@NotNull Context context) {
        this.context = context;
        cmanager = (CameraManager) this.context.getSystemService(Context.CAMERA_SERVICE);
        assert cmanager != null;
        try {
            cameraId = cmanager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            System.out.println("unable to instanciate Camera id");
        }
    }

    public void enableFlash() {
        setFlashState(true);
    }

    public void disableFlash() {
        setFlashState(false);
    }

    private void setFlashState(boolean state) {
        try {
            cmanager.setTorchMode(cameraId, state);
        } catch (CameraAccessException e) {
            System.out.println("unable to access camera");
        }
    }
}
