package com.android.esprit.smartreminders.customControllers;

import android.content.Context;
import android.media.AudioManager;

public class SoundManager {
    private AudioManager audioManager;
    private Context context;

    public SoundManager(Context context) {
        this.context = context;
        audioManager =(AudioManager) this.context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

    }
    public void enableSilentMode(){
        audioManager.setMode(AudioManager.RINGER_MODE_SILENT);
    }
    public void enableNormalMode(){
        audioManager.setMode(AudioManager.RINGER_MODE_NORMAL);
    }
    public void enableVibrateMode(){
        audioManager.setMode(AudioManager.RINGER_MODE_VIBRATE);
    }

}

