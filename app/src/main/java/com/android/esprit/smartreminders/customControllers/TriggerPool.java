package com.android.esprit.smartreminders.customControllers;

import android.content.Context;

import com.android.esprit.smartreminders.Entities.Trigger;


public class TriggerPool {
    private Context context;
    private Trigger triggers[];
    private static TriggerPool instance;
    private TriggerPool(Context context) {
        this.context = context;
        triggers = new Trigger[]{


        };
    }

}
